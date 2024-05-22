package com.shipdoc.domain.search.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.search.service.SearchQueryService;
import com.shipdoc.domain.search.web.dto.SearchRequestDto;
import com.shipdoc.domain.search.web.dto.SearchResponseDto;
import com.shipdoc.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchRestController {

	private final SearchQueryService searchQueryService;

	@PostMapping("/symptom")
	public ApiResponse<SearchResponseDto.SearchQueryResponseDto> searchWithsymptom(
		@Valid @RequestBody SearchRequestDto.SearchSymptomRequestDto request) {
		return ApiResponse.onSuccess(searchQueryService.getNearbyHospitalWithSymptom(request));
	}

	@PostMapping("/category")
	public ApiResponse<SearchResponseDto.SearchQueryResponseDto> searchWithCategory(
		@Valid @RequestBody SearchRequestDto.SearchCategoryRequestDto request) {
		return ApiResponse.onSuccess(searchQueryService.getNearbyHospitalWithCategory(request));
	}

	@PostMapping("/health-checkup")
	public ApiResponse<SearchResponseDto.SearchQueryResponseDto> searchWithHealthCheckup(
		@Valid @RequestBody SearchRequestDto.SearchHealthCheckupRequestDto request) {
		return ApiResponse.onSuccess(searchQueryService.getNearbyHospitalWithHealthCheckup(request));
	}
}
