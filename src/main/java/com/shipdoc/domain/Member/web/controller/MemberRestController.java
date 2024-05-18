package com.shipdoc.domain.Member.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.Member.converter.MemberConverter;
import com.shipdoc.domain.Member.service.MemberCommandService;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;
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

	/**
	 * 회원가입 기능
	 * => 본인인증 어떻게 진행할것인지
	 */
	@PostMapping("")
	public ApiResponse<?> signup(@Valid @RequestBody MemberRequestDto.SignupRequestDto request){
		return ApiResponse.onSuccess(MemberConverter.toSignupResponseDto(memberCommandService.signup(request)));
	}


}
