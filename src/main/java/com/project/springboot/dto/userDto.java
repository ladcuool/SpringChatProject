package com.project.springboot.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class userDto
{
	@NotNull(message="아이디를 입력해주세요.")
	@NotEmpty(message="아이디를 입력해주세요.")
	@Size(min=4, max=20, message="아이디는 최소 4글자 이상 최대 20자입니다.")
	String userID;
	@NotNull(message="비밀번호를 입력해주세요.")
	@NotEmpty(message="비밀번호를 입력해주세요.")
	@Size(min=8, max=20, message="비밀번호는 최소 8자리 이상, 최대 20자리입니다.")
	String userPassword;
	@NotNull(message="이름을 입력해주세요.")
	@NotEmpty(message="이름을 입력해주세요.")
	String userName;
	String userGender;
	String userAge;
	@NotNull(message="주소를 입력해주세요.")
	@NotEmpty(message="주소를 입력해주세요.")
	String userAddress;
	@NotNull(message="이메일을 입력해주세요.")
	@NotEmpty(message="이메일을 입력해주세요.")
	String userEmail;
	String userProfile;
	String Authority;
}
