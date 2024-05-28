package com.shipdoc.domain.hospital.service;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.web.dto.ReviewRequestDto;
import com.shipdoc.domain.hospital.web.dto.ReviewResponseDto;

public interface ReviewCommandService {
	Review createReview(ReviewRequestDto.createReviewRequestDto request, Member member, Long hospitalId);

	ReviewResponseDto.ReviewRecommendResponseDto addReviewRecommand(Long reviewId, Member member);

	ReviewResponseDto.ReviewRecommendResponseDto deleteReviewRecommend(Long reviewId, Member member);

	ReviewResponseDto.ReviewRecommendResponseDto changeReviewRecommend(Long reviewId, Member member);
}
