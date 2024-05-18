package com.shipdoc.domain.Member.validation.validator;

import com.shipdoc.domain.Member.validation.annotation.PasswordMatch;
import com.shipdoc.domain.Member.web.dto.MemberRequestDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 비밀번호 확인이 올바른지 체크하는 validator
 */

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		MemberRequestDto.SignupRequestDto request = (MemberRequestDto.SignupRequestDto) value;
		if(request.getPassword().isBlank() || request.getPassword() == null) return true; // 비밀번호가 올바르지 않을 경우 검증 X

		boolean isValid = request.getPasswordCheck() == request.getPassword();
		if(!isValid){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("올바른 이메일 형식으로 작성해주세요").addConstraintViolation();
		}

		return isValid;
	}

	@Override
	public void initialize(PasswordMatch constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
}
