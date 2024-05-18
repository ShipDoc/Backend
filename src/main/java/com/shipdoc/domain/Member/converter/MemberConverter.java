package com.shipdoc.domain.Member.converter;


import java.util.ArrayList;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;

public class MemberConverter {

	public static Member toMember(MemberRequestDto.SignupRequestDto request, String encodedPassword){
		return Member.builder()
			.login_id(request.getLogin_id())
			.password(encodedPassword)
			.memberRoleList(new ArrayList<>())
			.build();
	}
}
