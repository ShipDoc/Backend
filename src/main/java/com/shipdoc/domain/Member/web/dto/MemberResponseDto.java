package com.shipdoc.domain.Member.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResponseDto {

	/**
	 * 회원가입 응답 DTO
	 */
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SignupResponseDto {
		private Long memberId;
		private LocalDateTime createdAt;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AddPatientResponseDto {
		private Long patientId;
		private LocalDateTime createdAt;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class HealthCheckUpNotificationDto {
		private String phone;
		private boolean acceptNotification;
	}

}
