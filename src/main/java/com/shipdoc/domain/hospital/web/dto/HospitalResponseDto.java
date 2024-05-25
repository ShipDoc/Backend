package com.shipdoc.domain.hospital.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HospitalResponseDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class HospitalDetailResponseDto {
		private Long hospitalId;
		private String KakaoPlaceId;
		private String phone;
		private String placeName;
		private String placeUrl;
		private String address;
		private Double latitude;
		private Double longitude;
		private String department;
		private Boolean isOpenNow;
		private Integer waitingCount;
		private List<ReviewResponseDto.ReviewDetailResponseDto> reviewList;
		// TODO 즐겨찾기 한 병원인지 구분
		private BusinessHoursResponseDto businessHours;

	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class HospitalPreviewResponseDto {
		private String hospitalName;
		private String address;
		private String distance;
		private Double totalRate;
		private String imageUrl;
		private Double latitude;
		private Double longitude;
		private Integer reviewCount;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BusinessHoursResponseDto {
		public String monday;
		public String tuesday;
		public String wednesday;
		public String thursday;
		public String friday;
		public String saturday;
		public String sunday;
		public String holiday;
		public String breakTime;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class HospitalReviewPageResponseDto {
		private Long hospitalId;
		private String hospitalName;
		private List<ReviewResponseDto.ReviewDetailResponseDto> reviewList;
		String totalRate;
		Integer size;
		Integer totalPage;
		Long totalElements;
		Boolean isFirst;
		Boolean isLast;
	}

}
