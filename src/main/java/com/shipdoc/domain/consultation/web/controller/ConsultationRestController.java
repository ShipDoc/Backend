package com.shipdoc.domain.consultation.web.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.consultation.service.ConsultationCommandService;
import com.shipdoc.domain.consultation.web.dto.ConsultationRequestDto;
import com.shipdoc.domain.consultation.web.dto.ConsultationResponseDto;
import com.shipdoc.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consultations")
public class ConsultationRestController {
	private final ConsultationCommandService consultationCommandService;

	@PostMapping("/{reservationId}")
	public ApiResponse<ConsultationResponseDto.ConsultationConvertResponseDto> convertToConsultation(
		@PathVariable(name = "reservationId") Long reservationId, @Valid @RequestBody
	ConsultationRequestDto.ConsultationConvertRequestDto request) {
		return ApiResponse.onSuccess(consultationCommandService.convertToConsultation(reservationId, request));
	}

}
