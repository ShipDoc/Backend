package com.shipdoc.domain.hospital.service;

import java.util.List;

import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;
import com.shipdoc.domain.search.enums.HospitalSortStatus;
import com.shipdoc.global.kakao.dto.KakaoResponseDto;

public interface HospitalCommandService {
	List<HospitalResponseDto.HospitalPreviewResponseDto> saveNotExistHospitals(
		List<KakaoResponseDto.PlaceDetail> placeDetailList, HospitalSortStatus sort);
}
