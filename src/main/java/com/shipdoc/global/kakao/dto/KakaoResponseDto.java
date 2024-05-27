package com.shipdoc.global.kakao.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoResponseDto {

	@JsonProperty("documents")
	private List<PlaceDetail> placeDetailList;

	@Getter
	@Setter
	public static class PlaceDetail implements Comparable<PlaceDetail> {
		private String distance;
		@JsonProperty("id")
		private String KakaoPlaceId;

		private String phone;

		@JsonProperty("place_name")
		private String placeName;

		@JsonProperty("place_url")
		private String placeUrl;

		@JsonProperty("road_address_name")
		private String address;

		@JsonProperty("x")
		private Double longitude;

		@JsonProperty("y")
		private Double latitude;
		@JsonProperty("category_name")
		private String categoryName;

		@Override
		public int compareTo(PlaceDetail o) {
			return Integer.parseInt(this.distance) - Integer.parseInt(o.distance);
		}
	}

}
