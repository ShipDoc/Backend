package com.shipdoc.domain.search.enums;

public enum MedicalDepartment {
	SURGERY("외과"),
	INTERNAL_MEDICINE("내과"),
	OTOLARYNGOLOGY("이비인후과"),
	PEDIATRICS("소아과"),
	ORTHOPEDICS("정형외과"),
	NEUROSURGERY("신경외과"),
	FAMILY_MEDICINE("가정의학과"),
	OPHTHALMOLOGY("안과"),
	PSYCHIATRY("정신건강의학과"),
	DERMATOLOGY("피부과"),
	UROLOGY("비뇨기과"),
	OBSTETRICS_AND_GYNECOLOGY("산부인과"),
	NEUROLOGY("신경과"),
	PLASTIC_SURGERY("성형외과"),
	EMERGENCY_MEDICINE("응급의학과"),
	OTHERS("기타");

	private final String koreanName;

	MedicalDepartment(String koreanName) {
		this.koreanName = koreanName;
	}

	public String getKoreanName() {
		return koreanName;
	}
}
