package com.shipdoc.domain.search.service;

import com.shipdoc.domain.search.web.dto.SearchRequestDto;
import com.shipdoc.domain.search.web.dto.SearchResponseDto;

public interface SearchQueryService {
	SearchResponseDto.SearchQueryResponseDto getNearbyHospitalWithSymptom(
		SearchRequestDto.SearchSymptomRequestDto request);

	SearchResponseDto.SearchQueryResponseDto getNearbyHospitalWithCategory(
		SearchRequestDto.SearchCategoryRequestDto request);
}
