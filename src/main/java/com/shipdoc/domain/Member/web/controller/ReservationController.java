package com.shipdoc.domain.Member.web.controller;

import com.shipdoc.domain.Member.service.ReservationService;
import com.shipdoc.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/check-all")
    public ApiResponse<?> getAllReservation() {
        ApiResponse<?> result = reservationService.getAllReservation();
        return result;
    }

}
