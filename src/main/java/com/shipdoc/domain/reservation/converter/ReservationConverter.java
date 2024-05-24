package com.shipdoc.domain.reservation.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.reservation.web.dto.ReservationRequestDto;
import com.shipdoc.domain.reservation.web.dto.ReservationResponseDto;

public class ReservationConverter {

	public static Reservation toReservation(ReservationRequestDto.CreateReservationRequestDto request) {
		return Reservation.builder()
			.reservationTime(convertToLocalDateTime(request.getReservationDate(), request.getReservationTime()))
			.autoReservation(request.getAutoReservation())
			.absenceCount(0)
			.build();
	}

	public static ReservationResponseDto.CreateReservationResponseDto toCreateReservationResponseDto(
		Reservation reservation) {
		return ReservationResponseDto.CreateReservationResponseDto.builder()
			.reservationId(reservation.getId())
			.createdAt(reservation.getCreatedAt())
			.build();
	}

	/**
	 * yyyyMMdd, HH:mm 형식의 문자열을 LocalDateTime 형태로 변환
	 */
	private static LocalDateTime convertToLocalDateTime(String dateStr, String timeStr) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		LocalDate date = LocalDate.parse(dateStr, dateFormatter);
		LocalTime time = LocalTime.parse(timeStr, timeFormatter);

		return LocalDateTime.of(date, time);
	}

}
