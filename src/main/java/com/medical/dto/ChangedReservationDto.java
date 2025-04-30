package com.medical.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangedReservationDto {
	String language;
	String mainSymptom;
	String subSymptom;
	String detailSymptom;
	String reservationTime;
	Long reservationId;
}
