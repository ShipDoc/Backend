package com.shipdoc.global.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shipdoc.global.validation.validator.PageableVariableConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PageableVariableConstraint.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageableVariable {
	String message() default ("페이지 관련 값은 0보다 커야합니다.");

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
