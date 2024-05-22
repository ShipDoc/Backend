package com.shipdoc.domain.Member.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shipdoc.domain.Member.enums.FamilyRelation;
import com.shipdoc.domain.Member.enums.NationalityType;
import com.shipdoc.domain.Member.validation.annotation.PasswordMatch;
import com.shipdoc.domain.Member.validation.annotation.ValidateRRN;

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
		@Pattern(regexp = "^010\\d{4}\\d{4}$", message = "올바른 전화번호를 입력해 주세요.")
		private String phoneNumber;

		@NotBlank(message = "인증번호를 입력해주세요.")
		private String verifyCode;
	}

	@Getter
	@Setter
	public static class VerifySmsCodeRequestDto {
		@Pattern(regexp = "^010\\d{4}\\d{4}$", message = "올바른 전화번호를 입력해 주세요.")
		private String phoneNumber;
		@NotBlank(message = "인증번호를 입력해주세요.")
		private String verifyCode;
	}

	@Getter
	@Setter
	public static class SendSmsRequestDto{
		@Pattern(regexp = "^010\\d{4}\\d{4}$", message = "올바른 전화번호를 입력해 주세요.")
		private String phoneNumber;
	}

	@Getter
	@Setter
	public static class RRNVerificationRequestDto{
		@JsonProperty("RRN")
		@ValidateRRN
		private String RRN;
	}

	@Getter
	@Setter
	public static class AddPatientRequestDto{
		@JsonProperty("RRN")
		@ValidateRRN
		private String RRN;

		private NationalityType nationalityType;

		@NotBlank(message = "이름을 입력해주세요.")
		private String name;

		
		// 자녀 형식으로만 추가
		// private FamilyRelation familyRelation;
	}

}
