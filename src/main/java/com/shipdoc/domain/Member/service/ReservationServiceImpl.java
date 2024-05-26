package com.shipdoc.domain.Member.service;

import com.shipdoc.domain.Member.entity.Reservation;
import com.shipdoc.domain.Member.repository.ReservationRepository;
import com.shipdoc.domain.Member.web.dto.ReservationListDto;
import com.shipdoc.global.enums.statuscode.SuccessStatus;
import com.shipdoc.global.response.ApiResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    // 예약 조회
    @Override
    public ApiResponse<?> getAllReservation() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationListDto.ReservationResponse> reservationResponses = new ArrayList<>();

        if(reservations.isEmpty()) {
            return ApiResponse.of(true, SuccessStatus._OK, null);
        }

        for(Reservation reservation: reservations) {
            reservationResponses.add(ReservationListDto.ReservationResponse.builder()
                    .id(reservation.getId())
                    .reservationTime(reservation.getReservationTime())
                    .hospitalId(reservation.getHospitalId())
                    .patientId(reservation.getPatientId())
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
