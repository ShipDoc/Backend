package com.shipdoc.domain.Member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipdoc.domain.Member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByLoginId(String LoginId);
}
