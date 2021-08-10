package com.project.springboot.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.springboot.dao.IboardDao;
import com.project.springboot.dto.BDto;
import com.project.springboot.dto.BPageInfo;
import com.project.springboot.dto.replyDto;

@Service
public class boardService implements IboardService
{
	@Autowired
	IboardDao dao;
	
	int listCount=10;
	int pageCount=10;
	
	@Override
	public int upload(Map<String,String> map) {
		return dao.upload(map);
	}
	
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
	public ArrayList<BDto> list(int curPage) {
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		return dao.list(nEnd, nStart);
	}
	
	@Override
	public BDto contentView(String bId) {
		int TId=Integer.parseInt(bId);
		return dao.contentView(TId);
	}
	
	@Override
	public ArrayList<replyDto> replyView(String bId) {
		int TId=Integer.parseInt(bId);
		return dao.replyView(TId);
	}
	
	@Override
	public String filename(String bId) {
		int bIdint=Integer.parseInt(bId);
		return dao.filename(bIdint);
	}
	
	@Override
	public int modify(Map<String, String> map) {
		return dao.modify(map);
	}
	
	@Override
	public int delete(int bId) {
		return dao.delete(bId);
	}
	
	@Override
	public int reply(String bId, String id, String replyContent, int replyGroup, int replyStep) {
		return dao.reply(bId, id, replyContent, replyGroup, replyStep);
	}
	
	@Override
	public int maxStep(int bId, int replyGroup) {
		String value=dao.maxStep(bId, replyGroup);
		int result=0;
		if(value!=null) {
			result=Integer.parseInt(value);
		}
		return result;
	}
	
	@Override
	public int maxGroup(int bId) {
		String value=dao.maxGroup(bId);
		int result=0;
		if(value!=null) {
			result=Integer.parseInt(value);
		}
		return result;
	}
	
	@Override
	public ArrayList<replyDto> steplist(int bId,int replyGroup) {
		return dao.steplist(bId,replyGroup);
	}
	
	@Override
	public int reply_revise(int bId, String replyContent, int replyGroup) {
		return dao.reply_revise(bId, replyContent, replyGroup);
	}
	
	@Override
	public int reply_delete(int bId, int replyGroup) {
		return dao.reply_delete(bId, replyGroup);
	}
	
	@Override
	public int reply_reply_revise(String replyContent, int bId, int replyGroup, int replyStep) {
		return dao.reply_reply_revise(replyContent, bId, replyGroup, replyStep);
	}
	
	@Override
	public int reply_reply_delete(int bId, int replyGroup, int replyStep) {
		return dao.reply_reply_delete(bId, replyGroup, replyStep);
	}
	
	//여기서부터 공지사항 게시판 관련
	@Override
	public int upload2(Map<String,String> map) {
		return dao.upload2(map);
	}
	
	@Override
	public BPageInfo articlePage2(int curPage) {
		int listCount=10;		//한 페이지당 보여줄 게시물의 갯수
		int pageCount=5;		//하단에 보여줄 페이지 리스트의 갯수
		
		int totalCount=0;
		
		totalCount=dao.articlePage2();
		
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
	public ArrayList<BDto> list2(int curPage) {
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		return dao.list2(nEnd, nStart);
	}
	
	@Override
	public BDto contentView2(String bId) {
		int TId=Integer.parseInt(bId);
		return dao.contentView2(TId);
	}
	
	@Override
	public BPageInfo SearchPageForTitle(int curPage, String strTitle) {
		int listCount=10;	//한 페이지당 보여줄 게시물의 갯수
		int pageCount=5;	//하단에 보여줄 페이지 리스트의 갯수
		int totalCount=0;	//총 게시물의 갯수
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		totalCount=dao.SearchPageForTitle(strTitleResult);
		
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
	public ArrayList<BDto> SearchlistForTitle(int curPage, String strTitle) {
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		return dao.SearchlistForTitle(strTitleResult, nEnd, nStart);
	}
	
	@Override
	public BPageInfo SearchPageForContent(int curPage, String strTitle) {
		int listCount=10;	//한 페이지당 보여줄 게시물의 갯수
		int pageCount=5;	//하단에 보여줄 페이지 리스트의 갯수
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int totalCount=dao.SearchPageForContent(strTitleResult);
		
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
	public ArrayList<BDto> SearchlistForContent(int curPage, String strTitle) {
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		return dao.SearchlistForContent(strTitleResult, nEnd, nStart);
	}
	
	@Override
	public BPageInfo SearchPageForWriter(int curPage, String strTitle) {
		int listCount=10;	//한 페이지당 보여줄 게시물의 갯수
		int pageCount=5;	//하단에 보여줄 페이지 리스트의 갯수
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int totalCount=dao.SearchPageForWriter(strTitleResult);
		
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
	public ArrayList<BDto> SearchlistForWriter(int curPage, String strTitle) {
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		return dao.SearchlistForWriter(strTitleResult, nEnd, nStart);
	}
	
	@Override
	public String manager_filename(String bId) {
		int bIdint=Integer.parseInt(bId);
		return dao.manager_filename(bIdint);
	}
	
	@Override
	public int manager_modify(Map<String, String> map) {
		return dao.manager_modify(map);
	}
	
	@Override
	public int manager_delete(int bId) {
		return dao.manager_delete(bId);
	}
	
	//여기서부터 공지사항 게시판 관련 검색기능
	
	@Override
	public BPageInfo SearchPageForTitle2(int curPage, String strTitle) {
		int listCount=10;	//한 페이지당 보여줄 게시물의 갯수
		int pageCount=5;	//하단에 보여줄 페이지 리스트의 갯수
		int totalCount=0;	//총 게시물의 갯수
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		totalCount=dao.SearchPageForTitle2(strTitleResult);
		
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
	public ArrayList<BDto> SearchlistForTitle2(int curPage, String strTitle) {
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		return dao.SearchlistForTitle2(strTitleResult, nEnd, nStart);
	}
	
	@Override
	public BPageInfo SearchPageForContent2(int curPage, String strTitle) {
		int listCount=10;	//한 페이지당 보여줄 게시물의 갯수
		int pageCount=5;	//하단에 보여줄 페이지 리스트의 갯수
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int totalCount=dao.SearchPageForContent2(strTitleResult);
		
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
	public ArrayList<BDto> SearchlistForContent2(int curPage, String strTitle) {
		String strTitleResult="%";
		
		String[] strArray=strTitle.split("");
		for(String s : strArray) {
			strTitleResult+=s;
			strTitleResult+="%";
		}
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		return dao.SearchlistForContent2(strTitleResult, nEnd, nStart);
	}
}
