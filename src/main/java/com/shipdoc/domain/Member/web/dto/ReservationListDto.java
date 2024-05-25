package com.shipdoc.domain.Member.web.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationListDto {
    @Getter
    @Builder
    @NoArgsConstructor (access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ReservationResponse {
        private Long id; //예약 ID
        private Timestamp reservationTime; //예약 날짜
        private Long hospitalId; //병원명
        private Long patientId; //환자 고유 번호
        private Boolean autoReservation; //자동 예약 여부
        private Long absenceCount; // 노쇼 횟수
    }

    // 예약 내역 조회
    @Getter @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchReservationsRes {
        private List<ReservationListDto.ReservationResponse> reservations;
        public SearchReservationsRes(List<ReservationListDto.ReservationResponse> reservations) {
            this.reservations = reservations;
        }
    }
}
