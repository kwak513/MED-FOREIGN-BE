package com.medical.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberInfoChangedDto {
	Long id;
	String phoneNum;
	String gender;
	String birthDate;
	String email;
	String password;
}
