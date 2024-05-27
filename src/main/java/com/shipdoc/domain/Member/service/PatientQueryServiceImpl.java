package com.shipdoc.domain.Member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.Member.exception.PatientNotExistException;
import com.shipdoc.domain.Member.web.dto.MemberResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PatientQueryServiceImpl implements PatientQueryService {

	@Override
	public MemberResponseDto.HealthCheckUpNotificationDto getHeathCheckupNotificationInfo(Member member) {
		if (member.getPatientList().isEmpty()) {
			throw new PatientNotExistException();
		}
		Patient patient = member.getPatientList().get(0);

		if (patient.getPhoneNumber() == null) {
			return MemberResponseDto.HealthCheckUpNotificationDto.builder()
				.acceptNotification(false)
				.phone(member.getPhone())
				.build();
		}

		if (patient.getPhoneNumber().equals("rejected")) {
			return MemberResponseDto.HealthCheckUpNotificationDto.builder()
				.acceptNotification(false)
				.phone(null)
				.build();
		}

		return MemberResponseDto.HealthCheckUpNotificationDto.builder()
			.acceptNotification(true)
			.phone(patient.getPhoneNumber())
			.build();
	}
}
