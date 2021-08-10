package com.project.springboot.service;

import java.util.Map;

import com.project.springboot.dto.userDto;

public interface IuserDaoService
{
	public int userCheck(String id);
	public int register(Map<String, String> map);
	public userDto user(String id);
	public int update(Map<String, String> map);
	public int delete(String id);
	public String getuserProfile(String id);
	public String member_check(String password);
	public int member_register(Map<String, String> map);
}
