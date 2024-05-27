package com.shipdoc.domain.consultation.entity;

import java.time.LocalDateTime;

import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "treatment")
public class Consultation extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "reservation_time", nullable = false)
	private LocalDateTime reservationTime;

	@Column(name = "visit_count", nullable = false)
	private Integer visitCount;

	@Column(name = "diagnosis", nullable = false)
	private String diagnosis;

	@Column(name = "ai_recommend")
	private String aiRecommend;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospitalId")
	private Hospital hospital;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patientId")
	private Patient patient;

	// 연관 관계 편의 메서드

	public void changePatient(Patient patient) {
		this.patient = patient;
	}

	public void changeHospital(Hospital hospital) {
		this.hospital = hospital;
	}
}
