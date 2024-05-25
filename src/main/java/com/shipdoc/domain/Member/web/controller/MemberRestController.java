package com.shipdoc.domain.Member.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.Member.converter.MemberConverter;
import com.shipdoc.domain.Member.service.MemberCommandService;
import com.shipdoc.domain.Member.service.MemberQueryService;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;
import com.shipdoc.domain.Member.web.dto.MemberResponseDto;
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



	/**
	 * 회원가입 기능
	 * => 본인인증 어떻게 진행할것인지
	 */
	@PostMapping("")
	public ApiResponse<MemberResponseDto.SignupResponseDto> signup(@Valid @RequestBody MemberRequestDto.SignupRequestDto request){
		return ApiResponse.onSuccess(MemberConverter.toSignupResponseDto(memberCommandService.signup(request)));
	}

	/**
	 * sms 인증코드 검증
	 */
	@PostMapping("/sms/verify")
	public ApiResponse<String> verifySms(@Valid @RequestBody MemberRequestDto.VerifySmsCodeRequestDto request){
		memberQueryService.certificateCheck(request.getPhoneNumber(), request.getVerifyCode());

		return ApiResponse.onSuccess("인증에 성공하였습니다.");
	}

	/**
	 * 회원가입 인증코드 발송
	 */
	@PostMapping("/sms")
	public ApiResponse<String> sendSms(@Valid @RequestBody MemberRequestDto.SendSmsRequestDto request){
		memberCommandService.sendVerifyCode(request);
		return ApiResponse.onSuccess("인증번호를 성공적으로 발송했습니다. 발송된 인증코드는 5분간 유효합니다.");
	}


}
