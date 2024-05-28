package com.shipdoc.domain.hospital.service;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.web.dto.ReviewRequestDto;

public interface ReviewCommandService {
	Review createReview(ReviewRequestDto.createReviewRequestDto request, Member member, Long hospitalId);

	Integer addReviewRecommand(Long reviewId, Member member);

	Integer deleteReviewRecommend(Long reviewId, Member member);

	Integer changeReviewRecommend(Long reviewId, Member member);
}
