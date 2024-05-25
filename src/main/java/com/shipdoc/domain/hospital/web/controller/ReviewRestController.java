package com.shipdoc.domain.hospital.web.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.service.ReviewCommandService;
import com.shipdoc.global.annotation.LoginMember;
import com.shipdoc.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewRestController {

	// TODO Review 수정, 삭제
	private final ReviewCommandService reviewCommandService;

	@PostMapping("/{reviewId}/recommend")
	public ApiResponse<String> recommendReview(@PathVariable(name = "reviewId") Long reviewId, @LoginMember Member member){
		reviewCommandService.addReviewRecommand(reviewId, member);
		return ApiResponse.onSuccess("리뷰 추천에 성공했습니다.");
	}
}
