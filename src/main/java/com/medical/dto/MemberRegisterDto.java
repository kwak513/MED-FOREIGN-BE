package com.medical.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRegisterDto {
	String username;
	String password;
	String phoneNum;
	String gender;
	String birthDate;
	String email;
	
}
