package com.shipdoc.domain.search.validation.validator;

import com.shipdoc.domain.search.enums.MedicalDepartment;
import com.shipdoc.domain.search.validation.annotation.CategoryKeyword;
import com.shipdoc.domain.search.web.dto.SearchRequestDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryKeywordValidator
	implements ConstraintValidator<CategoryKeyword, SearchRequestDto.SearchCategoryRequestDto> {
	@Override
	public boolean isValid(SearchRequestDto.SearchCategoryRequestDto value, ConstraintValidatorContext context) {
		boolean flag = false;
		for (MedicalDepartment categoryDto : value.getCategory()) {
			if (categoryDto == MedicalDepartment.OTHERS) {
				flag = true;
				break;
			}
		}
		if (flag == false)
			return true;

		boolean isValid = !(value.getKeyword() == null || value.getKeyword().isEmpty());

		if (isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("병원 카테고리를 입력해주세요.").addConstraintViolation();
		}

		return isValid;
	}

	@Override
	public void initialize(CategoryKeyword constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
}
