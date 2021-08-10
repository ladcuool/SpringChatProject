package com.project.springboot.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.springboot.dto.userDto;

@Mapper
public interface IuserDao
{
	public int userCheckDao(String id);
	public int registerDao(Map<String, String> map);
	public userDto userDao(String id);
	public int update(Map<String, String> map);
	public int delete(String id);
	public String getuserProfile(String id);
	public String member_check(String password);
	public int member_registerDao(Map<String, String> map);
}
