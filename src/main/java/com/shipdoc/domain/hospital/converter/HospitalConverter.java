package com.shipdoc.domain.hospital.converter;

import java.util.BitSet;

import com.shipdoc.domain.hospital.entity.BusinessHours;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;

public class HospitalConverter {
	public static HospitalResponseDto.HospitalDetailResponseDto toHospitalDetailResponseDto(Hospital hospital, String distance){
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

	private static String distanceConverter(String distance){
		if(distance == null) return null;
		int disInt = Integer.parseInt(distance);
		if(disInt < 1000){
			return distance + "m";
		}
		else{
			return String.format("%.1fkm", (double)disInt / 1000);
		}
	}

	private static HospitalResponseDto.BusinessHoursResponseDto toBusinessHoursResponseDto(BusinessHours businessHours){
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
