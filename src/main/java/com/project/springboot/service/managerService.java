package com.project.springboot.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.springboot.dao.ImanagerDao;
import com.project.springboot.dto.BDto;
import com.project.springboot.dto.BPageInfo;
import com.project.springboot.dto.userDto;

@Service
public class managerService implements ImanagerService
{
	@Autowired
	ImanagerDao dao;
	
	int listCount=10;
	int pageCount=10;
	
	@Override
	public BPageInfo articlePage(int curPage) {
		int listCount=10;		//한 페이지당 보여줄 게시물의 갯수
		int pageCount=5;		//하단에 보여줄 페이지 리스트의 갯수
		
		int totalCount=0;
		
		totalCount=dao.articlePage();
		
		int totalPage=totalCount/listCount;
		if(totalCount%listCount>0) {
			totalPage++;
		}
		
		//현재 페이지
		int myCurPage=curPage;
		if(myCurPage>totalPage)
			myCurPage=totalPage;
		if(myCurPage<1)
			myCurPage=1;
		
		//시작 페이지
		int startPage=((myCurPage-1)/pageCount)*pageCount+1;
		
		int endPage=startPage+pageCount-1;
		if(endPage>totalPage)
			endPage=totalPage;
		
		if(totalPage==0) {
			totalPage=1;
		}
		
		BPageInfo pinfo=new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(curPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
	
	@Override
	public ArrayList<userDto> list(int curPage) {
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		return dao.list(nEnd, nStart);
	}
	
	@Override
	public userDto user(String id) {
		return dao.userDao(id);
	}
	
	@Override
	public int delete(String id) {
		return dao.manager_delete(id);
	}
	
	@Override
	public BPageInfo SearchPageForId(int curPage, String strTitle) {
		int listCount=10;	//한 페이지당 보여줄 게시물의 갯수
		int pageCount=5;	//하단에 보여줄 페이지 리스트의 갯수
		int totalCount=0;	//총 게시물의 갯수
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		totalCount=dao.SearchPageForId(strTitleResult);
		
		//총 페이지 수
		int totalPage=totalCount/listCount;
		if(totalCount%listCount>0) {
			totalPage++;
		}
		
		//현재 페이지
		int myCurPage=curPage;
		if(myCurPage>totalPage)
			myCurPage=totalPage;
		if(myCurPage<1)
			myCurPage=1;
		
		//시작 페이지
		int startPage=((myCurPage-1)/pageCount)*pageCount+1;
		
		int endPage=startPage+pageCount-1;
		if(endPage>totalPage)
			endPage=totalPage;
		
		if(totalPage==0) {
			totalPage=1;
		}
		
		BPageInfo pinfo=new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(curPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
	
	@Override
	public ArrayList<userDto> SearchlistForId(int curPage, String strTitle) {
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		return dao.SearchlistForId(strTitleResult, nEnd, nStart);
	}
	
	@Override
	public BPageInfo SearchPageForName(int curPage, String strTitle) {
		int listCount=10;	//한 페이지당 보여줄 게시물의 갯수
		int pageCount=5;	//하단에 보여줄 페이지 리스트의 갯수
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int totalCount=dao.SearchPageForName(strTitleResult);
		
		//총 페이지 수
		int totalPage=totalCount/listCount;
		if(totalCount%listCount>0) {
			totalPage++;
		}
		
		//현재 페이지
		int myCurPage=curPage;
		if(myCurPage>totalPage)
			myCurPage=totalPage;
		if(myCurPage<1)
			myCurPage=1;
		
		//시작 페이지
		int startPage=((myCurPage-1)/pageCount)*pageCount+1;
		
		int endPage=startPage+pageCount-1;
		if(endPage>totalPage)
			endPage=totalPage;
		
		if(totalPage==0) {
			totalPage=1;
		}
		
		BPageInfo pinfo=new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(curPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
	
	@Override
	public ArrayList<userDto> SearchlistForName(int curPage, String strTitle) {
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		return dao.SearchlistForName(strTitleResult, nEnd, nStart);
	}
}
