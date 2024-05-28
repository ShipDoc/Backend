package com.shipdoc.domain.Member.web.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class ReservationListDto {
    @Getter
    @Builder
    @NoArgsConstructor (access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ReservationResponse {
        private Long id; //예약 ID
        private String name; // 환자 이름
        private LocalDateTime reservationTime; //예약 날짜
        private Long hospitalId; //병원명
        private Long patientId; //환자 고유 번호
        private Boolean autoReservation; //자동 예약 여부
        private Integer absenceCount; // 노쇼 횟수
        private String smsId; //예약 문자 발송 ID
        private Long estimatedWaitTime; // 예상 대기 시간
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

    // 랜덤 예상 대기 시간을 설정하는 메서드
    public static void setRandomEstimatedWaitTime(List<ReservationResponse> reservations) {
        Random random = new Random();
        for (ReservationResponse reservation : reservations) {
            reservation.estimatedWaitTime = random.nextLong(60); // 예시로 0에서 60까지의 랜덤 값을 설정합니다.
        }
    }
}
