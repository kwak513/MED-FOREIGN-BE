package com.medical.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangedReviewDto {
	int rate;
	String originalTxt;
	Long reviewId;
}
