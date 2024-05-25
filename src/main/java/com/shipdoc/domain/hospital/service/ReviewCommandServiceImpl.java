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
import com.shipdoc.domain.hospital.repository.ReivewRepository;
import com.shipdoc.domain.hospital.repository.ReviewRecommendRepository;
import com.shipdoc.domain.hospital.web.dto.ReviewRequestDto;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService{
	private final ReivewRepository reivewRepository;
	private final HospitalRepository hospitalRepository;
	private final ReviewRecommendRepository reviewRecommendRepository;

	@Override
	public Review createReview(ReviewRequestDto.createReviewRequestDto request, Member member, Long hospitalId){
		Review review = ReviewConverter.toReview(request);
		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new HospitalNotExistException());
		member.addReview(review);
		hospital.addReview(review);
		return reivewRepository.save(review);
	}

	@Override
	public void addReviewRecommand(Long reviewId, Member member){
		Review review = reivewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotExistException());

		if(reviewRecommendRepository.existsByMemberIdAndReviewId(member.getId(), reviewId)){
			throw new GeneralException(ErrorStatus._EXIST_REVIEW_RECOMMEND);
		}
		ReviewRecommend reviewRecommend = ReviewRecommend.builder().build();

		review.addReviewRecommand(reviewRecommend);
		member.addReviewRecommand(reviewRecommend);
		reviewRecommendRepository.save(reviewRecommend);
	}

	@Override
	public void deleteReviewRecommend (Long reviewId, Member member){
		Review review = reivewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotExistException());

		Optional<ReviewRecommend> reviewRecommendOptional = reviewRecommendRepository.findByMemberIdAndReviewId(
			member.getId(), reviewId);

		if(!reviewRecommendOptional.isPresent()){
			throw new GeneralException(ErrorStatus._REVIEW_RECOMMEND_NOT_EXIST);
		}
		else{
			reviewRecommendRepository.delete(reviewRecommendOptional.get());
		}
	}
}
