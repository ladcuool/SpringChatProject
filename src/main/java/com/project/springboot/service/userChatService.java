package com.project.springboot.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.springboot.dao.IuserchatDao;
import com.project.springboot.dto.chatRoom;
import com.project.springboot.dto.chatingDto;
import com.project.springboot.dto.userDto;

@Service
public class userChatService implements IuserChatService
{
	@Autowired
	IuserchatDao dao;
	
	@Override
	public List<userDto> memberList() {
		return dao.memberList();
	}
	
	@Override
	public int makeRoom(String roomNumber, String userID) {
		return dao.makeRoom(roomNumber, userID);
	}
	
	@Override
	public int chating(String roomNumber, String userID, String msg) {
		Timestamp now=new Timestamp(System.currentTimeMillis());
		return dao.chating(roomNumber, userID, msg, now);
	}
	
	@Override
	public List<chatRoom> roomList(String userID) {
		return dao.roomList(userID);
	}
	
	@Override
	public chatingDto recentChat(String roomNumber) {
		return dao.recentChat(roomNumber);
	}
	
	@Override
	public List<chatingDto> chatlist(String roomNumber) {
		return dao.chatlist(roomNumber);
	}
	
	@Override
	public String RoomNumber(String id, String toID) {
		String userlist=id+","+toID;
		String userlist2=toID+","+id;
		return dao.RoomNumber(userlist, userlist2);
	}
	
	@Override
	public int unread(String id) {
		List<chatRoom> rooms=dao.roomList(id);
		int result=0;
		for(int i=0; i<rooms.size(); i++) {
			result+=dao.unread(rooms.get(i).getRoomNumber(), id);
		}
		return result;
	}
	
	@Override
	public int roomunread(String roomNumber, String id) {
		return dao.unread(roomNumber, id);
	}
	
	@Override
	public int read(String roomNumber, String id) {
		return dao.read(roomNumber, id);
	}
	
	@Override
	public int fullread(String roomNumber) {
		return dao.fullread(roomNumber);
	}
}
