package com.shipdoc.domain.Member.web.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.Member.converter.MemberConverter;
import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.service.MemberCommandService;
import com.shipdoc.domain.Member.service.MemberQueryService;
import com.shipdoc.domain.Member.service.PatientCommandService;
import com.shipdoc.domain.Member.service.PatientQueryService;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;
import com.shipdoc.domain.Member.web.dto.MemberResponseDto;
import com.shipdoc.global.annotation.LoginMember;
import com.shipdoc.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberRestController {
	private final MemberCommandService memberCommandService;

	private final MemberQueryService memberQueryService;

	private final PatientCommandService patientCommandService;

	private final PatientQueryService patientQueryService;

	/**
	 * 회원가입 기능
	 * => 본인인증 어떻게 진행할것인지
	 */
	@PostMapping("")
	public ApiResponse<MemberResponseDto.SignupResponseDto> signup(
		@Valid @RequestBody MemberRequestDto.SignupRequestDto request) {
		return ApiResponse.onSuccess(MemberConverter.toSignupResponseDto(memberCommandService.signup(request)));
	}

	/**
	 * sms 인증코드 검증
	 */
	@PostMapping("/sms/verify")
	public ApiResponse<String> verifySms(@Valid @RequestBody MemberRequestDto.VerifySmsCodeRequestDto request) {
		memberQueryService.certificateCheck(request.getPhoneNumber(), request.getVerifyCode());

		return ApiResponse.onSuccess("인증에 성공하였습니다.");
	}

	/**
	 * 회원가입 인증코드 발송
	 */
	@PostMapping("/sms")
	public ApiResponse<String> sendSms(@Valid @RequestBody MemberRequestDto.SendSmsRequestDto request) {
		memberCommandService.sendVerifyCode(request);
		return ApiResponse.onSuccess("인증번호를 성공적으로 발송했습니다. 발송된 인증코드는 5분간 유효합니다.");
	}

	/**
	 * 자녀 추가 API
	 */
	@PostMapping("/patient")
	public ApiResponse<MemberResponseDto.AddPatientResponseDto> addPatient(
		@Valid @RequestBody MemberRequestDto.AddPatientRequestDto request, @LoginMember
	Member member) {
		return ApiResponse.onSuccess(memberCommandService.addPatient(request, member));
	}

	/**
	 * 자녀 삭제 API
	 */

	@DeleteMapping("/patient/{patientId}")
	public ApiResponse<String> deletePatient(@PathVariable(name = "patientId") Long patientId,
		@LoginMember Member member) {
		memberCommandService.deletePatient(patientId, member);

		return ApiResponse.onSuccess("성공적으로 삭제했습니다.");
	}

	/**
	 * 주민등록번호 검증 API
	 */
	@PostMapping("/patient/verify")
	public ApiResponse<String> validateRRN(@Valid @RequestBody MemberRequestDto.RRNVerificationRequestDto request) {
		// TODO 본인인증 과정
		return ApiResponse.onSuccess("인증에 성공했습니다.");
	}

	/**
	 * 건강검진 알림 수신 동의
	 */
	// TODO 환자로 변경
	@PutMapping("health-checkup/notification")
	public ApiResponse<String> acceptHeathCheckupNotification(@LoginMember Member member,
		@Valid @RequestBody MemberRequestDto.AcceptHeathCheckupNotificationRequestDto request) {
		patientCommandService.acceptHeathCheckupNotification(member, request.getPhone());
		return ApiResponse.onSuccess("건강검진 알림 수신을 동의하였습니다.");
	}

	/**
	 * 건강검진 알림 수신 거절
	 */
	@DeleteMapping("health-checkup/notification")
	public ApiResponse<String> rejectHeathCheckupNotification(@LoginMember Member member) {
		patientCommandService.rejectHeathCheckupNotification(member);
		return ApiResponse.onSuccess("건강검진 알림 수신을 거절하였습니다.");
	}

	/**
	 * 건강검진 상태 반환
	 */

	@GetMapping("health-checkup/notification")
	public ApiResponse<MemberResponseDto.HealthCheckUpNotificationDto> getHeathCheckupNotificationInfo(
		@LoginMember Member member) {
		return ApiResponse.onSuccess(patientQueryService.getHeathCheckupNotificationInfo(member));
	}

}
