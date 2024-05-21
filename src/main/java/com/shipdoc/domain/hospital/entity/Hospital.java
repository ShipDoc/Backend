package com.shipdoc.domain.hospital.entity;

import java.util.List;

import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.hospital.entity.mapping.FavoriteHospital;
import com.shipdoc.domain.hospital.entity.mapping.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "hospital")
public class Hospital {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "kakao_id")
	private Long kakaoId;

	@Column(name = "hospital_name")
	private String name;

	@Column(name = "hospital_address")
	private String address;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
	List<FavoriteHospital> favoriteHospitalList;

	@OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Review> reviewList;

	@OneToMany(mappedBy = "hospital", cascade = CascadeType.PERSIST)
	List<Reservation> reservationList;

	// 연관 관계 편의 메서드

	public void addFavoriteHospital(FavoriteHospital favoriteHospital) {
		favoriteHospitalList.add(favoriteHospital);
		favoriteHospital.changeHospital(this);
	}

	public void addReview(Review review) {
		reviewList.add(review);
		review.changeHospital(this);
	}

	public void addReservation(Reservation reservation) {
		reservationList.add(reservation);
		reservation.changeHospital(this);
	}

}
