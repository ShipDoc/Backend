package com.shipdoc.domain.Member.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.Member.entity.PhoneCertification;
import com.shipdoc.domain.Member.exception.PatientNotExistException;
import com.shipdoc.domain.Member.repository.MemberRepository;
import com.shipdoc.domain.Member.repository.PhoneCertificationRepository;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService{

	private final MemberRepository memberRepository;

	private final PhoneCertificationRepository phoneCertificationRepository;

	@Override
	public Optional<Member> getMemberWithAuthorities(String loginId) {
		Member member = memberRepository.findByLoginId(loginId).orElse(null);
		member.getMemberRoleList().size();
		return Optional.ofNullable(member);
	}

	@Override
	public void certificateCheck(String phoneNumber, String verifyCode){
		PhoneCertification phoneCertification = phoneCertificationRepository.findByCode(verifyCode)
			.orElseThrow(() -> new GeneralException(
				ErrorStatus._INVALID_VERIFICATION_CODE));
		if(!phoneCertification.getPhoneNumber().equals(phoneNumber)){
			throw new GeneralException(ErrorStatus._INVALID_VERIFICATION_CODE);
		}
		if(!isWithinFiveMinute(phoneCertification.getCreatedAt())){
			throw new GeneralException(ErrorStatus._EXPIRED_VERIFICATION_CODE);
		}
	}

	@Override
	public String getUserName(Member member){
		List<Patient> patientList = member.getPatientList();
		if(patientList.isEmpty()){
			throw new PatientNotExistException();
		}
		return patientList.get(0).getName();
	}

	private static boolean isWithinFiveMinute(LocalDateTime timeToCheck) {
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(timeToCheck, now);
		return Math.abs(duration.toMinutes()) <= 5;
	}
}
