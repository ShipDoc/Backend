package com.shipdoc.domain.Member.validation.validator;

import java.util.BitSet;

import com.shipdoc.domain.Member.validation.annotation.ValidateRRN;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateRRNValidator implements ConstraintValidator<ValidateRRN, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = validateRRN(value);

		if(!isValid){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("주민등록번호가 올바르지 않습니다.").addConstraintViolation();
		}
		return isValid;
	}

	@Override
	public void initialize(ValidateRRN constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	/**
	 * 올바른 주민등록번호 형식인지 검증
	 * 13자리, 마지막 자리와 이전 12자리의 가중치 합이 같은지 검증
	 */
	private boolean validateRRN(String rrn) {
		if (rrn == null || rrn.length() != 13 || !rrn.matches("\\d{13}")) {
			return false;
		}

		String birthDatePart = rrn.substring(0, 6);
		char centuryIndicator = rrn.charAt(6);
		if(centuryIndicator == 0 || centuryIndicator > '4') return false;

		int birthMonth = Integer.parseInt(birthDatePart.substring(2, 4));
		if(birthMonth == 0 || birthMonth > 12) return false;

		int birthDay = Integer.parseInt(birthDatePart.substring(4, 6));
		if(birthDay == 0 || birthDay > 31) return false;

		int[] weights = {2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5};
		int sum = 0;

		for (int i = 0; i < 12; i++) {
			sum += (rrn.charAt(i) - '0') * weights[i];
		}

		int checkDigit = (11 - (sum % 11)) % 10;

		return checkDigit == (rrn.charAt(12) - '0');
	}

}
