package com.shipdoc.domain.reservation.exception;

import com.shipdoc.global.enums.statuscode.BaseCode;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

public class ReservationNotExistException extends GeneralException {
	public ReservationNotExistException() {
		super(ErrorStatus._RESERVATION_NOT_EXSIT);
	}
}
