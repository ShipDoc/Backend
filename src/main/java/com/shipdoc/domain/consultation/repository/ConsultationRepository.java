package com.shipdoc.domain.consultation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipdoc.domain.consultation.entity.Consultation;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

	Optional<Consultation> findByPatientIdAndHospitalId(Long patientId, Long hospitalId);

	List<Consultation> findByPatientId(Long patientId);
}
