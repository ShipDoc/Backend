package com.shipdoc.domain.Member.service;

import com.shipdoc.domain.Member.entity.Reservation;
import com.shipdoc.domain.Member.repository.ReservationRepository;
import com.shipdoc.domain.Member.web.dto.ReservationListDto;
import com.shipdoc.global.response.ApiResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    // 예약 조회
    @Override
    public ApiResponse<?> getAllReservation() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationListDto.ReservationResponse> reservationResponses = new ArrayList<>();

        if(reservations.isEmpty()) {
            return ApiResponse.onFailure("NO_RESERVATIONS", "예약 내역이 없습니다.", null);
        }
        for(Reservation reservation: reservations) {
            reservationResponses.add(ReservationListDto.ReservationResponse.builder()
                    .id(reservation.getId())
                    .reservationDate(reservation.getReservationDate())
                    .hospitalId(reservation.getHospitalId())
                    .memberId(reservation.getMemberId())
                    .build());
        }

        return ApiResponse.onSuccess(new ReservationListDto.SearchReservationsRes(reservationResponses));
    }

}
