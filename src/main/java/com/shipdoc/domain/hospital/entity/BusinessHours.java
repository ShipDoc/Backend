package com.shipdoc.domain.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "business_hours")
public class BusinessHours {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "monday", nullable = false)
	private String monday;

	@Column(name = "tuesday", nullable = false)
	private String tuesday;

	@Column(name = "wednesday", nullable = false)
	private String wednesday;

	@Column(name = "thursday", nullable = false)
	private String thursday;

	@Column(name = "friday", nullable = false)
	private String friday;

	@Column(name = "saturday", nullable = false)
	private String saturday;

	@Column(name = "sunday", nullable = false)
	private String sunday;

	@Column(name = "holiday", nullable = false)
	private String holiday;

	@Column(name = "break_time")
	private String breakTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	// 연관관계 편의 메서드

	public void changeHospital(Hospital hospital) {
		this.hospital = hospital;
	}
}
