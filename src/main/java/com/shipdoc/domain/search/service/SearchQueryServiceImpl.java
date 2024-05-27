package com.shipdoc.domain.search.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shipdoc.domain.hospital.service.HospitalCommandService;
import com.shipdoc.domain.search.converter.SearchConverter;
import com.shipdoc.domain.search.enums.MedicalDepartment;
import com.shipdoc.domain.search.enums.Symptom;
import com.shipdoc.domain.search.web.dto.SearchRequestDto;
import com.shipdoc.domain.search.web.dto.SearchResponseDto;
import com.shipdoc.global.kakao.NearbySearchService;
import com.shipdoc.global.kakao.dto.KakaoResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchQueryServiceImpl implements SearchQueryService {
	private final NearbySearchService nearbySearchService;
	private final HospitalCommandService hospitalCommandService;

	@Override
	public SearchResponseDto.SearchQueryResponseDto getNearbyHospitalWithSymptom(
		SearchRequestDto.SearchSymptomRequestDto request) {
		List<MedicalDepartment> categoryList = request.getSymptomList()
			.stream()
			.map(Symptom::getDepartment)
			.collect(Collectors.toSet()).stream().collect(Collectors.toList());

		List<KakaoResponseDto.PlaceDetail> placeDetailList = nearbySearchService.findByCategoryList(categoryList,
			request.getLatitude(), request.getLongitude(), request.getSize());
		Collections.sort(placeDetailList);

		return SearchConverter.toSearchQueryResponseDto(
			hospitalCommandService.saveNotExistHospitals(placeDetailList, request.getSort()));
	}

	@Override
	public SearchResponseDto.SearchQueryResponseDto getNearbyHospitalWithCategory(
		SearchRequestDto.SearchCategoryRequestDto request) {
		List<KakaoResponseDto.PlaceDetail> placeDetailList;

		boolean hasOtherCategory = false;
		for (MedicalDepartment department : request.getCategory()) {
			if (department == MedicalDepartment.OTHERS) {
				hasOtherCategory = true;
			}
		}

		if (hasOtherCategory) {
			placeDetailList = nearbySearchService.findByKeyword(
				request.getKeyword(), request.getLatitude(), request.getLongitude(), request.getSize());
		} else {
			placeDetailList = nearbySearchService.findByCategoryList(request.getCategory(), request.getLatitude(),
				request.getLongitude(), request.getSize());
		}
		Collections.sort(placeDetailList);

		return SearchConverter.toSearchQueryResponseDto(
			hospitalCommandService.saveNotExistHospitals(placeDetailList, request.getSort()));
	}

	@Override
	public SearchResponseDto.SearchQueryResponseDto getNearbyHospitalWithHealthCheckup(
		SearchRequestDto.SearchNearbyHospitalRequestDto request) {
		List<KakaoResponseDto.PlaceDetail> placeDetailList = nearbySearchService.findByKeyword("내과",
			request.getLatitude(),
			request.getLongitude(), request.getSize());

		Collections.sort(placeDetailList);
		return SearchConverter.toSearchQueryResponseDto(
			hospitalCommandService.saveNotExistHospitals(placeDetailList, request.getSort()));
	}

	@Override
	public SearchResponseDto.SearchQueryResponseDto getAllNearbyHospital(
		SearchRequestDto.SearchNearbyHospitalRequestDto request) {
		List<KakaoResponseDto.PlaceDetail> placeDetailList = nearbySearchService.findByKeyword("병원",
			request.getLatitude(),
			request.getLongitude(), request.getSize());

		Collections.sort(placeDetailList);
		return SearchConverter.toSearchQueryResponseDto(
			hospitalCommandService.saveNotExistHospitals(placeDetailList, request.getSort()));
	}
}
