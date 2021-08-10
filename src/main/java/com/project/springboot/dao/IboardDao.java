package com.project.springboot.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.springboot.dto.BDto;
import com.project.springboot.dto.BPageInfo;
import com.project.springboot.dto.replyDto;

@Mapper
public interface IboardDao
{
	public int upload(Map<String, String> map);
	public int articlePage();
	public ArrayList<BDto> list(int nEnd, int nStart);
	public BDto contentView(int bId);
	public ArrayList<replyDto> replyView(int bId);
	public String filename(int bId);
	public int modify(Map<String, String> map);
	public int delete(int bId);
	public int reply(String bId, String id, String replyContent, int replyGroup, int replyStep);
	public String maxStep(int bId, int replyGroup);
	public String maxGroup(int bId);
	public ArrayList<replyDto> steplist(int bId, int replyGroup);
	public int reply_revise(int bId, String replyContent, int replyGroup);
	public int reply_delete(int bId, int replyGroup);
	public int reply_reply_revise(String replyContent, int bId, int replyGroup, int replyStep);
	public int reply_reply_delete(int bId, int replyGroup, int replyStep);
	public int SearchPageForTitle(String strTitle);
	public ArrayList<BDto> SearchlistForTitle(String strTitleResult, int nEnd, int nStart);
	public int SearchPageForContent(String strTitle);
	public ArrayList<BDto> SearchlistForContent(String strTitleResult, int nEnd, int nStart);
	public int SearchPageForWriter(String strTitle);
	public ArrayList<BDto> SearchlistForWriter(String strTitleResult, int nEnd, int nStart);
	//여기서부터 공지사항 게시판 관련
	public int upload2(Map<String, String> map);
	public int articlePage2();
	public ArrayList<BDto> list2(int nEnd, int nStart);
	public BDto contentView2(int bId);
	public String manager_filename(int bId);
	public int manager_modify(Map<String, String> map);
	public int manager_delete(int bId);
	public int SearchPageForTitle2(String strTitle);
	public ArrayList<BDto> SearchlistForTitle2(String strTitleResult, int nEnd, int nStart);
	public int SearchPageForContent2(String strTitle);
	public ArrayList<BDto> SearchlistForContent2(String strTitleResult, int nEnd, int nStart);
}
