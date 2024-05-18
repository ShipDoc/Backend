package com.shipdoc.domain.Member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService{

	private final MemberRepository memberRepository;

	@Override
	public Optional<Member> getMemberWithAuthorities(String loginId) {
		Member member = memberRepository.findByLoginId(loginId).orElse(null);
		member.getMemberRoleList().size();
		return Optional.ofNullable(member);
	}
}
