package com.shipdoc.domain.hospital.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.mapping.ReviewRecommend;
import com.shipdoc.domain.hospital.converter.ReviewConverter;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.domain.hospital.exception.HospitalNotExistException;
import com.shipdoc.domain.hospital.exception.ReviewNotExistException;
import com.shipdoc.domain.hospital.repository.HospitalRepository;
import com.shipdoc.domain.hospital.repository.ReviewRecommendRepository;
import com.shipdoc.domain.hospital.repository.ReviewRepository;
import com.shipdoc.domain.hospital.web.dto.ReviewRequestDto;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {
	private final ReviewRepository reviewRepository;
	private final HospitalRepository hospitalRepository;
	private final ReviewRecommendRepository reviewRecommendRepository;

	@Override
	public Review createReview(ReviewRequestDto.createReviewRequestDto request, Member member, Long hospitalId) {
		// 병원 검진을 받은 작성자인지 검증
		// if(member.getPatientList().isEmpty()){
		// 	throw new PatientNotExistException();
		// }
		// consultationRepository.findByPatientIdAndHospitalId(member.getPatientList().get(0).getId(), hospitalId).orElseThrow(() -> new GeneralException(ErrorStatus._FORBIDDEN));
		Review review = ReviewConverter.toReview(request);
		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new HospitalNotExistException());
		member.addReview(review);
		hospital.addReview(review);
		return reviewRepository.save(review);
	}

	@Override
	public Integer addReviewRecommand(Long reviewId, Member member) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotExistException());

		if (reviewRecommendRepository.existsByMemberIdAndReviewId(member.getId(), reviewId)) {
			throw new GeneralException(ErrorStatus._EXIST_REVIEW_RECOMMEND);
		}
		ReviewRecommend reviewRecommend = ReviewRecommend.builder().build();

		review.addReviewRecommand(reviewRecommend);
		member.addReviewRecommend(reviewRecommend);

		reviewRecommendRepository.save(reviewRecommend);
		return reviewRecommendRepository.countByReviewId(review.getId());
	}

	@Override
	public Integer deleteReviewRecommend(Long reviewId, Member member) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotExistException());

		Optional<ReviewRecommend> reviewRecommendOptional = reviewRecommendRepository.findByMemberIdAndReviewId(
			member.getId(), reviewId);

		if (!reviewRecommendOptional.isPresent()) {
			throw new GeneralException(ErrorStatus._REVIEW_RECOMMEND_NOT_EXIST);
		} else {
			reviewRecommendRepository.delete(reviewRecommendOptional.get());
		}

		return reviewRecommendRepository.countByReviewId(review.getId());
	}

	public Integer changeReviewRecommend(Long reviewId, Member member) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotExistException());

		Optional<ReviewRecommend> reviewRecommendOptional = reviewRecommendRepository.findByMemberIdAndReviewId(
			member.getId(), reviewId);
		if (reviewRecommendOptional.isPresent()) {
			ReviewRecommend reviewRecommend = reviewRecommendOptional.get();
			reviewRecommendRepository.delete(reviewRecommend);
		} else {
			ReviewRecommend reviewRecommend = ReviewRecommend.builder().build();

			review.addReviewRecommand(reviewRecommend);
			member.addReviewRecommend(reviewRecommend);

			reviewRecommendRepository.save(reviewRecommend);
		}
		return reviewRecommendRepository.countByReviewId(review.getId());
	}
}
