package com.shipdoc.domain.hospital.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewResponseDto {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateReviewResponseDto{
		private Long reviewId;
		private LocalDateTime createdAt;

	}
}
