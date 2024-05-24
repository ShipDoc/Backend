package com.shipdoc.domain.Member.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.converter.MemberConverter;
import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.Member.entity.PhoneCertification;
import com.shipdoc.domain.Member.entity.Role;
import com.shipdoc.domain.Member.entity.mapping.MemberRole;
import com.shipdoc.domain.Member.enums.Authority;
import com.shipdoc.domain.Member.enums.FamilyRelation;
import com.shipdoc.domain.Member.enums.NationalityType;
import com.shipdoc.domain.Member.exception.PatientNotExistException;
import com.shipdoc.domain.Member.repository.MemberRepository;
import com.shipdoc.domain.Member.repository.PatientRepository;
import com.shipdoc.domain.Member.repository.PhoneCertificationRepository;
import com.shipdoc.domain.Member.repository.RoleRepository;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;
import com.shipdoc.domain.Member.web.dto.MemberResponseDto;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;
import com.shipdoc.global.security.jwt.JwtService;
import com.shipdoc.global.sms.SmsSentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

	private final MemberQueryService memberQueryService;
	private final MemberRepository memberRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final SmsSentService smsSentService;
	private final JwtService jwtService;
	private final PatientRepository patientRepository;

	private final PhoneCertificationRepository phoneCertificationRepository;

	@Override
	public Member signup(MemberRequestDto.SignupRequestDto request) {
		memberQueryService.certificateCheck(request.getPhoneNumber(), request.getVerifyCode());

		if (memberRepository.existsByLoginId(request.getLoginId())) {
			throw new GeneralException(ErrorStatus._EXIST_LOGINID);
		}

		String encodedPassword = passwordEncoder.encode(request.getPassword());
		Member member = MemberConverter.toMember(request, encodedPassword, jwtService.createRefreshToken());

		// 환자 데이터 작성 및 추가
		Patient patient = Patient.builder()
			.familyRelation(FamilyRelation.SELF)
			.nationalityType(NationalityType.DOMESTIC_RESIDENT)
			.name(request.getName())
			.birth(toLocalDate(request.getBirth()))
			.reservationList(new ArrayList<>())
			.build();

		member.addPatient(patient);

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
	public void sendVerifyCode(MemberRequestDto.SendSmsRequestDto request) {
		Optional<PhoneCertification> certificationOptional = phoneCertificationRepository.findByPhoneNumber(
			request.getPhoneNumber());
		// 코드가 존재하면 3분 이내에 발송된 코드가 있는지 검증 -> 만약 3분이 지났다면 데이터베이스에서 삭제
		if (certificationOptional.isPresent()) {
			PhoneCertification phoneCertification = certificationOptional.get();
			if (isWithinThreeMinute(phoneCertification.getCreatedAt()))
				throw new GeneralException(ErrorStatus._EXIST_CODE_REQUEST);
			else {
				phoneCertificationRepository.delete(phoneCertification);
			}
		}
		String verificationCode = generateVerificationCode();

		// 생성된 코드가 이미 데이터베이스에 있는지 검증
		while (phoneCertificationRepository.existsByCode(verificationCode)) {
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

	@Override
	public MemberResponseDto.AddPatientResponseDto addPatient(MemberRequestDto.AddPatientRequestDto request,
		Member member) {
		Patient patient = Patient.builder()
			.nationalityType(request.getNationalityType())
			.name(request.getName())
			.birth(extractBirthInRRN(request.getRRN()))
			.familyRelation(FamilyRelation.CHILD) // 자녀로 고정 => 추후 필요한 경우 변경
			.build();

		member.addPatient(patient);

		return MemberConverter.toAddPatientResponseDto(patientRepository.save(patient));
	}

	@Override
	public void deletePatient(Long patientId, Member member) {
		// 존재하는 환자 데이터인지 검증
		Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotExistException());

		// 만약 로그인 된 사용자에게 속한 환자 데이터가 아니라면 접근 권한 에러 발생
		if (patient.getMember() != member)
			throw new GeneralException(ErrorStatus._PATIENT_DELETE_FORBIDDEN);
		patientRepository.delete(patient);
	}

	private LocalDate extractBirthInRRN(String rrn) {
		String birthDatePart = rrn.substring(0, 6);
		char centuryIndicator = rrn.charAt(6);

		String birthYearPrefix;
		switch (centuryIndicator) {
			case '1':
			case '2': // 1900-1999
				birthYearPrefix = "19";
				break;
			case '3':
			case '4': // 2000-2099
				birthYearPrefix = "20";
				break;
			default:
				birthYearPrefix = "18";
				break;
		}

		String fullBirthDate = birthYearPrefix + birthDatePart;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.parse(fullBirthDate, formatter);
	}

	private void sendCodeMessage(String verificationCode, String phoneNumber) {
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

	private static LocalDate toLocalDate(String birth) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.parse(birth, formatter);
	}

}

