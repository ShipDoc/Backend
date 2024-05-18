package com.shipdoc.domain.Member.service;

import java.util.Optional;

import com.shipdoc.domain.Member.entity.Member;

public interface MemberQueryService {
	Optional<Member> getMemberWithAuthorities(String loginId);
}
