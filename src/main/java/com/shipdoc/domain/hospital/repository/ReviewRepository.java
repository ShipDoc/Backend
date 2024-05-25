package com.shipdoc.domain.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shipdoc.domain.hospital.entity.mapping.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	Page<Review> findByHospitalId(Long hospitalId, Pageable pageable);

	// 추천 순 조회 (리뷰의 추천 데이터 개수 내림차순)
	@Query("SELECT r FROM Review r LEFT JOIN r.reviewRecommendList rr WHERE r.hospital.id = :hospitalId GROUP BY r ORDER BY COUNT(rr) DESC")
	Page<Review> findAllByHospitalIdOrderByReviewRecommendCountDesc(@Param("hospitalId") Long hospitalId,
		Pageable pageable);

}
