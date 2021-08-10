package com.project.springboot.service;

import java.util.ArrayList;

import com.project.springboot.dto.BDto;
import com.project.springboot.dto.BPageInfo;
import com.project.springboot.dto.userDto;

public interface ImanagerService
{
	public BPageInfo articlePage(int curPage);
	public ArrayList<userDto> list(int curPage);
	public userDto user(String id);
	public int delete(String id);
	public BPageInfo SearchPageForId(int curPage, String strTitle);
	public ArrayList<userDto> SearchlistForId(int curPage, String strTitle);
	public BPageInfo SearchPageForName(int curPage, String strTitle);
	public ArrayList<userDto> SearchlistForName(int curPage, String strTitle);
}
