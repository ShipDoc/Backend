package com.shipdoc.domain.hospital.converter;

import java.util.ArrayList;

import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.web.dto.ReviewRequestDto;
import com.shipdoc.domain.hospital.web.dto.ReviewResponseDto;

public class ReviewConverter {

	public static Review toReview(ReviewRequestDto.createReviewRequestDto request){
		return Review.builder()
			.score(request.getScore())
			.content(request.getContent())
			.recommendationCount(0L)
			.reviewRecommendList(new ArrayList<>())
			.build();
	}

	public static ReviewResponseDto.CreateReviewResponseDto toCreateReviewResponseDto(Review review){
		return 	ReviewResponseDto.CreateReviewResponseDto.builder()
			.reviewId(review.getId())
			.createdAt(review.getCreatedAt())
			.build();
	}
}
