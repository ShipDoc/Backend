package com.shipdoc.global.validation.validator;

import org.springframework.stereotype.Component;

import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.validation.annotation.PageableVariable;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PageableVariableConstraint implements ConstraintValidator<PageableVariable, Integer> {
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		boolean isValid = value > 0;
		if (!isValid) {
			log.error("validation 에러 발생");
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(ErrorStatus._PAGE_VARIABLE_INVALID.getMessage().toString())
				.addConstraintViolation();
		}
		return isValid;
	}

	@Override
	public void initialize(PageableVariable constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
}
