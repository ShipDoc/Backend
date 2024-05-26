package com.shipdoc.domain.reservation.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.Member.enums.FamilyRelation;
import com.shipdoc.domain.Member.exception.PatientNotExistException;
import com.shipdoc.domain.Member.repository.PatientRepository;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.domain.hospital.exception.HospitalNotExistException;
import com.shipdoc.domain.hospital.repository.HospitalRepository;
import com.shipdoc.domain.reservation.converter.ReservationConverter;
import com.shipdoc.domain.reservation.exception.ReservationNotExistException;
import com.shipdoc.domain.reservation.repository.ReservationRepository;
import com.shipdoc.domain.reservation.web.dto.ReservationRequestDto;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;
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
		if(reservation.getPhoneNumber() != null) {
			String smsId = smsSentService.sendScheduledMessage(reservation.getPhoneNumber(),
				generateRemindMessageText(hospital, patient.getName(), reservation.getReservationTime()),
				reservation.getReservationTime().minusMinutes(30));
			reservation.changeSmsId(smsId);
		}

		return reservationRepository.save(reservation);
	}

	@Override
	public void cancelReservation(Member member, Long reservationId){
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ReservationNotExistException());

		if(reservation.getPatient().getMember() != member){
			log.error("예약에 대한 접근 권한이 없습니다. 예약 번호 = {}, 사용자 번호 = {}", reservationId, member.getId());
			throw new GeneralException(ErrorStatus._FORBIDDEN);
		}


		if(reservation.getSmsId() != null) {
			// 만약 메세지 전송 예약이 있다면 취소
			smsSentService.cancelScheduledMessage(reservation.getSmsId());
		}

		reservationRepository.delete(reservation);

	}

	private String generateRemindMessageText(Hospital hospital, String patientName, LocalDateTime reservationTime){
			return "[쉽닥] 병원 예약 리마인드\n\n"
			+ "안녕하세요, " + patientName + "님!\n"
			+ "\n"
			+ "곧 있을 병원 예약을 잊지 않으셨죠? \n"
			+ "\n"
			+ hospital.getName() +"에서의 진료 예약이 30분 후에 시작됩니다.\n"
			+ "[예약 시간: " + ConvertToTimeText(reservationTime) + "]\n"
			+ "\n"
			+ "방문 시 필요한 서류와 신분증을 꼭 지참해 주세요. 늦지 않게 도착해 주시기 바랍니다.\n"
			+ "\n"
			+ "쉽닥과 함께 건강한 하루 되세요!\n"
			+ "\n"
			+ "쉽닥 드림\uD83D\uDC0F\n"
			+ "\n";
	}

	private String ConvertToTimeText(LocalDateTime reservationTime){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분");
		return reservationTime.format(formatter);
	}
}
