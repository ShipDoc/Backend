package com.shipdoc.domain.hospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipdoc.domain.Member.entity.mapping.ReviewRecommend;

@Repository
public interface ReviewRecommendRepository extends JpaRepository<ReviewRecommend, Long> {
	boolean existsByMemberIdAndReviewId(Long memberId, Long reviewId);
	Optional<ReviewRecommend> findByMemberIdAndReviewId(Long memberId, Long reviewId);

}
