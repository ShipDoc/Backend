package com.shipdoc.domain.search.web.dto;

import java.util.List;

import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;

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
		private List<HospitalResponseDto.HospitalPreviewResponseDto> hospitalList;
		private Long size;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class HospitalSearchResponseDto {
		private String keyword;
		private List<HospitalResponseDto.HospitalPreviewResponseDto> hospitalList;
		private Integer size;
	}
}
