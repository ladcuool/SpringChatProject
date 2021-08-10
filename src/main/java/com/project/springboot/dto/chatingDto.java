package com.project.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class chatingDto
{
	String roomNumber;
	String userID;
	String msg;
	Timestamp nowTime;
	String userProfile;
	String read;
}
