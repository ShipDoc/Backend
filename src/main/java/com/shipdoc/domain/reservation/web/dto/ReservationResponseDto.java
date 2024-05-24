package com.shipdoc.domain.reservation.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReservationResponseDto {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateReservationResponseDto {
		private Long reservationId;
		private LocalDateTime createdAt;
	}
}
