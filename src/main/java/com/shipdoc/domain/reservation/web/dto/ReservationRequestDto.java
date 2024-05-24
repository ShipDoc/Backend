package com.shipdoc.domain.reservation.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shipdoc.domain.reservation.validation.annotation.PhoneNumber;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

public class ReservationRequestDto {

	@Getter
	@Setter
	public static class CreateReservationRequestDto {
		// TODO 자녀부분 추가되면 자녀 ID 받기
		@NotNull(message = "병원 번호를 입력해주세요.")
		private Long hospitalId;

		@Pattern(regexp = "^(19|20)\\d\\d(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$", message = "예약날짜를 올바른 형식으로 입력해주세요. - yyyyMMdd")
		private String reservationDate;

		@Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "예약 시각을 올바른 형식으로 입력해주세요. - HH:MM")
		private String reservationTime;

		@PhoneNumber
		private String phoneNumber;
		@JsonProperty("isAutoReservation")
		@NotNull(message = "예약 형식을 선택해주세요.")
		private Boolean autoReservation;

	}
}
