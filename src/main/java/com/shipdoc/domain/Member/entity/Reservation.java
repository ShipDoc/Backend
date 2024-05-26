package com.shipdoc.domain.Member.entity;

import com.shipdoc.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RESERVATION")
@Builder
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //예약 고유 번호

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime; //예약 날짜(날짜, 시간)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital; // 병원 ID

    @Column(name = "auto_reservation", nullable = false)
    private Boolean autoReservation; //자동 예약 여부

    @Column(name = "absence_count", nullable = false)
    private Integer absenceCount; // 노쇼 횟수

    @Column(name = "sms_id")
    private String smsId; // 예약 문자 발송 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient; // 환자 ID

    public Long getHospitalId() {
        Hospital h = new Hospital();
        Long id = h.getId();
        return id;
    }

    public Long getPatientId() {
        Patient p = new Patient();
        Long id = p.getId();
        return id;
    }
}