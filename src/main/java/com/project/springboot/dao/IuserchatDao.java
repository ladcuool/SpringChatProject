package com.project.springboot.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.springboot.dto.chatRoom;
import com.project.springboot.dto.chatingDto;
import com.project.springboot.dto.userDto;

@Mapper
public interface IuserchatDao
{
	public List<userDto> memberList();
	public int makeRoom(String roomNumber, String userID);
	public int chating(String roomNumber, String userID, String msg, Timestamp now);
	public List<chatRoom> roomList(String userID);
	public chatingDto recentChat(String roomNumber);
	public List<chatingDto> chatlist(String roomNumber);
	public String RoomNumber(String userlist, String userlist2);
	public int unread(String roomNumber, String id);
	public int read(String roomNumber, String id);
	public int fullread(String roomNumber);
}
