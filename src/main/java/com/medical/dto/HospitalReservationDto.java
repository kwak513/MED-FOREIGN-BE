package com.medical.dto;

import java.time.LocalDateTime;

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
	
	String reservationTime;
	
	String source;
	
	Long hospitalId;
	Long memberId;
	
}
