package com.shipdoc.domain.hospital.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.web.dto.ReviewRequestDto;
import com.shipdoc.domain.hospital.web.dto.ReviewResponseDto;

public class ReviewConverter {

	public static Review toReview(ReviewRequestDto.createReviewRequestDto request) {
		return Review.builder()
			.score(request.getScore())
			.content(request.getContent())
			.reviewRecommendList(new ArrayList<>())
			.build();
	}

	public static ReviewResponseDto.CreateReviewResponseDto toCreateReviewResponseDto(Review review) {
		return ReviewResponseDto.CreateReviewResponseDto.builder()
			.reviewId(review.getId())
			.createdAt(review.getCreatedAt())
			.build();
	}

	public static ReviewResponseDto.ReviewDetailResponseDto toReviewDetailResponseDto(Review review, String name,
		Boolean recommended, Integer recommendCount) {
		return ReviewResponseDto.ReviewDetailResponseDto.builder()
			.reviewId(review.getId())
			.score(review.getScore())
			.createdAt(convertCreatedAtToString(review.getCreatedAt()))
			.name(maskName(name))
			.content(review.getContent())
			.recommended(recommended)
			.recommendCount(recommendCount)
			.build();
	}

	private static String convertCreatedAtToString(LocalDateTime createdAt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
		return createdAt.format(formatter);
	}

	private static String maskName(String name) {
		// 이름이 한 글자라면 그냥 반환
		if (name.length() == 1) {
			return name;
		}

		// 두글자면 뒷글자만 변경
		if (name.length() == 2) {
			StringBuilder masked = new StringBuilder();
			masked.append(name.charAt(0));
			masked.append('*');
			return masked.toString();
		}

		char firstChar = name.charAt(0);
		char lastChar = name.charAt(name.length() - 1);

		// 가운데 글자를 *로 대체
		StringBuilder masked = new StringBuilder();
		masked.append(firstChar);
		for (int i = 1; i < name.length() - 1; i++) {
			masked.append('*');
		}
		masked.append(lastChar);

		return masked.toString();
	}
}
