package com.shipdoc.domain.Member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipdoc.domain.Member.entity.PhoneCertification;
@Repository
public interface PhoneCertificationRepository extends JpaRepository<PhoneCertification, Long> {
	Optional<PhoneCertification> findByPhoneNumber(String phoneNumber);
	Optional<PhoneCertification> findByCode(String code);

	boolean existsByCode(String code);

}
