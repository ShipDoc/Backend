package com.shipdoc.domain.search.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shipdoc.domain.search.enums.HospitalSortCriteria;
import com.shipdoc.domain.search.enums.MedicalDepartment;
import com.shipdoc.domain.search.enums.Symptom;
import com.shipdoc.domain.search.validation.annotation.CategoryKeyword;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class SearchRequestDto {
	@Getter
	@Setter
	@CategoryKeyword
	public static class SearchCategoryRequestDto {
		@NotNull(message = "위도 좌표를 입력해주세요.")
		private Double latitude;

		@NotNull(message = "경도 좌표를 입력해주세요.")
		private Double longitude;

		@JsonProperty(defaultValue = "1")
		@Min(value = 1, message = "페이지 번호는 1보다 크거나 같아야합니다.")
		@Max(value = 45, message = "페이지 번호는 최대 45입니다.")
		private Long page;

		@JsonProperty(defaultValue = "15")
		@Min(value = 1, message = "한 페이지에 보일 수 있는 개수는 1보다 크거나 같아야합니다.")
		@Max(value = 45, message = "한 페이지에 보일 수 있는 최대 개수는 15개 입니다.")
		private Long size;

		@Size(min = 1, message = "카테고리를 선택해주세요.")
		private List<MedicalDepartment> category;

		private HospitalSortCriteria sort;

		private String keyword;
	}

	@Getter
	@Setter
	public static class SearchSymptomRequestDto {
		@NotNull(message = "위도 좌표를 입력해주세요.")
		private Double latitude;

		@NotNull(message = "경도 좌표를 입력해주세요.")
		private Double longitude;

		@JsonProperty(defaultValue = "1")
		@Min(value = 1, message = "페이지 번호는 1보다 크거나 같아야합니다.")
		@Max(value = 45, message = "페이지 번호는 최대 45입니다.")
		private Long page;

		@JsonProperty(defaultValue = "15")
		@Min(value = 1, message = "한 페이지에 보일 수 있는 개수는 1보다 크거나 같아야합니다.")
		@Max(value = 45, message = "한 페이지에 보일 수 있는 최대 개수는 15개 입니다.")
		private Long size;

		@Size(min = 1, message = "증상을 선택해주세요.")
		private List<Symptom> symptomList;

		private HospitalSortCriteria sort;

	}

	@Getter
	@Setter
	public static class SearchHealthCheckupRequestDto{
		@NotNull(message = "위도 좌표를 입력해주세요.")
		private Double latitude;

		@NotNull(message = "경도 좌표를 입력해주세요.")
		private Double longitude;

		@JsonProperty(defaultValue = "15")
		@Min(value = 1, message = "한 페이지에 보일 수 있는 개수는 1보다 크거나 같아야합니다.")
		@Max(value = 45, message = "한 페이지에 보일 수 있는 최대 개수는 15개 입니다.")
		private Long size;

		private HospitalSortCriteria sort;
	}
}
