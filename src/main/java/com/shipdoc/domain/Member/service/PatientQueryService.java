package com.shipdoc.domain.Member.service;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.web.dto.MemberResponseDto;

public interface PatientQueryService {
	MemberResponseDto.HealthCheckUpNotificationDto getHeathCheckupNotificationInfo(Member member);
}
