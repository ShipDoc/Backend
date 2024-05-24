package com.shipdoc.domain.Member.entity;

import java.util.List;

import com.shipdoc.domain.Member.entity.mapping.MemberRole;
import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.Member.entity.mapping.ReviewRecommend;
import com.shipdoc.domain.hospital.entity.mapping.FavoriteHospital;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")

public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "login_id", nullable = false)
	private String loginId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "phone")
	private String phone;

	@Column(name = "refresh_token")
	private String refreshToken;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MemberRole> memberRoleList;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private	List<FavoriteHospital> favoriteHospitalList;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviewList;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Patient> patientList;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewRecommend> reviewRecommendList;

	// 연관 관계 편의 메서드
	public void addMemberRole(MemberRole memberRole) {
		memberRoleList.add(memberRole);
		memberRole.setMember(this);
	}

	public void changeRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	// 연관 관계 편의 메서드

	public void addFavoriteHospital(FavoriteHospital favoriteHospital) {
		favoriteHospitalList.add(favoriteHospital);
		favoriteHospital.changeMember(this);
	}

	public void addReview(Review review) {
		reviewList.add(review);
		review.changeMember(this);
	}

	public void addPatient(Patient patient){
		patientList.add(patient);
		patient.changeMember(this);
	}

}
