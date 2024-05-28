package com.shipdoc.domain.reservation.service;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.global.annotation.LoginMember;
import com.shipdoc.global.response.ApiResponse;

public interface ReservationQueryService {
	ApiResponse<?> getAllReservation(@LoginMember Member member);
}
