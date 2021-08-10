package com.project.springboot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.springboot.dto.chatRoom;
import com.project.springboot.dto.chatingDto;
import com.project.springboot.dto.recentChatDto;
import com.project.springboot.service.userChatService;
import com.project.springboot.service.userDaoService;

@Controller
public class ChatController
{
	@Autowired
	userChatService chatservice;
	@Autowired
	userDaoService userservice;
	
	@RequestMapping("/createRoom")
	@ResponseBody
	public String createRoom(HttpServletRequest request) {
		String oriroom=null;
		HttpSession session=request.getSession();
		List<chatingDto> result=new ArrayList<>();
		String roomNumber=chatservice.RoomNumber((String)session.getAttribute("id"), request.getParameter("userID"));

		if(request.getParameter("roomNumber")!=null) {
			oriroom=request.getParameter("roomNumber");
		} else if(roomNumber!=null) {
			oriroom=roomNumber;
			chatservice.read(oriroom, (String)session.getAttribute("id"));
		} else {
			//이전 채팅 기록이 존재하지 않으면 방 생성
			String userID=(String)session.getAttribute("id");
			String toID=request.getParameter("userID");
			oriroom=UUID.randomUUID().toString();
			
			String id=userID+","+toID;
			chatservice.makeRoom(oriroom, id);
		}

		return oriroom;
	}
	
	@RequestMapping("/rooms")
	@ResponseBody
	public List<recentChatDto> rooms(HttpServletRequest request) throws Exception {
		HttpSession session=request.getSession();
		List<chatRoom> roomlist=chatservice.roomList(request.getParameter("id"));
		chatingDto chatlist=null;
		List<String> roomusers=new ArrayList<>();
		List<recentChatDto> result=new ArrayList<>();
		
		//roomlist를 가져와서 room별 최근 chating 내용 불러오기
		for(int i=0; i<roomlist.size(); i++) {
			String[] str=roomlist.get(i).getUserID().split(",");
			String toID="";
			for(int k=0;k<str.length;k++) {
				if(!str[k].equals((String)session.getAttribute("id"))) {
					toID=str[k];
				}
			}
			
			chatlist=chatservice.recentChat(roomlist.get(i).getRoomNumber());
			String userProfile=userservice.user(toID).getUserProfile();
			recentChatDto recent=new recentChatDto();
			recent.setRoomNumber(chatlist.getRoomNumber());
			recent.setMsg(chatlist.getMsg());
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm");
			recent.setNowTime(format.format(chatlist.getNowTime()));
			recent.setRoomUsers(toID);
			recent.setUserID(chatlist.getUserID());
			recent.setUserProfile(userProfile);
			recent.setUnread(chatservice.roomunread(roomlist.get(i).getRoomNumber(),request.getParameter("id")));
			result.add(recent);
		}
		
		Collections.sort(result, new Comparator<>() {
			@Override
			public int compare(recentChatDto a1, recentChatDto a2) {
				return a2.getNowTime().compareTo(a1.getNowTime());
			}
		});
		
		return result;
		
	}
	
	@RequestMapping("/chathistory")
	@ResponseBody
	public List<chatingDto> chathistory(HttpServletRequest request) {
		List<chatingDto> result=chatservice.chatlist(request.getParameter("roomNumber"));
		Collections.reverse(result);
		
		for(int i=0; i<result.size(); i++) {
			result.get(i).setUserProfile(userservice.user(result.get(i).getUserID()).getUserProfile());
		}
		
		
		return result;
	}
	
	@RequestMapping("/unread")
	@ResponseBody
	public int unread(HttpServletRequest request) {
		return chatservice.unread(request.getParameter("id"));
	}
	
	@RequestMapping("/read")
	@ResponseBody
	public void read(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String roomNumber=request.getParameter("roomNumber");
		String id=(String)session.getAttribute("id");
		chatservice.read(roomNumber , id);
		return;
	}
	
	@RequestMapping("/chatprofile")
	@ResponseBody
	public String chatprofile(HttpServletRequest request) {
		String id=request.getParameter("id");
		return userservice.user(id).getUserProfile();
	}
}