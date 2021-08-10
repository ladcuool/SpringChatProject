package com.project.springboot.dto;

import lombok.Data;

@Data
public class recentChatDto
{
	String roomNumber;
	String roomUsers;
	String userProfile;
	String userID;
	String msg;
	String nowTime;
	int unread;
}
