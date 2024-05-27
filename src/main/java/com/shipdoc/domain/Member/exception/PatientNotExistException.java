package com.shipdoc.domain.Member.exception;

import com.shipdoc.global.enums.statuscode.BaseCode;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

public class PatientNotExistException extends GeneralException {
	public PatientNotExistException() {
		super(ErrorStatus._PATIENT_NOT_EXIST);
	}
}
