package com.shipdoc.domain.reservation.web.controller;

import com.shipdoc.domain.Member.entity.Patient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.reservation.converter.ReservationConverter;
import com.shipdoc.domain.reservation.service.ReservationCommandService;
import com.shipdoc.domain.reservation.service.ReservationQueryService;
import com.shipdoc.domain.reservation.web.dto.ReservationRequestDto;
import com.shipdoc.domain.reservation.web.dto.ReservationResponseDto;
import com.shipdoc.global.annotation.LoginMember;
import com.shipdoc.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationRestController {

	private final ReservationCommandService reservationCommandService;
	private final ReservationQueryService reservationQueryService;

	@PostMapping()
	public ApiResponse<ReservationResponseDto.CreateReservationResponseDto> createReservation(@Valid @RequestBody
	ReservationRequestDto.CreateReservationRequestDto request, @LoginMember Member member) {
		Reservation reservation = reservationCommandService.createReservation(request, member);
		return ApiResponse.onSuccess(ReservationConverter.toCreateReservationResponseDto(reservation));
	}

	@DeleteMapping("/{reservationId}")
	public ApiResponse<String> cancelReservation(@PathVariable(name = "reservationId") Long reservationid,
		@LoginMember Member member) {
		reservationCommandService.cancelReservation(member, reservationid);
		return ApiResponse.onSuccess("정상적으로 예약을 취소했습니다.");
	}

	@GetMapping("/check-all")
	public ApiResponse<?> getAllReservation(@LoginMember Member member) {
		Patient patient = member.getPatientList().get(0);
		ApiResponse<?> result = reservationQueryService.getAllReservation(member);
		return result;
	}
}
