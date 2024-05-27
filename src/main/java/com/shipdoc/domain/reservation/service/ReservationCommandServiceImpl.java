package com.shipdoc.domain.reservation.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.Member.enums.FamilyRelation;
import com.shipdoc.domain.Member.exception.PatientNotExistException;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.domain.hospital.exception.HospitalNotExistException;
import com.shipdoc.domain.hospital.repository.HospitalRepository;
import com.shipdoc.domain.reservation.converter.ReservationConverter;
import com.shipdoc.domain.reservation.exception.ReservationNotExistException;
import com.shipdoc.domain.reservation.repository.ReservationRepository;
import com.shipdoc.domain.reservation.web.dto.ReservationRequestDto;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;
import com.shipdoc.global.service.SchedulerService;
import com.shipdoc.global.sms.SmsSentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationCommandServiceImpl implements ReservationCommandService {

	private final ReservationRepository reservationRepository;
	private final HospitalRepository hospitalRepository;

	private final SmsSentService smsSentService;
	private final SchedulerService schedulerService;

	@Override
	public Reservation createReservation(ReservationRequestDto.CreateReservationRequestDto request, Member member) {
		Reservation reservation = ReservationConverter.toReservation(request);
		Patient patient = null;
		for (Patient memberPatient : member.getPatientList()) {
			if (memberPatient.getFamilyRelation() == FamilyRelation.SELF) {
				patient = memberPatient;
			}
		}

		if (patient == null) {
			log.error("회원의 환자 데이터가 존재하지 않습니다.");
			throw new PatientNotExistException();
		}

		patient.addReservation(reservation);

		Hospital hospital = hospitalRepository.findById(request.getHospitalId())
			.orElseThrow(() -> new HospitalNotExistException());

		hospital.addReservation(reservation);

		// 예약 문자 발송
		addScheduledMessage(reservation, hospital.getName(), reservation.getPatient().getName());

		// 예약 시간 1시간 이후 왔는지 체크 => 만약 아직 예약 기록이 있다면(도착하지 못했다면) 자동으로 다음 예약
		schedulerService.scheduleTask(UUID.randomUUID().toString(),
			() -> checkReservation(reservation.getId(), hospital.getName()),
			convertLocalDateTimeToDate(reservation.getReservationTime()));

		return reservationRepository.save(reservation);
	}

	public void checkReservation(Long reservationId, String hospitalName) {
		Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
		// 만약 노쇼한 경우
		if (optionalReservation.isPresent()) {
			Reservation reservation = optionalReservation.get();
			reservation.changeAbsenceCount();

			Patient patient = reservation.getPatient();

			// 만약 노쇼 횟수가 3번 또는 자동 재예약이 아닐 시 날짜 변경 X ==> 예약 데이터 삭제
			if (reservation.getAbsenceCount() == 3 || !reservation.getAutoReservation()) {
				reservationRepository.delete(reservation);
				return;
			}

			smsSentService.sendMessage(reservation.getPhoneNumber(),
				generateMissingMessageText(hospitalName, patient.getName(), reservation.getReservationTime()));
			// TODO 예약 날짜 변경 (현재 60분 뒤로)
			reservation.changeAbsenceCount();
			reservation.changeReservationTime(reservation.getReservationTime().plusHours(1));
			addScheduledMessage(reservation, hospitalName, patient.getName());
			reservationRepository.save(reservation);
		}
	}

	private void addScheduledMessage(Reservation reservation, String hospitalName, String patientName) {
		if (reservation.getPhoneNumber() != null) {
			String smsId = smsSentService.sendScheduledMessage(reservation.getPhoneNumber(),
				generateRemindMessageText(hospitalName, patientName, reservation.getReservationTime()),
				reservation.getReservationTime().minusMinutes(30));
			reservation.changeSmsId(smsId);
		}
	}

	@Override
	public void cancelReservation(Member member, Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ReservationNotExistException());

		if (reservation.getPatient().getMember() != member) {
			log.error("예약에 대한 접근 권한이 없습니다. 예약 번호 = {}, 사용자 번호 = {}", reservationId, member.getId());
			throw new GeneralException(ErrorStatus._FORBIDDEN);
		}

		if (reservation.getSmsId() != null) {
			// 만약 메세지 전송 예약이 있다면 취소
			smsSentService.cancelScheduledMessage(reservation.getSmsId());
		}

		reservationRepository.delete(reservation);

	}

	private String generateRemindMessageText(String hospitalName, String patientName, LocalDateTime reservationTime) {
		return "[쉽닥] 병원 예약 리마인드\n\n"
			+ "안녕하세요, " + patientName + "님!\n"
			+ "\n"
			+ "곧 있을 병원 예약을 잊지 않으셨죠? \n"
			+ "\n"
			+ hospitalName + "에서의 진료 예약이 30분 후에 시작됩니다.\n"
			+ "[예약 시간: " + convertToTimeText(reservationTime) + "]\n"
			+ "\n"
			+ "방문 시 필요한 서류와 신분증을 꼭 지참해 주세요. 늦지 않게 도착해 주시기 바랍니다.\n"
			+ "\n"
			+ "쉽닥과 함께 건강한 하루 되세요!\n"
			+ "\n"
			+ "쉽닥 드림";
	}

	private String generateMissingMessageText(String hospitalName, String patientName, LocalDateTime reservationTime) {
		return "[쉽닥] 병원 예약 안내\n"
			+ "\n"
			+ "안녕하세요, " + patientName + "님!\n"
			+ "\n"
			+ "오늘 " + hospitalName + "에서의 예약 시간 [예약 시간: " + convertToTimeText(reservationTime) + "]에 오지 못하셨습니다. \n"
			+ "\n"
			+ "다음 가능한 시간으로 자동 재예약을 해드렸습니다.\n"
			+ "\n"
			+ "새 예약 시간: [새 예약 시간: " + convertToTimeText(reservationTime.plusHours(1)) + "]\n"
			+ "\n"
			+ "*참고로, 3회 이상 노쇼 시에는 선입금 비용이 환불되지 않으며, 자동 재예약이 불가하오니 주의해 주세요.\n"
			+ "\n"
			+ "쉽닥과 함께 건강을 지켜 나가세요!\n"
			+ "\n"
			+ "쉽닥 드림";
	}

	private String convertToTimeText(LocalDateTime reservationTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분");
		return reservationTime.format(formatter);
	}

	private Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
		Instant instant = zonedDateTime.toInstant();
		return Date.from(instant);
	}
}
