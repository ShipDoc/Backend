package com.shipdoc.domain.Member.entity;

import com.shipdoc.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Long id; //회원 ID

    @Column(name = "name")
    private String name; // 회원명

    @Column(name = "hodpital")
    private String hospital; //병원명

    @Column(name = "reservation_date")
    private String ReservationDate; //예약 날짜

    @Column(name = "reservation_time")
    private String ReservationTime; //예약 시간

}