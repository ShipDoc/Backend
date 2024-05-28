package com.shipdoc.domain.hospital.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.exception.PatientNotExistException;
import com.shipdoc.domain.hospital.converter.HospitalConverter;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.enums.ReviewSortStatus;
import com.shipdoc.domain.hospital.exception.HospitalNotExistException;
import com.shipdoc.domain.hospital.repository.HospitalRepository;
import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;
import com.shipdoc.domain.hospital.web.dto.ReviewResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HospitalQueryServiceImpl implements HospitalQueryService {
	private final HospitalRepository hospitalRepository;
	private final ReviewQueryService reviewQueryService;

	@Override
	public HospitalResponseDto.HospitalReviewPageResponseDto getHospitalReviewList(Integer page, Integer size,
		ReviewSortStatus sortStatus, Long hospitalId, Member member) {

		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new HospitalNotExistException());
		Double totalRate = hospitalRepository.findAverageScoreByHospitalId(hospitalId);

		Page<Review> reviewPage = reviewQueryService.getReviewPage(sortStatus, page, size, hospitalId);

		List<ReviewResponseDto.ReviewDetailResponseDto> reviewDetailResponseDtoList = reviewQueryService.getReviewDetailList(
			member, reviewPage);

		return HospitalConverter.toHospitalReviewPageResponseDto(hospital, reviewDetailResponseDtoList, totalRate,
			reviewPage);
	}

	@Override
	public HospitalResponseDto.HospitalDetailResponseDto getHospitalDetail(Long hospitalId, Member member) {
		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new HospitalNotExistException());

		Page<Review> reviewPage = reviewQueryService.getReviewPage(ReviewSortStatus.RECOMMEND, 0, 3, hospitalId);
		Double totalRate = hospitalRepository.findAverageScoreByHospitalId(hospitalId);

		List<ReviewResponseDto.ReviewDetailResponseDto> reviewList = reviewQueryService.getReviewDetailList(member,
			reviewPage);
		return HospitalConverter.toHospitalDetailResponseDto(hospital, reviewList, totalRate);
	}

	@Override
	public HospitalResponseDto.GetReservationDetailsResponseDto getReservationDetails(Long hospitalId, Member member) {
		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new HospitalNotExistException());
		String hours = hospital.getBusinessHours().getTodayHours();
		String startTime = null;
		String endTime = null;
		if (!hours.equals("휴무")) {
			String[] split = hours.split(" ~ ");
			startTime = split[0];
			endTime = split[1];
		}

		if (member.getPatientList().isEmpty()) {
			throw new PatientNotExistException();
		}

		return HospitalResponseDto.GetReservationDetailsResponseDto.builder()
			.startTime(startTime)
			.endTime(endTime)
			.phone(member.getPhone())
			.name(member.getPatientList().get(0).getName())
			.build();
	}

}
