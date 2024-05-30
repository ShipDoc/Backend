package com.shipdoc.domain.Member.service;

import java.util.Optional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;

public interface MemberQueryService {
	Optional<Member> getMemberWithAuthorities(String loginId);
	void certificateCheck(String phoneNumber, String verifyCode);
	String getUserName(Member member);



}
