package com.shipdoc.domain.Member.service;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;

public interface MemberCommandService {
	Member signup(MemberRequestDto.SignupRequestDto request);
}
