package com.shipdoc.domain.hospital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
public class HospitalCommandServiceImpl {
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
	public HospitalResponseDto.HospitalDetailResponseDto getHospitalDetailResponseDto(
		KakaoResponseDto.PlaceDetail placeDetail) {
		return HospitalConverter.toHospitalDetailResponseDto(
			hospitalRepository.findByKakaoId(placeDetail.getKakaoPlaceId()).orElse(
				hospitalRepository.save(Hospital.builder()
					.kakaoId(placeDetail.getKakaoPlaceId())
					.name(placeDetail.getPlaceName())
					.address(placeDetail.getAddress())
					.latitude(placeDetail.getLatitude())
					.longitude(placeDetail.getLongitude())
					.favoriteHospitalList(new ArrayList<>())
					.reviewList(new ArrayList<>())
					.reservationList(new ArrayList<>())
					.build())
			));
	}

	private String generatePhotoUrl() {
		Random random = new Random();
		int randomNumber = random.nextInt(120) + 1;
		return "https://shipdoc-bucket.s3.ap-northeast-2.amazonaws.com/hospital/image/" + randomNumber + ".jpg";
	}

	private BusinessHours generateBusinessHours() {

	}

}

