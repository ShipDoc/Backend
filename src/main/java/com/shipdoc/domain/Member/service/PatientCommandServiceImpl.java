package com.shipdoc.domain.Member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.entity.Patient;
import com.shipdoc.domain.Member.exception.PatientNotExistException;
import com.shipdoc.domain.Member.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientCommandServiceImpl implements PatientCommandService {
	private final PatientRepository patientRepository;

	public void acceptHeathCheckupNotification(Member member, String phoneNumber) {

		if (member.getPatientList().isEmpty()) {
			throw new PatientNotExistException();
		}

		Patient patient = member.getPatientList().get(0);

		patient.changePhoneNumber(phoneNumber);
	}

	public void rejectHeathCheckupNotification(Member member) {
		if (member.getPatientList().isEmpty()) {
			throw new PatientNotExistException();
		}

		Patient patient = member.getPatientList().get(0);

		patient.changePhoneNumber("rejected");
	}

}
