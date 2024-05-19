package com.shipdoc.domain.Member.exception;

import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

public class MemberNotExistException extends GeneralException {
	public MemberNotExistException() {
		super(ErrorStatus._MEMBER_NOT_EXIST);
	}
}
