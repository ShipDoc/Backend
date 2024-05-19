package com.shipdoc.domain.search.enums;

public enum HospitalSortCriteria {

	DISTANCE("distance"), ACCURACY("accuracy");

	private String value;

	HospitalSortCriteria(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
