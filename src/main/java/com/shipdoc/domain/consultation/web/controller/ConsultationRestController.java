package com.shipdoc.domain.consultation.web.controller;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.consultation.service.ConsultationQueryService;
import com.shipdoc.domain.consultation.web.dto.ConsultationListDto;
import com.shipdoc.global.annotation.LoginMember;
import org.springframework.web.bind.annotation.*;

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
	private final ConsultationQueryService consultationQueryService;

	@PostMapping("/{reservationId}")
	public ApiResponse<ConsultationResponseDto.ConsultationConvertResponseDto> convertToConsultation(
		@PathVariable(name = "reservationId") Long reservationId, @Valid @RequestBody
	ConsultationRequestDto.ConsultationConvertRequestDto request) {
		return ApiResponse.onSuccess(consultationCommandService.convertToConsultation(reservationId, request));
	}

	@GetMapping("/check-all")
	public ApiResponse<?> getAllConsultation(@LoginMember Member member) {
		Patient patient = member.getPatientList().get(0);
		ApiResponse<?> result = consultationQueryService.getAllConsultation(member);
		return result;
	}

}
