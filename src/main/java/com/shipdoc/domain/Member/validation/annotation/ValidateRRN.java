package com.shipdoc.domain.Member.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shipdoc.domain.Member.validation.validator.ValidateRRNValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * RRN(주민등록번호)가 올바른지 검증
 */
@Documented
@Constraint(validatedBy = ValidateRRNValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateRRN {
	String message() default ("주민등록번호가 올바르지 않습니다.");

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
