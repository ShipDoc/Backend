package com.shipdoc.domain.Member.entity;

import com.shipdoc.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RESERVATION")
@Builder
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id; //예약 고유 번호

    @Column(name = "reservation_date", nullable = false)
    private Timestamp reservationDate; //예약 날짜(날짜, 시간)

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital; // 병원 참조

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient; // 환자 참조

    @Column(name = "auto_reservation", nullable = false)
    private Boolean autoReservation; //자동 예약 여부

    @Column(name = "absence_count", nullable = false)
    private Long absenceCount; // 노쇼 횟수

    public Long getHospitalId() {
        return hospital.getHospitalId();
    }

    public Long getPatientId() {
        return patient.getPatientId();
    }
}