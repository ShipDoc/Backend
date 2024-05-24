package com.shipdoc.domain.reservation.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shipdoc.domain.reservation.validation.validator.PhoneNumberValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * 예약 시 SMS 발송 신청 시
 * 핸드폰 번호이 있는 지 검증 어노테이션
 */
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
	String message() default ("핸드폰 번호가 올바르지 않습니다.");

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
