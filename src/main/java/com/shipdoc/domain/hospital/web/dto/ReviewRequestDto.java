package com.shipdoc.domain.hospital.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class ReviewRequestDto {
	@Setter
	@Getter
	public static class createReviewRequestDto{
		@NotNull(message = "별점을 입력해주세요.")
		private Double score;

		private String content;
	}
}
