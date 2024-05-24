package com.shipdoc.domain.reservation.validation.validator;

import java.util.regex.Pattern;

import com.shipdoc.domain.reservation.validation.annotation.PhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isBlank())
			return true;
		Pattern pattern = Pattern.compile("^01[016789]\\d{7}$"); // 세번째 자리는 016789만 가능

		boolean isValid = pattern.matcher(value).matches();

		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("올바른 전화번호를 입력해주세요.").addConstraintViolation();
		}

		return isValid;
	}

	@Override
	public void initialize(PhoneNumber constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
}
