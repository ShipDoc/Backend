package com.shipdoc.domain.hospital.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.enums.ReviewSortStatus;
import com.shipdoc.domain.hospital.web.dto.ReviewResponseDto;

public interface ReviewQueryService {
	List<ReviewResponseDto.ReviewDetailResponseDto> getReviewDetailList(Member member, Page<Review> reviewPage);

	Page<Review> getReviewPage(ReviewSortStatus sortStatus, Integer page, Integer size, Long hospitalId);
}
