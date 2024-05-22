package com.shipdoc.domain.hospital.service;

import java.util.List;

import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;
import com.shipdoc.global.kakao.dto.KakaoResponseDto;

public interface HospitalCommandService {
	List<HospitalResponseDto.HospitalDetailResponseDto> saveNotExistHospitals(
		List<KakaoResponseDto.PlaceDetail> placeDetailList);
}