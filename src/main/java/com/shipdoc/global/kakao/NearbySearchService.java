package com.shipdoc.global.kakao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipdoc.domain.search.enums.HospitalSortCriteria;
import com.shipdoc.domain.search.enums.MedicalDepartment;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;
import com.shipdoc.global.kakao.dto.KakaoResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NearbySearchService {

	@Value("${cloud.kakao.map-search.api-key}")
	private String apiKey;

	private final ObjectMapper objectMapper;

	public List<KakaoResponseDto.PlaceDetail> findByCategoryList(List<MedicalDepartment> departmentList,
		double latitude,
		double longitude, Long size, String keyword, HospitalSortCriteria sort) {
		Long remain = size;
		Long listSize = (long)departmentList.size() + 1;

		List<KakaoResponseDto.PlaceDetail> placeDetailList = new ArrayList<>();
		for (MedicalDepartment department : departmentList) {
			Long elementSize = remain / listSize;
			for (KakaoResponseDto.PlaceDetail placeDetail : findByKeyword(department.getKoreanName(), latitude,
				longitude, elementSize, sort)) {
				placeDetailList.add(placeDetail);
			}
			listSize--;
			remain -= elementSize;
		}

		Long elementSize = remain;
		for (KakaoResponseDto.PlaceDetail placeDetail : findByKeyword(keyword, latitude,
			longitude, elementSize, sort)) {
			placeDetailList.add(placeDetail);
		}

		return placeDetailList;
	}

	public List<KakaoResponseDto.PlaceDetail> findByCategoryList(List<MedicalDepartment> departmentList,
		double latitude,
		double longitude, Long size, HospitalSortCriteria sort) {

		Long remain = size;
		Long listSize = (long)departmentList.size();

		List<KakaoResponseDto.PlaceDetail> placeDetailList = new ArrayList<>();
		for (MedicalDepartment department : departmentList) {
			Long elementSize = remain / listSize;
			for (KakaoResponseDto.PlaceDetail placeDetail : findByKeyword(department.getKoreanName(), latitude,
				longitude, elementSize, sort)) {
				placeDetailList.add(placeDetail);
			}
			listSize--;
			remain -= elementSize;
		}

		return placeDetailList;
	}

	public List<KakaoResponseDto.PlaceDetail> findByKeyword(String keyword, double latitude, double longitude,
		Long size, HospitalSortCriteria sort) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + apiKey);

		HttpEntity<String> header = new HttpEntity<>(headers);

		String url =
			"https://dapi.kakao.com/v2/local/search/keyword.json?y=" + latitude + "&x=" + longitude
				+ "&radius=10000&category_group_code=HP8&sort=" + sort.getValue() + "&query=" + keyword + "&size="
				+ size;

		ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, header, String.class);

		try {
			// JSON 문자열을 Response 객체로 변환
			KakaoResponseDto response = objectMapper.readValue(exchange.getBody(), KakaoResponseDto.class);

			// Response 객체에서 documents 리스트를 가져오기
			List<KakaoResponseDto.PlaceDetail> places = response.getPlaceDetailList();
			return places;

		} catch (IOException e) {
			log.error("주변 검색 API 요청 도중 에러가 발생했습니다. {}", e);
			throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
		}
	}

}
