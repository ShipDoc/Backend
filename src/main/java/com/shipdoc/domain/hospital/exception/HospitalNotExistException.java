package com.shipdoc.domain.hospital.exception;

import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

public class HospitalNotExistException extends GeneralException {
	public HospitalNotExistException() {
		super(ErrorStatus._HOSPITAL_NOT_EXIST);
	}
}
