package com.project.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.project.springboot.dto.userDto;

@Mapper
public interface ImanagerDao
{
	public int articlePage();
	public ArrayList<userDto> list(int nEnd, int nStart);
	public userDto userDao(String id);
	public int manager_delete(String id);
	public int SearchPageForId(String strTitle);
	public ArrayList<userDto> SearchlistForId(String strTitleResult, int nEnd, int nStart);
	public int SearchPageForName(String strTitle);
	public ArrayList<userDto> SearchlistForName(String strTitleResult, int nEnd, int nStart);
}
