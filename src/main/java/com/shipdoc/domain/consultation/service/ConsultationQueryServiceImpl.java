package com.shipdoc.domain.consultation.service;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.consultation.entity.Consultation;
import com.shipdoc.domain.consultation.repository.ConsultationRepository;
import com.shipdoc.domain.consultation.web.dto.ConsultationListDto;
import com.shipdoc.global.annotation.LoginMember;
import com.shipdoc.global.enums.statuscode.SuccessStatus;
import com.shipdoc.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultationQueryServiceImpl implements ConsultationQueryService{
    public final ConsultationRepository consultationRepository;

    @Override
    public ApiResponse<?> getAllConsultation(@LoginMember Member member) {
        List<Consultation> consultations = consultationRepository.findAll();
        List<ConsultationListDto.ConsultationResponse> consultationResponses = new ArrayList<>();

        Patient patient = member.getPatientList().get(0);

        if (consultations.isEmpty()) {
            return ApiResponse.of(true, SuccessStatus._OK, null);
        }

        for(Consultation consultation: consultations) {
            consultationResponses.add(ConsultationListDto.ConsultationResponse.builder()
                    .id(consultation.getId())
                    .patientName(patient.getName())
                    .hospitalName(consultation.getHospital().getName())
                    .reservationTime(consultation.getReservationTime())
                    .diagnosis(consultation.getDiagnosis())
                    .department(consultation.getHospital().getDepartment())
                    .visitCount(consultation.getVisitCount())
                    .hospitalAddress(consultation.getHospital().getAddress())
                    .kakaoUrl(consultation.getHospital().getKakaoUrl())
                    .aiRecommend(consultation.getAiRecommend())
                    .build());
        }

        return ApiResponse.onSuccess(new ConsultationListDto.SearchConsultationRes(consultationResponses));
    }

}