package com.shipdoc.domain.reservation.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.reservation.converter.ReservationConverter;
import com.shipdoc.domain.reservation.service.ReservationCommandService;
import com.shipdoc.domain.reservation.web.dto.ReservationRequestDto;
import com.shipdoc.domain.reservation.web.dto.ReservationResponseDto;
import com.shipdoc.global.annotation.LoginMember;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationRestController {

	private final ReservationCommandService reservationCommandService;

	@PostMapping()
	public ReservationResponseDto.CreateReservationResponseDto createReservation(@Valid @RequestBody
	ReservationRequestDto.CreateReservationRequestDto request, @LoginMember Member member) {
		Reservation reservation = reservationCommandService.createReservation(request, member);
		return ReservationConverter.toCreateReservationResponseDto(reservation);
	}

}
