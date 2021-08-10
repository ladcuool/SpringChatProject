package com.project.springboot;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.springboot.dto.BDto;
import com.project.springboot.dto.BPageInfo;
import com.project.springboot.dto.userDto;
import com.project.springboot.service.managerService;

@Controller
public class ManagerController
{
	@Autowired
	managerService managerservice;
	
	@RequestMapping("/managerPage")
	public ModelAndView managerPagelist(HttpServletRequest request) {
		int nPage=1;
		ModelAndView view=new ModelAndView();
		HttpSession session=request.getSession();
		String sPage=null;
		
		if(request.getParameter("page")!=null) {
			sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}
		
		BPageInfo pinfo=managerservice.articlePage(nPage);
		view.addObject("page", pinfo);
		
		nPage=pinfo.getCurPage();

		session.setAttribute("cpage", nPage);
		
		ArrayList<userDto> dtos=managerservice.list(nPage);
		view.setViewName("managerPage");
		view.addObject("list", dtos);
		return view;
	}
	
	@RequestMapping("user_management")
	public ModelAndView user_management(HttpServletRequest request) {
		ModelAndView view=new ModelAndView();
		view.setViewName("user_management");
		view.addObject("dto", managerservice.user((request.getParameter("userID"))));
		return view;
	}
	
	@RequestMapping("/manager_userremove")
	public String manager_userremove(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userProfile=null;
		String userID=request.getParameter("userID");

		int result=managerservice.delete(userID);
		
		if(request.getParameter("userProfile")!=null) {
			userProfile=request.getParameter("userProfile");
		}

		if(userProfile!=null && userProfile.trim().length()!=0 && !userProfile.equals("noimage.png")) {
			try {
				String path=ResourceUtils
						.getFile("classpath:static/upload/").toPath().toString();
				File DelFile=new File(path+"/"+userProfile);
				if(DelFile.exists()) {
					DelFile.delete();
				}
			} catch(Exception e) {
				e.printStackTrace();
				session.setAttribute("messageContent", "회원 삭제에 실패했습니다.");
			}
		}

		session.setAttribute("messageContent", "해당 유저를 삭제했습니다.");
		return "redirect:/managerPage";
	}
	
	@RequestMapping("/manager_user_search")
	public ModelAndView manager_user_search(HttpServletRequest request) {
		String Sel=request.getParameter("Sel");
		String strTitle=request.getParameter("strTitle");
		HttpSession session=request.getSession();
		ModelAndView view=new ModelAndView();
		BPageInfo pinfo=null;
		String sPage=null;
		view.setViewName("manager_searchlist");

		int nPage=1;
		if(request.getParameter("page")!=null) {
			sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}
		
		if(Sel.equals("아이디")) {
			pinfo=managerservice.SearchPageForId(nPage, strTitle);
			view.addObject("page", pinfo);
			
			nPage=pinfo.getCurPage();
			
			session.setAttribute("cpage", nPage);
			session.setAttribute("Sel", Sel);
			
			ArrayList<userDto> dtos=managerservice.SearchlistForId(nPage, strTitle);
			view.addObject("list", dtos);
			view.addObject("strlist", strTitle);
		} else if(Sel.equals("이름")) {
			pinfo=managerservice.SearchPageForName(nPage,strTitle);
			view.addObject("page",pinfo);
			
			nPage=pinfo.getCurPage();
			session.setAttribute("cpage", nPage);
			session.setAttribute("Sel", Sel);
			
			ArrayList<userDto> dtos=managerservice.SearchlistForName(nPage, strTitle);
			view.addObject("list", dtos);
			view.addObject("strlist", strTitle);
		} else {			
			pinfo=managerservice.articlePage(nPage);
			view.addObject("page", pinfo);
			
			nPage=pinfo.getCurPage();

			session.setAttribute("cpage", nPage);
			
			session.setAttribute("messageContent", "검색할 항목을 선택해주세요.");
			
			ArrayList<userDto> dtos=managerservice.list(nPage);
			view.setViewName("managerPage");
			view.addObject("list", dtos);
		}
		
		return view;
	}
}

//검색 기능 추가