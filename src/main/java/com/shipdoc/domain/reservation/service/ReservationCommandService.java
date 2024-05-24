package com.shipdoc.domain.reservation.service;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.reservation.web.dto.ReservationRequestDto;

public interface ReservationCommandService {
	Reservation createReservation(ReservationRequestDto.CreateReservationRequestDto request, Member member);
}
