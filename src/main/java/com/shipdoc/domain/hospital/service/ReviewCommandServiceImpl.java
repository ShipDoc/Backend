package com.shipdoc.domain.hospital.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.converter.ReviewConverter;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.exception.HospitalNotExistException;
import com.shipdoc.domain.hospital.repository.HospitalRepository;
import com.shipdoc.domain.hospital.repository.ReivewRepository;
import com.shipdoc.domain.hospital.web.dto.ReviewRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService{
	private final ReivewRepository reivewRepository;
	private final HospitalRepository hospitalRepository;

	@Override
	public Review createReview(ReviewRequestDto.createReviewRequestDto request, Member member, Long hospitalId){
		Review review = ReviewConverter.toReview(request);
		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new HospitalNotExistException());
		member.addReview(review);
		hospital.addReview(review);
		return reivewRepository.save(review);
	}
}
