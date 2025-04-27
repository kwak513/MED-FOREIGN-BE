package com.medical.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HospitalReservationDto {
	String language;
	String mainSymptom;
	String subSymptom;
	String detailSymptom;
	
	String source;
	
	Long hospitalId;
	Long memberId;
	
}
