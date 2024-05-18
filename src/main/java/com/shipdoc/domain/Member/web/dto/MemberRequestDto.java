package com.shipdoc.domain.Member.web.dto;

import com.shipdoc.domain.Member.validation.annotation.PasswordMatch;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class MemberRequestDto {

	/**
	 * 회원가입 요청 DTO
	 */
	@Getter
	@Setter
	@ToString
	@PasswordMatch
	public static class SignupRequestDto{
		@NotBlank(message = "이메일을 입력해주세요.")
		@Email(message = "올바른 이메일 형식으로 작성해주세요.")
		private String loginId;
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\S]{8,20}$", message = "영문, 숫자를 포함한 8~20자리 이내로 입력해주세요.")
		private String password;
		private String passwordCheck;
	}
}
