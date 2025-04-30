package com.medical.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberFavoriteDto {
	Long memberId;
	String hospitalSource;
	Long hospitalId;
}
