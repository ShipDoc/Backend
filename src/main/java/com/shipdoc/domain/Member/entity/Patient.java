package com.shipdoc.domain.Member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PATIENT")
@Builder
public class Patient {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
}
