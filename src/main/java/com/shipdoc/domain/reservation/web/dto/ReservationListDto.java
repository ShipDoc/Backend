package com.shipdoc.domain.reservation.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReservationListDto {
	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class ReservationResponse {
		private Long reservationId; // 예약 ID
		private Long hospitalId; //병원 ID
		private String name; // 환자 이름
		private LocalDateTime reservationTime; //예약 날짜
		private String hospitalName; //병원 이름
		private String hospitalPhone; // 병원 전화번호
		private Integer absenceCount; // 노쇼 횟수
		private String smsId; //예약 문자 발송 ID
		private Boolean autoReservation; //자동 예약 여부
		private String hospitalAddress; // 병원 주소
		private Double hospitalLatitude; // 병원 위도 좌표
		private Double hospitalLongitude; //병원 경도 좌표
		private String kakaoUrl; // 병원 지도
		private Long estimatedWaitTime; // 예상 대기 시간
		private Long estimatedWaitPatient; // 대기자 현황
	}

	// 예약 내역 조회
	@Getter
	@Builder
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
			reservation.estimatedWaitTime = reservation.estimatedWaitPatient * 5; // 예시로 대기 환자 수 * 5로 설정합니다.
		}
	}

	// 랜덤 대기자 현황 설정 메서드
	public static void setRandomEstimatedWaitPatient(List<ReservationResponse> reservations) {
		Random random = new Random();
		for (ReservationResponse reservation : reservations) {
			reservation.estimatedWaitPatient = random.nextLong(30); // 예시로 0에서 30까지의 랜덤 값을 설정합니다.
		}
	}
}
