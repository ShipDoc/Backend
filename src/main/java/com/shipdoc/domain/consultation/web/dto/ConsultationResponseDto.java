package com.shipdoc.domain.consultation.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ConsultationResponseDto {
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ConsultationConvertResponseDto {
		private Long consultationId;
		private LocalDateTime createdAt;
	}
}
