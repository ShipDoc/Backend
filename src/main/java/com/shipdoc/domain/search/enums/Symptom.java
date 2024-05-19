package com.shipdoc.domain.search.enums;

public enum Symptom {
	HEADACHE("두통", MedicalDepartment.NEUROLOGY),
	FEVER("발열", MedicalDepartment.OTOLARYNGOLOGY),
	COUGH("기침", MedicalDepartment.OTOLARYNGOLOGY),
	SORE_THROAT("목통증", MedicalDepartment.OTOLARYNGOLOGY),
	NAUSEA("메스꺼움", MedicalDepartment.INTERNAL_MEDICINE),
	FATIGUE("피로", MedicalDepartment.INTERNAL_MEDICINE),
	DIARRHEA("설사", MedicalDepartment.INTERNAL_MEDICINE),
	VOMITING("구토", MedicalDepartment.INTERNAL_MEDICINE),
	DIZZINESS("어지럼증", MedicalDepartment.NEUROLOGY),
	CHEST_PAIN("흉통", MedicalDepartment.INTERNAL_MEDICINE),
	SHORTNESS_OF_BREATH("호흡곤란", MedicalDepartment.INTERNAL_MEDICINE),
	ABDOMINAL_PAIN("복통", MedicalDepartment.INTERNAL_MEDICINE),
	RASH("발진", MedicalDepartment.DERMATOLOGY),
	JOINT_PAIN("관절통", MedicalDepartment.ORTHOPEDICS),
	MUSCLE_ACHES("근육통", MedicalDepartment.ORTHOPEDICS),
	RUNNY_NOSE("콧물", MedicalDepartment.OTOLARYNGOLOGY),
	BODY_ACHE("몸살", MedicalDepartment.INTERNAL_MEDICINE);

	private final String description;
	private final MedicalDepartment department;

	Symptom(String description, MedicalDepartment department) {
		this.description = description;
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public MedicalDepartment getDepartment() {
		return department;
	}
}
