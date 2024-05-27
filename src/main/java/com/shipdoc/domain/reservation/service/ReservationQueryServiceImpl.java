package com.shipdoc.domain.reservation.service;

import java.util.ArrayList;
import java.util.List;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.Member.repository.PatientRepository;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.global.annotation.LoginMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.reservation.repository.ReservationRepository;
import com.shipdoc.domain.reservation.web.dto.ReservationListDto;
import com.shipdoc.global.enums.statuscode.SuccessStatus;
import com.shipdoc.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryServiceImpl implements ReservationQueryService {
	public final ReservationRepository reservationRepository;

	@Override
	public ApiResponse<?> getAllReservation(@LoginMember Member member) {
		List<Reservation> reservations = reservationRepository.findAll();
		List<ReservationListDto.ReservationResponse> reservationResponses = new ArrayList<>();

		Patient patient = member.getPatientList().get(0);

		if (reservations.isEmpty()) {
			return ApiResponse.of(true, SuccessStatus._OK, null);
		}

		for (Reservation reservation : reservations) {
			reservationResponses.add(ReservationListDto.ReservationResponse.builder()
				.id(reservation.getId())
				.name(patient.getName())
				.reservationTime(reservation.getReservationTime())
				.hospitalId(reservation.getHospital().getId())
				.hospitalName(reservation.getHospital().getName())
				.patientId(reservation.getPatient().getId())
				.autoReservation(reservation.getAutoReservation())
				.absenceCount(reservation.getAbsenceCount())
				.smsId(reservation.getSmsId())
				.build());
		}

		// 랜덤 예상 대기 시간 설정
		ReservationListDto.setRandomEstimatedWaitTime(reservationResponses);

		return ApiResponse.onSuccess(new ReservationListDto.SearchReservationsRes(reservationResponses));
	}

}
