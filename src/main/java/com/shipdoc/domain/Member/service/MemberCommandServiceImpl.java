package com.shipdoc.domain.Member.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.converter.MemberConverter;
import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Role;
import com.shipdoc.domain.Member.entity.mapping.MemberRole;
import com.shipdoc.domain.Member.enums.Authority;
import com.shipdoc.domain.Member.repository.MemberRepository;
import com.shipdoc.domain.Member.repository.RoleRepository;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService{

	private final MemberRepository memberRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Member signup(MemberRequestDto.SignupRequestDto request){
		// TODO 본인인증 과정 추가
		if(memberRepository.existsByLoginId(request.getLoginId())){
			throw new GeneralException(ErrorStatus._EXIST_LOGINID);
		}

		String encodedPassword = passwordEncoder.encode(request.getPassword());
		Member member = MemberConverter.toMember(request, encodedPassword);

		// 권한 부여
		Role role = roleRepository.findById(Authority.ROLE_USER.getId()).get();

		MemberRole memberRole = MemberRole.builder().build();
		role.addMemberRole(memberRole);
		member.addMemberRole(memberRole);

		return memberRepository.save(member);
	}

	@Override
	public void updateRefreshToken(Member member, String reIssuedRefreshToken) {
		member.changeRefreshToken(reIssuedRefreshToken);
		memberRepository.saveAndFlush(member);
	}

}

