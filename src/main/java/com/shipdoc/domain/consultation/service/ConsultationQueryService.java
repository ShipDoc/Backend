package com.shipdoc.domain.consultation.service;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.global.annotation.LoginMember;
import com.shipdoc.global.response.ApiResponse;

public interface ConsultationQueryService {
    ApiResponse<?> getAllConsultation(@LoginMember Member member);
}
