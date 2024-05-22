package com.shipdoc.domain.hospital.web.dto;

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
		private String distance;
		private String KakaoPlaceId;

		private String phone;

		private String placeName;

		private String placeUrl;

		private String address;

		private Double longitude;

		private Double latitude;

		private BusinessHoursResponseDto businessHours;

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
}