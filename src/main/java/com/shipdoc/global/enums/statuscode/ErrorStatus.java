package com.shipdoc.global.enums.statuscode;

import org.springframework.http.HttpStatus;

public enum ErrorStatus implements BaseCode {
	// common
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	_PAGE_VARIABLE_INVALID(HttpStatus.BAD_REQUEST, "PAGE4001", "요청한 페이지 혹은 페이지당 요소 개수는 0보다 커야합니다."),

	// Member
	_MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "MEMBER4001", "존재하지 않는 사용자입니다."),
	_EXIST_LOGINID(HttpStatus.BAD_REQUEST, "MEMBER4002", "이미 존재하는 로그인 ID 입니다."),
	_EXIST_CODE_REQUEST(HttpStatus.BAD_REQUEST, "MEMBER4003", "최근 3분 이내에 요청한 인증번호가 존재합니다."),
	_INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "MEMBER4004", "올바르지 않은 인증코드입니다."),
	_EXPIRED_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "MEMBER4005", "유효기간이 만료된 코드입니다. 인증번호를 다시 요청해주세요."),
	_PATIENT_NOT_EXIST(HttpStatus.NOT_FOUND, "MEMBER4006", "존재하지 않는 회원입니다."),
	_PATIENT_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "MEMBER4007", "삭제 권한이 없습니다."),

	// review
	_REVIEW_NOT_EXIST(HttpStatus.NOT_FOUND, "REVIEW4001", "존재하지 않는 리뷰입니다."),
	_EXIST_REVIEW_RECOMMEND(HttpStatus.BAD_REQUEST, "REVIEW4002", "이미 추천한 리뷰입니다."),
	_REVIEW_RECOMMEND_NOT_EXIST(HttpStatus.NOT_FOUND, "REVIEW4003", "추천하지 않은 리뷰입니다."),

	// Hospital
	_HOSPITAL_NOT_EXIST(HttpStatus.NOT_FOUND, "HOSPITAL4001", "존재하지 않는 병원입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	ErrorStatus(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public Integer getStatusValue() {
		return httpStatus.value();
	}
}
