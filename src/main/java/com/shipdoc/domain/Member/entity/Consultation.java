package com.shipdoc.domain.Member.entity;

import com.shipdoc.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CONSULTATION")
@Builder
public class Consultation extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    @Column(name = "visit_count", nullable = false)
    private Integer visitCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital; // 병원 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient; // 환자 ID

    @Column(name = "diagnosis", nullable = false)
    private String diagnosis; // 진료 결과

    @Column(name = "ai_recommend")
    private String aiRecommend; // AI 추천 내용

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
