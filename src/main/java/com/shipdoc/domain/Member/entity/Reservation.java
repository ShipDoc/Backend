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
    private Long id; //예약 ID

    @Column(name = "reservation_date")
    private Timestamp reservationDate; //예약 날짜

    @Column(name = "hospital_id")
    private Long hospitalId; //병원 ID

    @Column(name = "member_id")
    private Long memberId; //회원 ID

}