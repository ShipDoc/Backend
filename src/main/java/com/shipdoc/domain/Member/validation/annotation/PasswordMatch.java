package com.shipdoc.domain.Member.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shipdoc.domain.Member.validation.validator.PasswordMatchValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


/**
 * 비밀번호 확인이 올바른지 체크하는 annotation
 */
@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {
	String message() default ("비밀번호가 일치하지 않아요!");

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
