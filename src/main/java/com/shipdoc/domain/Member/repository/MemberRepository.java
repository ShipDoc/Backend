package com.shipdoc.domain.Member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipdoc.domain.Member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByLoginId(String LoginId);

	Optional<Member> findByRefreshToken(String refreshToken);

	Optional<Member> findByLoginId(String loginId);

}
