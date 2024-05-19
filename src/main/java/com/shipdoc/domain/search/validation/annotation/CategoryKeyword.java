package com.shipdoc.domain.search.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shipdoc.domain.search.validation.validator.CategoryKeywordValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * 기타 카테고리 선택 시 검색어가 있는지 검증
 */
@Documented
@Constraint(validatedBy = CategoryKeywordValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryKeyword {
	String message() default ("병원 카테고리를 입력해주세요.");

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
