package com.shipdoc.domain.search.converter;

import java.util.List;

import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;
import com.shipdoc.domain.search.web.dto.SearchResponseDto;

public class SearchConverter {
	public static SearchResponseDto.SearchQueryResponseDto toSearchQueryResponseDto(
		List<HospitalResponseDto.HospitalPreviewResponseDto> placePreviewList) {
		return SearchResponseDto.SearchQueryResponseDto.builder()
			.hospitalList(placePreviewList)
			.size((long)placePreviewList.size())
			.build();
	}
}
