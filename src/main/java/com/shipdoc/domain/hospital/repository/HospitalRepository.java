package com.shipdoc.domain.hospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shipdoc.domain.hospital.entity.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	Optional<Hospital> findByKakaoId(String kakaoId);

	Optional<Hospital> findById(Long hospitalId);

	// 병원의 리뷰 점수 평균을 한자리수까지만 가져오는 쿼리
	@Query("SELECT ROUND(AVG(r.score), 1) FROM Review r WHERE r.hospital.id = :hospitalId")
	Double findAverageScoreByHospitalId(@Param("hospitalId") Long hospitalId);
}
