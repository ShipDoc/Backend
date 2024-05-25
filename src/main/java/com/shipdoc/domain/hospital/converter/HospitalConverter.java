package com.shipdoc.domain.hospital.converter;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shipdoc.domain.hospital.entity.BusinessHours;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;
import com.shipdoc.domain.hospital.web.dto.ReviewResponseDto;

public class HospitalConverter {
	public static HospitalResponseDto.HospitalDetailResponseDto toHospitalDetailResponseDto(Hospital hospital,
		String distance) {
		return HospitalResponseDto.HospitalDetailResponseDto.builder()
			.hospitalId(hospital.getId())
			.KakaoPlaceId(hospital.getKakaoId())
			.distance(distanceConverter(distance))
			.phone(hospital.getPhoneNumber())
			.placeName(hospital.getName())
			.placeUrl(hospital.getKakaoUrl())
			.address(hospital.getAddress())
			.longitude(hospital.getLongitude())
			.latitude(hospital.getLatitude())
			.businessHours(toBusinessHoursResponseDto(hospital.getBusinessHours()))
			.build();
	}

	public static HospitalResponseDto.HospitalReviewPageResponseDto toHospitalReviewPageResponseDto(Hospital hospital,
		List<ReviewResponseDto.ReviewDetailResponseDto> reviewDetailResponseDtoPage, Double totalRate,
		Page<Review> reviewPage) {
		return HospitalResponseDto.HospitalReviewPageResponseDto.builder()
			.hospitalId(hospital.getId())
			.hospitalName(hospital.getName())
			.reviewList(reviewDetailResponseDtoPage)
			.totalRate(String.format("%.1f", totalRate))
			.totalElements(reviewPage.getTotalElements())
			.totalPage(reviewPage.getTotalPages())
			.isFirst(reviewPage.isFirst())
			.isLast(reviewPage.isLast())
			.size(reviewPage.getSize())
			.build();
	}

	private static String distanceConverter(String distance) {
		if (distance == null)
			return null;
		int disInt = Integer.parseInt(distance);
		if (disInt < 1000) {
			return distance + "m";
		} else {
			return String.format("%.1fkm", (double)disInt / 1000);
		}
	}

	private static HospitalResponseDto.BusinessHoursResponseDto toBusinessHoursResponseDto(
		BusinessHours businessHours) {
		return HospitalResponseDto.BusinessHoursResponseDto.builder()
			.monday(businessHours.getMonday())
			.tuesday(businessHours.getTuesday())
			.wednesday(businessHours.getWednesday())
			.thursday(businessHours.getThursday())
			.friday(businessHours.getFriday())
			.saturday(businessHours.getSaturday())
			.sunday(businessHours.getSunday())
			.holiday(businessHours.getHoliday())
			.breakTime(businessHours.getBreakTime())
			.build();
	}
}
