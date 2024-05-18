package com.shipdoc.domain.Member.enums;

/**
 * 회원 접근 권한 Enum
 */
public enum Authority {
	ROLE_ADMIN(1L), ROLE_GUEST(2L), ROLE_USER(3L);

	private final Long id;

	Authority(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
