package com.shipdoc.domain.hospital.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "business_hours")
public class BusinessHours {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "monday", nullable = false)
	private String monday;

	@Column(name = "tuesday", nullable = false)
	private String tuesday;

	@Column(name = "wednesday", nullable = false)
	private String wednesday;

	@Column(name = "thursday", nullable = false)
	private String thursday;

	@Column(name = "friday", nullable = false)
	private String friday;

	@Column(name = "saturday", nullable = false)
	private String saturday;

	@Column(name = "sunday", nullable = false)
	private String sunday;

	@Column(name = "holiday", nullable = false)
	private String holiday;

	@Column(name = "break_time")
	private String breakTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	// 연관관계 편의 메서드

	public void changeHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	// 영업시간 문자열을 파싱하여 LocalTime 객체로 변환하는 메서드
	private LocalTime[] parseTimeRange(String timeRange) {
		String[] times = timeRange.split(" ~ ");
		return new LocalTime[] {
			LocalTime.parse(times[0], DateTimeFormatter.ofPattern("HH:mm")),
			LocalTime.parse(times[1], DateTimeFormatter.ofPattern("HH:mm"))
		};
	}

	// 현재 시각에 대해 영업중인지 확인하는 메서드
	public String isOpenNow() {
		LocalDateTime now = LocalDateTime.now();
		LocalTime currentTime = now.toLocalTime();
		String hours = getTodayHours();

		if (hours.equals("휴무")) {
			return "CLOSED";
		}

		LocalTime[] timeRange = parseTimeRange(hours);
		LocalTime start = timeRange[0];
		LocalTime end = timeRange[1];

		LocalTime[] breakTimeRange = parseTimeRange(hours);
		LocalTime breakTimeStart = breakTimeRange[0];
		LocalTime breakTimeEnd = breakTimeRange[1];

		if (!currentTime.isBefore(breakTimeStart) && !currentTime.isAfter(breakTimeEnd)) {
			return "BREAK_TIME";
		}

		return !currentTime.isBefore(start) && !currentTime.isAfter(end) ? "OPEN" : "CLOSED";
	}

	public String getTodayHours() {
		LocalDateTime now = LocalDateTime.now();
		DayOfWeek dayOfWeek = now.getDayOfWeek();

		String hours = "";

		switch (dayOfWeek) {
			case MONDAY:
				hours = monday;
				break;
			case TUESDAY:
				hours = tuesday;
				break;
			case WEDNESDAY:
				hours = wednesday;
				break;
			case THURSDAY:
				hours = thursday;
				break;
			case FRIDAY:
				hours = friday;
				break;
			case SATURDAY:
				hours = saturday;
				break;
			case SUNDAY:
				hours = sunday;
				break;
		}

		return hours;
	}
}
