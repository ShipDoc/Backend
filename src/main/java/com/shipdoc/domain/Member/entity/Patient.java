package com.shipdoc.domain.Member.entity;

import java.time.LocalDate;
import java.util.List;

import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.Member.enums.FamilyRelation;
import com.shipdoc.domain.Member.enums.NationalityType;
import com.shipdoc.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "patient")
public class Patient extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nationality_type", nullable = false)
	private NationalityType nationalityType;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "birth", nullable = false)
	private LocalDate birth;

	@Enumerated(EnumType.STRING)
	private FamilyRelation familyRelation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reservation> reservationList;

	// 연관 관계 편의 메서드

	public void changeMember(Member member) {
		this.member = member;
	}

	public void addReservation(Reservation reservation) {
		reservationList.add(reservation);
		reservation.changePatient(this);
	}
}
