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
@Table(name = "hospital")
public class Hospital {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "kakao_id")
	private String kakaoId;

	@Column(name = "hospital_name")
	private String name;

	@Column(name = "hospital_address")
	private String address;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "photo_url")
	private String photoUrl;

	@Column(name = "phone")
	private String phoneNumber;

	@Column(name = "kakao_url")
	private String kakaoUrl;

	@OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FavoriteHospital> favoriteHospitalList;

	@OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviewList;

	@OneToMany(mappedBy = "hospital", cascade = CascadeType.PERSIST)
	private List<Reservation> reservationList;

	@OneToOne(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
	private BusinessHours businessHours;

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

	public void changeBusinessHours(BusinessHours businessHours) {
		this.businessHours = businessHours;
		businessHours.changeHospital(this);
	}
}
