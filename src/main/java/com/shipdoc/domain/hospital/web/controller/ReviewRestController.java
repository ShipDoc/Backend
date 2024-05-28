package com.shipdoc.domain.hospital.web.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.service.ReviewCommandService;
import com.shipdoc.domain.hospital.web.dto.ReviewResponseDto;
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
	public ApiResponse<ReviewResponseDto.ReviewRecommendResponseDto> recommendReview(
		@PathVariable(name = "reviewId") Long reviewId, @LoginMember Member member) {
		Integer reviewCount = reviewCommandService.addReviewRecommand(reviewId, member);
		return ApiResponse.onSuccess(
			ReviewResponseDto.ReviewRecommendResponseDto.builder().recommended(reviewCount).build());
	}

	@DeleteMapping("/{reviewId}/recommend")
	public ApiResponse<ReviewResponseDto.ReviewRecommendResponseDto> deleteReviewRecommend(
		@PathVariable(name = "reviewId") Long reviewId, @LoginMember Member member) {
		Integer reviewCount = reviewCommandService.deleteReviewRecommend(reviewId, member);
		return ApiResponse.onSuccess(
			ReviewResponseDto.ReviewRecommendResponseDto.builder().recommended(reviewCount).build());
	}

	@PutMapping("/{reviewId}/recommend")
	public ApiResponse<ReviewResponseDto.ReviewRecommendResponseDto> changeReviewRecommend(
		@PathVariable(name = "reviewId") Long reviewId, @LoginMember Member member) {
		Integer reviewCount = reviewCommandService.changeReviewRecommend(reviewId, member);
		return ApiResponse.onSuccess(
			ReviewResponseDto.ReviewRecommendResponseDto.builder().recommended(reviewCount).build());
	}
}
