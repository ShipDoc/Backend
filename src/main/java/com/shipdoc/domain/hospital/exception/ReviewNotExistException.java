package com.shipdoc.domain.hospital.exception;

import com.shipdoc.global.enums.statuscode.BaseCode;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

public class ReviewNotExistException extends GeneralException {
	public ReviewNotExistException() {
		super(ErrorStatus._REVIEW_NOT_EXIST);
	}
}
