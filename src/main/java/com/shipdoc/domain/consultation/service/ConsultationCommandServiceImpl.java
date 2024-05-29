package com.shipdoc.domain.consultation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.mapping.Reservation;
import com.shipdoc.domain.consultation.converter.ConsultationConverter;
import com.shipdoc.domain.consultation.entity.Consultation;
import com.shipdoc.domain.consultation.repository.ConsultationRepository;
import com.shipdoc.domain.consultation.web.dto.ConsultationRequestDto;
import com.shipdoc.domain.consultation.web.dto.ConsultationResponseDto;
import com.shipdoc.domain.reservation.exception.ReservationNotExistException;
import com.shipdoc.domain.reservation.repository.ReservationRepository;
import com.shipdoc.global.gpt.GptService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsultationCommandServiceImpl implements ConsultationCommandService {
	private final ConsultationRepository consultationRepository;
	private final ReservationRepository reservationRepository;
	private final GptService gptService;

	@Override
	public ConsultationResponseDto.ConsultationConvertResponseDto convertToConsultation(Long reservationId,
		ConsultationRequestDto.ConsultationConvertRequestDto request) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ReservationNotExistException());

		reservationRepository.delete(reservation);

		// AI 진단 결과
		String aiRecommend = gptService.gptChatCompletion(request.getDiagnosis());
		Consultation consultation = ConsultationConverter.toConsultation(reservation, request.getDiagnosis(),
			aiRecommend);
		reservation.getHospital().addConsultation(consultation);
		reservation.getPatient().addConsultation(consultation);
		return ConsultationConverter.toConsultationConvertResponseDto(consultationRepository.save(consultation));
	}
}
