package com.shipdoc.domain.Member.entity.mapping;

import org.hibernate.annotations.Fetch;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.entity.mapping.Review;
import com.shipdoc.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRecommend extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;


	// 연관 관계 편의 메서드

	public void changeMember(Member member){
		this.member = member;
	}

	public void changeReview(Review review){
		this.review = review;
	}
}
