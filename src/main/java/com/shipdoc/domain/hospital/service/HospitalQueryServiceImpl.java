package com.shipdoc.domain.hospital.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.converter.HospitalConverter;
import com.shipdoc.domain.hospital.converter.ReviewConverter;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.enums.ReviewSortStatus;
import com.shipdoc.domain.hospital.exception.HospitalNotExistException;
import com.shipdoc.domain.hospital.repository.HospitalRepository;
import com.shipdoc.domain.hospital.repository.ReviewRecommendRepository;
import com.shipdoc.domain.hospital.repository.ReviewRepository;
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
	private final ReviewRepository reviewRepository;
	private final ReviewRecommendRepository reviewRecommendRepository;

	@Override
	public HospitalResponseDto.HospitalReviewPageResponseDto getHospitalReviewList(Integer page, Integer size,
		ReviewSortStatus sortStatus, Long hospitalId, Member member) {

		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new HospitalNotExistException());
		Double totalRate = hospitalRepository.findAverageScoreByHospitalId(hospitalId);

		List<ReviewResponseDto.ReviewDetailResponseDto> reviewDetailResponseDtoList = new ArrayList<>();

		Sort sort = null;
		if (sortStatus == ReviewSortStatus.LATEST) {
			sort = Sort.by(Sort.Direction.DESC, "createdAt");
		} else if (sortStatus == ReviewSortStatus.TOP_SCORE) {
			sort = Sort.by(Sort.Direction.DESC, "score");
		} else if (sortStatus == ReviewSortStatus.LOW_SCORE) {
			sort = Sort.by(Sort.Direction.ASC, "score");
		}

		Page<Review> reviewPage;

		if (sort != null) {
			Pageable pageable = PageRequest.of(page, size, sort);
			reviewPage = reviewRepository.findByHospitalId(hospitalId, pageable);
		} else {
			PageRequest pageable = PageRequest.of(page, size);
			reviewPage = reviewRepository.findAllByHospitalIdOrderByReviewRecommendCountDesc(hospitalId, pageable);
		}

		for (Review review : reviewPage) {
			String name = null;
			Boolean recommended = reviewRecommendRepository.existsByMemberIdAndReviewId(member.getId(), review.getId());
			if (review.getMember() != null) {
				Member writer = review.getMember();
				if (!writer.getPatientList().isEmpty()) {
					name = writer.getPatientList().get(0).getName();
				}
			}
			if (name == null) {
				log.error("리뷰 작성자의 정보가 존재하지 않습니다. reviewId = {}", review.getId());
			}
			reviewDetailResponseDtoList.add(ReviewConverter.toReviewDetailResponseDto(review, name, recommended,
				review.getReviewRecommendList().size()));
		}

		return HospitalConverter.toHospitalReviewPageResponseDto(hospital, reviewDetailResponseDtoList, totalRate,
			reviewPage);
	}
}
