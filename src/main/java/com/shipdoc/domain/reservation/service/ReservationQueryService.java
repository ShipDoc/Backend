package com.shipdoc.domain.reservation.service;

import com.shipdoc.global.response.ApiResponse;

public interface ReservationQueryService {
	ApiResponse<?> getAllReservation();
}
