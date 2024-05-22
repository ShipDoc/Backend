package com.shipdoc.domain.Member.service;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;
import com.shipdoc.domain.Member.web.dto.MemberResponseDto;

public interface MemberCommandService {
	Member signup(MemberRequestDto.SignupRequestDto request);
	void updateRefreshToken(Member member, String reIssuedRefreshToken);
	void sendVerifyCode(MemberRequestDto.SendSmsRequestDto request);
	MemberResponseDto.AddPatientResponseDto addPatient(MemberRequestDto.AddPatientRequestDto request, Member member);
	void deletePatient(Long patientId, Member member);
}
