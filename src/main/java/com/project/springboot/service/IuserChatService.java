package com.project.springboot.service;

import java.util.List;

import com.project.springboot.dto.chatRoom;
import com.project.springboot.dto.chatingDto;
import com.project.springboot.dto.userDto;

public interface IuserChatService
{
	public List<userDto> memberList();
	public int makeRoom(String roomNumber, String userID);
	public int chating(String roomNumber, String userID, String msg);
	public List<chatRoom> roomList(String userID);
	public chatingDto recentChat(String roomNumber);
	public List<chatingDto> chatlist(String roomNumber);
	public String RoomNumber(String id, String toID);
	public int unread(String id);
	public int roomunread(String roomNumber, String id);
	public int read(String roomNumber, String id);
	public int fullread(String roomNumber);
}
