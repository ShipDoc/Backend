package com.shipdoc.domain.hospital.entity.mapping;

import java.util.List;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.mapping.ReviewRecommend;
import com.shipdoc.domain.hospital.entity.Hospital;
import com.shipdoc.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "review")
public class Review extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "content")
	private String content;

	@Column(name = "score")
	private Double score;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewRecommend> reviewRecommendList;

	// 연관 관계 편의 메서드

	public void changeMember(Member member) {
		this.member = member;
	}

	public void changeHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public void addReviewRecommand(ReviewRecommend reviewRecommend) {
		reviewRecommendList.add(reviewRecommend);
		reviewRecommend.changeReview(this);
	}
}
