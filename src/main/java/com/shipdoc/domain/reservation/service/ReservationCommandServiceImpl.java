package com.shipdoc.domain.reservation.service;

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
import com.shipdoc.domain.reservation.repository.ReservationRepository;
import com.shipdoc.domain.reservation.web.dto.ReservationRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationCommandServiceImpl implements ReservationCommandService {

	private final ReservationRepository reservationRepository;
	private final PatientRepository patientRepository;
	private final HospitalRepository hospitalRepository;

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
		//TODO 예약 문자 발송

		return reservationRepository.save(reservation);
	}
}
