package com.shipdoc.domain.search.web.dto;

import java.util.List;

import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;
import com.shipdoc.global.kakao.dto.KakaoResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SearchResponseDto {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SearchQueryResponseDto {
		private List<HospitalResponseDto.HospitalDetailResponseDto> placeDetailList;
		private Long size;
	}
}
