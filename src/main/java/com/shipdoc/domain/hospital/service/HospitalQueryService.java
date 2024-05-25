package com.shipdoc.domain.hospital.service;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.hospital.enums.ReviewSortStatus;
import com.shipdoc.domain.hospital.web.dto.HospitalResponseDto;

public interface HospitalQueryService {
	HospitalResponseDto.HospitalReviewPageResponseDto getHospitalReviewList(Integer page, Integer size,
		ReviewSortStatus sortStatus, Long hospitalId, Member member);
}
