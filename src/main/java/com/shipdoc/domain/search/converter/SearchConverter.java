package com.shipdoc.domain.search.converter;

import java.util.List;

import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;
import com.shipdoc.domain.search.web.dto.SearchResponseDto;
import com.shipdoc.global.kakao.dto.KakaoResponseDto;

public class SearchConverter {
	public static SearchResponseDto.SearchQueryResponseDto toSearchQueryResponseDto(
		List<HospitalResponseDto.HospitalDetailResponseDto> placeDetailList) {
		return SearchResponseDto.SearchQueryResponseDto.builder()
			.placeDetailList(placeDetailList)
			.size((long)placeDetailList.size())
			.build();
	}
}
