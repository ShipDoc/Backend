package com.shipdoc.domain.consultation.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class ConsultationRequestDto {

	@Getter
	@Setter
	public static class ConsultationConvertRequestDto {
		@NotBlank(message = "진단결과를 입력하세요.")
		private String diagnosis;
	}

}
