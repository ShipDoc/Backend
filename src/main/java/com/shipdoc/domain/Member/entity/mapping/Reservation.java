package com.shipdoc.domain.Member.entity.mapping;

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
@Table(name = "reservation")
public class Reservation extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "reservation_time")
	private LocalDateTime reservationTime;

	@Column(name = "auto_reservation", nullable = false)
	private Boolean autoReservation;

	@Column(name = "absence_count", nullable = false)
	private Integer absenceCount;

	@Column(name ="phone_number")
	private String phoneNumber;

	@Column(name = "sms_id")
	private String smsId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	// 연관 관계 편의 메서드

	public void changePatient(Patient patient) {
		this.patient = patient;
	}

	public void changeHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public void changeSmsId(String smsId){
		this.smsId = smsId;
	}

	public void changeAbsenceCount(){
		this.absenceCount ++;
	}

	public void changeReservationTime(LocalDateTime reservationTime){
		this.reservationTime = reservationTime;
	}

}
