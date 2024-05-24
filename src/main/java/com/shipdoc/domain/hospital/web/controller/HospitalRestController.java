package com.shipdoc.domain.hospital.web.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.converter.ReviewConverter;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.service.ReviewCommandService;
import com.shipdoc.domain.hospital.web.dto.ReviewRequestDto;
import com.shipdoc.global.annotation.LoginMember;
import com.shipdoc.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospitals")
public class HospitalRestController {
	private final ReviewCommandService reviewCommandService;

	@PostMapping("/{hospitalId}/reviews")
	public ApiResponse<?> createReview(ReviewRequestDto.createReviewRequestDto request, @LoginMember Member member, @PathVariable(name = "hospitalId") Long hospitalId){
		Review review = reviewCommandService.createReview(request, member, hospitalId);
		return ApiResponse.onSuccess(ReviewConverter.toCreateReviewResponseDto(review));
	}
}
