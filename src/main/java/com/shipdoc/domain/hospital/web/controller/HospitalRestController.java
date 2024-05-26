package com.shipdoc.domain.hospital.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.converter.ReviewConverter;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.enums.ReviewSortStatus;
import com.shipdoc.domain.hospital.service.HospitalQueryService;
import com.shipdoc.domain.hospital.service.ReviewCommandService;
import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;
import com.shipdoc.domain.hospital.web.dto.ReviewRequestDto;
import com.shipdoc.domain.hospital.web.dto.ReviewResponseDto;
import com.shipdoc.global.annotation.LoginMember;
import com.shipdoc.global.response.ApiResponse;
import com.shipdoc.global.validation.annotation.PageableVariable;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospitals")
public class HospitalRestController {
	private final ReviewCommandService reviewCommandService;
	private final HospitalQueryService hospitalQueryService;

	@PostMapping("/{hospitalId}/reviews")
	public ApiResponse<ReviewResponseDto.CreateReviewResponseDto> createReview(
		@Valid @RequestBody ReviewRequestDto.createReviewRequestDto request, @LoginMember Member member,
		@PathVariable(name = "hospitalId") Long hospitalId) {
		Review review = reviewCommandService.createReview(request, member, hospitalId);
		return ApiResponse.onSuccess(ReviewConverter.toCreateReviewResponseDto(review));
	}

	@GetMapping("/{hospitalId}/reviews")
	public ApiResponse<HospitalResponseDto.HospitalReviewPageResponseDto> getHospitalReviewList(
		@PageableVariable @RequestParam(name = "page") Integer page,
		@PageableVariable @RequestParam(name = "size") Integer size,
		@RequestParam(name = "sort")
		ReviewSortStatus sortStatus, @PathVariable(name = "hospitalId") Long hospitalId,
		@LoginMember Member member
	) {

		HospitalResponseDto.HospitalReviewPageResponseDto result = hospitalQueryService.getHospitalReviewList(
			page - 1, size, sortStatus, hospitalId, member);
		return ApiResponse.onSuccess(result);
	}

	@GetMapping("/{hospitalId}")
	public ApiResponse<HospitalResponseDto.HospitalDetailResponseDto> getHospitalDetail(
		@PathVariable(name = "hospitalId") Long hospitalId, @LoginMember Member member) {
		return ApiResponse.onSuccess(hospitalQueryService.getHospitalDetail(hospitalId, member));
	}

	@GetMapping("/{hospitalId}/reservation")
	public ApiResponse<HospitalResponseDto.GetReservationDetailsResponseDto> getReservationDetails(
		@LoginMember Member member, @PathVariable(name = "hospitalId") Long hospitalId) {
		return ApiResponse.onSuccess(hospitalQueryService.getReservationDetails(hospitalId, member));
	}

}
