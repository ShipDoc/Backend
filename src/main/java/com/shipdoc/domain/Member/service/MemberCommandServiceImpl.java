package com.shipdoc.domain.Member.service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.converter.MemberConverter;
import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.PhoneCertification;
import com.shipdoc.domain.Member.entity.Role;
import com.shipdoc.domain.Member.entity.mapping.MemberRole;
import com.shipdoc.domain.Member.enums.Authority;
import com.shipdoc.domain.Member.repository.MemberRepository;
import com.shipdoc.domain.Member.repository.PhoneCertificationRepository;
import com.shipdoc.domain.Member.repository.RoleRepository;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;
import com.shipdoc.global.sms.SmsSentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService{


	private final MemberQueryService memberQueryService;
	private final MemberRepository memberRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final SmsSentService smsSentService;

	private final PhoneCertificationRepository phoneCertificationRepository;


	@Override
	public Member signup(MemberRequestDto.SignupRequestDto request){
		memberQueryService.certificateCheck(request.getPhoneNumber(), request.getVerifyCode());

		if(memberRepository.existsByLoginId(request.getLoginId())){
			throw new GeneralException(ErrorStatus._EXIST_LOGINID);
		}

		String encodedPassword = passwordEncoder.encode(request.getPassword());
		Member member = MemberConverter.toMember(request, encodedPassword);

		// 권한 부여
		Role role = roleRepository.findById(Authority.ROLE_USER.getId()).get();

		MemberRole memberRole = MemberRole.builder().build();
		role.addMemberRole(memberRole);
		member.addMemberRole(memberRole);

		return memberRepository.save(member);
	}

	@Override
	public void updateRefreshToken(Member member, String reIssuedRefreshToken) {
		member.changeRefreshToken(reIssuedRefreshToken);
		memberRepository.saveAndFlush(member);
	}

	/**
	 * 인증 코드 sms 발송
	 * 만약 3분이내에 발송된 메세지가 있으면 에러 응답
	 */
	@Override
	public void sendVerifyCode(MemberRequestDto.SendSmsRequestDto request){
		Optional<PhoneCertification> certificationOptional = phoneCertificationRepository.findByPhoneNumber(
			request.getPhoneNumber());
		// 코드가 존재하면 3분 이내에 발송된 코드가 있는지 검증 -> 만약 3분이 지났다면 데이터베이스에서 삭제
		if(certificationOptional.isPresent()){
			PhoneCertification phoneCertification = certificationOptional.get();
			if(isWithinThreeMinute(phoneCertification.getCreatedAt())) throw new GeneralException(ErrorStatus._EXIST_CODE_REQUEST);
			else{
				phoneCertificationRepository.delete(phoneCertification);
			}
		}
		String verificationCode = generateVerificationCode();

		// 생성된 코드가 이미 데이터베이스에 있는지 검증
		while(phoneCertificationRepository.existsByCode(verificationCode)){
			verificationCode = generateVerificationCode();
		}
		sendCodeMessage(verificationCode, request.getPhoneNumber());

		// TODO 저장과정 메세지 큐 처리하기

		// 데이터베이스에 저장
		PhoneCertification certification = PhoneCertification.builder()
			.phoneNumber(request.getPhoneNumber())
			.code(verificationCode)
			.build();

		phoneCertificationRepository.save(certification);
	}


	private void sendCodeMessage(String verificationCode, String phoneNumber){
		String messageText = "[쉽닥]\n"
			+ "본인인증 인증번호\n"
			+ "(" + verificationCode + ")를 입력해주세요.";

		smsSentService.sendMessage(phoneNumber, messageText);
	}

	private static String generateVerificationCode() {
		Random random = new Random();
		int verificationCode = 100000 + random.nextInt(900000);
		return String.valueOf(verificationCode);
	}

	private static boolean isWithinThreeMinute(LocalDateTime timeToCheck) {
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(timeToCheck, now);
		return Math.abs(duration.toMinutes()) <= 3;
	}
}

