package com.shipdoc.domain.Member.converter;

import java.util.ArrayList;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;
import com.shipdoc.domain.Member.web.dto.MemberResponseDto;

public class MemberConverter {

	public static Member toMember(MemberRequestDto.SignupRequestDto request, String encodedPassword,
		String refreshToken) {
		return Member.builder()
			.loginId(request.getLoginId())
			.password(encodedPassword)
			.phone(request.getPhoneNumber())
			.refreshToken(refreshToken)
			.memberRoleList(new ArrayList<>())
			.patientList(new ArrayList<>())
			.favoriteHospitalList(new ArrayList<>())
			.reviewList(new ArrayList<>())
			.reviewRecommendList(new ArrayList<>())
			.build();
	}

	public static MemberResponseDto.SignupResponseDto toSignupResponseDto(Member member) {
		return MemberResponseDto.SignupResponseDto.builder()
			.memberId(member.getId())
			.createdAt(member.getCreatedAt())
			.build();
	}

	public static MemberResponseDto.AddPatientResponseDto toAddPatientResponseDto(Patient patient) {
		return MemberResponseDto.AddPatientResponseDto.builder()
			.patientId(patient.getId())
			.createdAt(patient.getCreatedAt())
			.build();
	}

}
