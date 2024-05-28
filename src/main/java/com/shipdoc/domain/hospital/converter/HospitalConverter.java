package com.shipdoc.domain.hospital.converter;

import java.util.List;
import java.util.Random;

import org.springframework.data.domain.Page;

import com.shipdoc.domain.hospital.entity.BusinessHours;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;
import com.shipdoc.domain.hospital.web.dto.ReviewResponseDto;

public class HospitalConverter {
	public static HospitalResponseDto.HospitalDetailResponseDto toHospitalDetailResponseDto(Hospital hospital,
		List<ReviewResponseDto.ReviewDetailResponseDto> reviewList, Double totalRate) {

		String openNow = hospital.getBusinessHours().isOpenNow();
		// TODO 일단 무조건 오픈으로
		openNow = generateRandomOpenNow();
		return HospitalResponseDto.HospitalDetailResponseDto.builder()
			.hospitalId(hospital.getId())
			.KakaoPlaceId(hospital.getKakaoId())
			.phone(hospital.getPhoneNumber())
			.placeName(hospital.getName())
			.placeUrl(hospital.getKakaoUrl())
			.address(hospital.getAddress())
			.latitude(hospital.getLatitude())
			.longitude(hospital.getLongitude())
			.department(hospital.getDepartment())
			.isOpenNow(openNow)
			.imageUrl(hospital.getPhotoUrl())
			.totalRate(totalRate == null ? 0.0 : Math.round(totalRate * 10) / 10.0)
			//TODO 실제 예약 대기자 인원으로 변경
			.waitingCount(generateRandomWaitingCount(openNow))
			.reviewList(reviewList)
			.businessHours(toBusinessHoursResponseDto(hospital.getBusinessHours()))
			.build();
	}

	public static HospitalResponseDto.HospitalPreviewResponseDto toHospitalPreviewResponseDto(Hospital hospital,
		String distance, Double totalRate, Integer reviewCount) {
		return HospitalResponseDto.HospitalPreviewResponseDto.builder()
			.hospitalId(hospital.getId())
			.hospitalName(hospital.getName())
			.address(hospital.getAddress())
			.imageUrl(hospital.getPhotoUrl())
			.totalRate(totalRate == null ? 0.0 : Math.round(totalRate * 10) / 10.0)
			.distance(distanceConverter(distance))
			.latitude(hospital.getLatitude())
			.longitude(hospital.getLongitude())
			.reviewCount(reviewCount)
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

	private static Integer generateRandomWaitingCount(String isOpenNow) {
		if (isOpenNow.equals("CLOSED") || isOpenNow.equals("BREAK_TIME")) {
			return 0;
		} else {
			return new Random().nextInt(15) + 5;
		}
	}

	private static String generateRandomOpenNow() {
		Random random = new Random();
		int i = random.nextInt(6);
		if (i <= 3) {
			return "OPEN";
		} else if (i == 4) {
			return "CLOSED";
		} else {
			return "BREAK_TIME";
		}
	}
}
