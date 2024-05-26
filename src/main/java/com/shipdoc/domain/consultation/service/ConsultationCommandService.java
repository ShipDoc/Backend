package com.shipdoc.domain.consultation.service;

import com.shipdoc.domain.consultation.web.dto.ConsultationRequestDto;
import com.shipdoc.domain.consultation.web.dto.ConsultationResponseDto;

public interface ConsultationCommandService {
	ConsultationResponseDto.ConsultationConvertResponseDto convertToConsultation(Long reservationId,
		ConsultationRequestDto.ConsultationConvertRequestDto request);
}
