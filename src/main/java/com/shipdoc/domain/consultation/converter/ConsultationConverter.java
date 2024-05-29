package com.shipdoc.domain.consultation.converter;

import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.consultation.entity.Consultation;
import com.shipdoc.domain.consultation.web.dto.ConsultationResponseDto;

public class ConsultationConverter {
	public static Consultation toConsultation(Reservation reservation, String diagnosis, String aiRecommend) {
		return Consultation.builder()
			.reservationTime(reservation.getReservationTime())
			.visitCount(1)
			.aiRecommend(aiRecommend)
			.diagnosis(diagnosis)
			.build();
	}

	public static ConsultationResponseDto.ConsultationConvertResponseDto toConsultationConvertResponseDto(
		Consultation consultation) {
		return ConsultationResponseDto.ConsultationConvertResponseDto.builder()
			.consultationId(consultation.getId())
			.createdAt(consultation.getCreatedAt())
			.build();
	}
}
