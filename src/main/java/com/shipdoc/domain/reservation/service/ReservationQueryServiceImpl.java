package com.shipdoc.domain.reservation.service;

import java.util.ArrayList;
import java.util.List;

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
	public ApiResponse<?> getAllReservation() {
		List<Reservation> reservations = reservationRepository.findAll();
		List<ReservationListDto.ReservationResponse> reservationResponses = new ArrayList<>();

		if (reservations.isEmpty()) {
			return ApiResponse.of(true, SuccessStatus._OK, null);
		}

		for (Reservation reservation : reservations) {
			reservationResponses.add(ReservationListDto.ReservationResponse.builder()
				.id(reservation.getId())
				.reservationTime(reservation.getReservationTime())
				.hospitalId(reservation.getHospital().getId())
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
