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
	public static class CreateReviewResponseDto {
		private Long reviewId;
		private LocalDateTime createdAt;

	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ReviewDetailResponseDto {
		private Long reviewId;
		private String createdAt;
		private String name;
		private Double score;
		private String content;
		private Boolean recommended;
		private Integer recommendCount;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ReviewRecommendResponseDto {
		private boolean recommended;
		private Integer recommendCount;
	}
}
