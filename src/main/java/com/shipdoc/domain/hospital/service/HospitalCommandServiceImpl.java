package com.shipdoc.domain.hospital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.hospital.converter.HospitalConverter;
import com.shipdoc.domain.hospital.entity.BusinessHours;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.domain.hospital.repository.HospitalRepository;
import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;
import com.shipdoc.global.kakao.dto.KakaoResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalCommandServiceImpl implements HospitalCommandService{
	private final HospitalRepository hospitalRepository;

	/**
	 * 존재하지 않는 병원 데이터라면
	 * 데이터베이스에 추가하는 메서드
	 */
	public List<HospitalResponseDto.HospitalDetailResponseDto> saveNotExistHospitals(
		List<KakaoResponseDto.PlaceDetail> placeDetailList) {
		return placeDetailList.stream().map(this::getHospitalDetailResponseDto).collect(Collectors.toList());
	}

	/**
	 * kakao 검색으로 받은 placeDetail 객체를
	 * 데이터가 없으면 저장하고,
	 * HospitalDetailResponseDto 객체로 반환
	 */
	private HospitalResponseDto.HospitalDetailResponseDto getHospitalDetailResponseDto(
		KakaoResponseDto.PlaceDetail placeDetail) {
		Optional<Hospital> hospitalOptional =  hospitalRepository.findByKakaoId(placeDetail.getKakaoPlaceId());
		Hospital hospital;
		if(hospitalOptional.isPresent()){
			hospital = hospitalOptional.get();
		}
		else{
			BusinessHours businessHours = generateBusinessHours();
			hospital = Hospital.builder()
				.kakaoId(placeDetail.getKakaoPlaceId())
				.name(placeDetail.getPlaceName())
				.address(placeDetail.getAddress())
				.latitude(placeDetail.getLatitude())
				.longitude(placeDetail.getLongitude())
				.phoneNumber(placeDetail.getPhone())
				.kakaoUrl(placeDetail.getPlaceUrl())
				.favoriteHospitalList(new ArrayList<>())
				.reviewList(new ArrayList<>())
				.reservationList(new ArrayList<>())
				.photoUrl(generatePhotoUrl())
				.build();

			hospital.changeBusinessHours(businessHours);

			hospitalRepository.save(hospital);
		}

		return HospitalConverter.toHospitalDetailResponseDto(hospital, placeDetail.getDistance());
	}

	private String generatePhotoUrl() {
		Random random = new Random();
		int randomNumber = random.nextInt(120) + 1;
		return "https://shipdoc-bucket.s3.ap-northeast-2.amazonaws.com/hospital/image/" + randomNumber + ".jpg";
	}

	private BusinessHours generateBusinessHours() {
		Random random = new Random();
		int randomNumber = random.nextInt(100) + 1;
		String startTime;
		if(randomNumber<= 20){
			startTime = "09:30";
		}
		if(randomNumber <= 50){
			startTime = "09:00";
		}
		else if(randomNumber <= 80){
			startTime = "08:30";
		}
		else{
			startTime = "08:00";
		}

		randomNumber = random.nextInt(100) + 1;
		String endTime;
		if(randomNumber<= 20){
			endTime = "18:30";
		}
		if(randomNumber <= 50){
			endTime = "21:00";
		}
		else if(randomNumber <= 80){
			endTime = "19:00";
		}
		else{
			endTime = "20:00";
		}


		String weekdayTime = startTime + " ~ " + endTime;

		StringTokenizer st = new StringTokenizer(endTime, ":");
		String saturdayTime = startTime + " ~ " + (Integer.parseInt(st.nextToken()) - 3) + ":" + st.nextToken();


		randomNumber = random.nextInt(100) + 1;
		String holidayTime;

		if(randomNumber<= 75){
			holidayTime = "휴뮤";
		}
		else if(randomNumber <= 15){
			holidayTime = startTime + " ~ " + (Integer.parseInt(st.nextToken()) - 5) + ":" + st.nextToken();
		}
		else{
			holidayTime = saturdayTime;
		}


		String breakTime;
		randomNumber = random.nextInt(100) + 1;
		if(randomNumber <= 50){
			breakTime = "12:00 ~ 13:00";
		}
		else if(randomNumber <= 30){
			breakTime = "12:30 ~ 13:30";
		}
		else{
			breakTime = "13:00 ~ 14:00";
		}

		return BusinessHours.builder()
			.monday(weekdayTime)
			.tuesday(weekdayTime)
			.wednesday(weekdayTime)
			.thursday(weekdayTime)
			.friday(weekdayTime)
			.saturday(saturdayTime)
			.sunday(holidayTime)
			.holiday(holidayTime)
			.breakTime(breakTime)
			.build();
	}

}
