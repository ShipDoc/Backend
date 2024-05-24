package com.shipdoc.domain.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipdoc.domain.hospital.entity.mapping.Review;

@Repository
public interface ReivewRepository extends JpaRepository<Review, Long> {
}
