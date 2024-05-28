package com.shipdoc.domain.consultation.web.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ConsultationListDto {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ConsultationResponse {
        private Long id;
        private String patientName;
        private String hospitalName;
        private LocalDateTime reservationTime;
        private String diagnosis;
        private String department; // 진료 과목
        private Integer visitCount;
        private String hospitalAddress; // 병원 주소
        private String kakaoUrl; // 병원 지도
        private String aiRecommend;
    }

    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchConsultationRes {
        private List<ConsultationListDto.ConsultationResponse> consultations;

        public SearchConsultationRes(List<ConsultationListDto.ConsultationResponse> consultations){
            this.consultations = consultations;
        }
    }
}
