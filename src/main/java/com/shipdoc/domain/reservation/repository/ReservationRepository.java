package com.shipdoc.domain.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipdoc.domain.Member.entity.mapping.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	Optional<Reservation> findByPatientIdAndHospitalId(Long patientId, Long hospitalId);
}
