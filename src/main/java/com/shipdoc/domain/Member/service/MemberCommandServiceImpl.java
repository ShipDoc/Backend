package com.shipdoc.domain.Member.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.converter.MemberConverter;
import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.repository.MemberRepository;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService{

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Member signup(MemberRequestDto.SignupRequestDto request){
		// TODO 본인인증 과정 추가
		if(memberRepository.existsByLoginId(request.getLogin_id())){
			throw new GeneralException(ErrorStatus._EXIST_LOGINID);
		}

		String encodedPassword = passwordEncoder.encode(request.getPassword());
		Member member = MemberConverter.toMember(request, encodedPassword);

		return memberRepository.save(member);
	}

}

