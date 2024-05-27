package com.shipdoc.domain.Member.service;

import com.shipdoc.domain.Member.entity.Member;

public interface PatientCommandService {
	void acceptHeathCheckupNotification(Member member, String phoneNumber);

	void rejectHeathCheckupNotification(Member member);
}
