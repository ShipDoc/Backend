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
import com.shipdoc.domain.hospital.web.dto.ReviewResponseDto;
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
	public ReviewResponseDto.ReviewRecommendResponseDto addReviewRecommand(Long reviewId, Member member) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotExistException());

		if (reviewRecommendRepository.existsByMemberIdAndReviewId(member.getId(), reviewId)) {
			throw new GeneralException(ErrorStatus._EXIST_REVIEW_RECOMMEND);
		}
		ReviewRecommend reviewRecommend = ReviewRecommend.builder().build();

		review.addReviewRecommand(reviewRecommend);
		member.addReviewRecommend(reviewRecommend);

		reviewRecommendRepository.save(reviewRecommend);
		Integer recommendCount = reviewRecommendRepository.countByReviewId(review.getId());
		return ReviewResponseDto.ReviewRecommendResponseDto.builder()
			.recommended(true)
			.recommendCount(recommendCount)
			.build();
	}

	@Override
	public ReviewResponseDto.ReviewRecommendResponseDto deleteReviewRecommend(Long reviewId, Member member) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotExistException());

		Optional<ReviewRecommend> reviewRecommendOptional = reviewRecommendRepository.findByMemberIdAndReviewId(
			member.getId(), reviewId);

		if (!reviewRecommendOptional.isPresent()) {
			throw new GeneralException(ErrorStatus._REVIEW_RECOMMEND_NOT_EXIST);
		} else {
			reviewRecommendRepository.delete(reviewRecommendOptional.get());
		}

		Integer recommendCount = reviewRecommendRepository.countByReviewId(review.getId());
		return ReviewResponseDto.ReviewRecommendResponseDto.builder()
			.recommended(false)
			.recommendCount(recommendCount)
			.build();
	}

	public ReviewResponseDto.ReviewRecommendResponseDto changeReviewRecommend(Long reviewId, Member member) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotExistException());

		Optional<ReviewRecommend> reviewRecommendOptional = reviewRecommendRepository.findByMemberIdAndReviewId(
			member.getId(), reviewId);
		boolean recommended;
		if (reviewRecommendOptional.isPresent()) {
			recommended = true;
			ReviewRecommend reviewRecommend = reviewRecommendOptional.get();
			reviewRecommendRepository.delete(reviewRecommend);

		} else {
			recommended = false;
			ReviewRecommend reviewRecommend = ReviewRecommend.builder().build();
			review.addReviewRecommand(reviewRecommend);
			member.addReviewRecommend(reviewRecommend);
			reviewRecommendRepository.save(reviewRecommend);
		}

		Integer recommendCount = reviewRecommendRepository.countByReviewId(review.getId());
		return ReviewResponseDto.ReviewRecommendResponseDto.builder()
			.recommended(!recommended)
			.recommendCount(recommendCount)
			.build();
	}
}
