package com.project.springboot.service;

import java.util.ArrayList;
import java.util.Map;

import com.project.springboot.dto.BDto;
import com.project.springboot.dto.BPageInfo;
import com.project.springboot.dto.replyDto;

public interface IboardService
{
	public int upload(Map<String,String> map);
	public BPageInfo articlePage(int curPage);
	public ArrayList<BDto> list(int curPage);
	public BDto contentView(String bId);
	public ArrayList<replyDto> replyView(String bId);
	public String filename(String bId);
	public int modify(Map<String, String> map);
	public int delete(int bId);
	public int reply(String bId, String id, String replyContent, int replyGroup, int replyStep);
	public int maxStep(int bId,int replyGroup);
	public int maxGroup(int bId);
	public ArrayList<replyDto> steplist(int bId, int replyGroup);
	public int reply_revise(int bId, String replyContent, int replyGroup);
	public int reply_delete(int bId, int replyGroup);
	public int reply_reply_revise(String replyContent, int bId, int replyGroup, int replyStep);
	public int reply_reply_delete(int bId, int replyGroup, int replyStep);
	public BPageInfo SearchPageForTitle(int curPage, String strTitle);
	public ArrayList<BDto> SearchlistForTitle(int curPage, String strTitle);
	public BPageInfo SearchPageForContent(int curPage, String strTitle);
	public ArrayList<BDto> SearchlistForContent(int curPage, String strTitle);
	public BPageInfo SearchPageForWriter(int curPage, String strTitle);
	public ArrayList<BDto> SearchlistForWriter(int curPage, String strTitle);
	//여기서부터 공지사항 게시판 관련
	public int upload2(Map<String,String> map);
	public BPageInfo articlePage2(int curPage);
	public ArrayList<BDto> list2(int curPage);
	public BDto contentView2(String bId);
	public String manager_filename(String bId);
	public int manager_modify(Map<String, String> map);
	public int manager_delete(int bId);
	public BPageInfo SearchPageForTitle2(int curPage, String strTitle);
	public ArrayList<BDto> SearchlistForTitle2(int curPage, String strTitle);
	public BPageInfo SearchPageForContent2(int curPage, String strTitle);
	public ArrayList<BDto> SearchlistForContent2(int curPage, String strTitle);
}
