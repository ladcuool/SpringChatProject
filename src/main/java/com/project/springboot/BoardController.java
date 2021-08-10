package com.project.springboot;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.project.springboot.dto.BDto;
import com.project.springboot.dto.BPageInfo;
import com.project.springboot.dto.SmarteditorVO;
import com.project.springboot.dto.replyDto;
import com.project.springboot.service.boardService;

@Controller
public class BoardController
{
	@Autowired
	boardService boardservice;
	
	@RequestMapping("/write")
	public String write() {
		return "write_view";
	}
	
	@RequestMapping("/list")
	public ModelAndView Blist(HttpServletRequest request) {
		int nPage=1;
		ModelAndView view=new ModelAndView();
		HttpSession session=request.getSession();
		String sPage=null;
		
		if(request.getParameter("page")!=null) {
			sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}
		
		BPageInfo pinfo=boardservice.articlePage(nPage);
		view.addObject("page", pinfo);
		
		nPage=pinfo.getCurPage();

		session.setAttribute("cpage", nPage);
		
		ArrayList<BDto> dtos=boardservice.list(nPage);
		view.setViewName("list");
		view.addObject("list", dtos);
		return view;
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpServletRequest request) {
		HttpSession session=request.getSession();
		int size=1024*1024*10; //10M
		String file=null;
		String oriFile=null;
		int result;
		
		//파일 업로드
		try {
			String path=ResourceUtils
					.getFile("classpath:static/upload/").toPath().toString();
						
			MultipartRequest multi=new MultipartRequest(request, path, size,
											"UTF-8", new DefaultFileRenamePolicy());
			Enumeration files=multi.getFileNames();
			String str=(String)files.nextElement();
			
			file=multi.getFilesystemName(str);
			oriFile=multi.getOriginalFileName(str);
			
			
			if(multi.getParameter("bContent")==null||multi.getParameter("bContent").trim().isEmpty()) {
					session.setAttribute("messageContent","내용을 입력해주세요.");
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					return "write_view";
				} else if(multi.getParameter("bTitle")==null||multi.getParameter("bTitle").trim().isEmpty()) {
					session.setAttribute("messageContent","제목을 입력해주세요.");
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					return "write_view";
				}
			
			
			Map<String, String> map=new HashMap<>();
			map.put("item1", (String)session.getAttribute("id"));
			map.put("item2", multi.getParameter("bTitle"));
			map.put("item3", multi.getParameter("bContent"));
			if(file!=null) {
				map.put("item4",file);
			} else {
				map.put("item4", "");
			}

			result=boardservice.upload(map);
					
		} catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("messageContent", "파일 용량은 10M을 넘을 수 없습니다.");
			return "write_view";
		}
		
		if(result==1)
		{
			session.setAttribute("messageContent", "업로드에 성공했습니다.");
			return "redirect:/list";
		} else {
			session.setAttribute("messageContent", "업로드에 실패했습니다.");
			return "write_view";
		}
	}
	
	@RequestMapping("/content_view")
	public ModelAndView content_view(HttpServletRequest request) {
		String bId=null;
		HttpSession session=request.getSession();
		if(request.getParameter("bId")!=null) {
			bId=request.getParameter("bId");
		} else {
			bId=(String)session.getAttribute("bId");
		}

		BDto dto=boardservice.contentView(bId);

		ArrayList<replyDto> rdto=boardservice.replyView(bId);
		
		ModelAndView view=new ModelAndView();
		view.setViewName("content_view");
		view.addObject("content_view",dto);
		if(rdto!=null) {
			view.addObject("Table_view",rdto);
		}

		
		return view;
	}
	
	
	//파일 다운로드 시
	@RequestMapping("/downloadAction")
	@ResponseBody
	public void downloadAction(HttpServletRequest request,HttpServletResponse response) {
		String CHARSET="utf-8";
		int LIMIT_SIZE_BYTES=1024*1024;
		String fileName=request.getParameter("file");
		
		try {
			String directory=ResourceUtils
					.getFile("classpath:static/upload/").toPath().toString();
			
			File file=new File(directory+"/"+fileName);
			
			String mimeType=request.getSession().getServletContext().getMimeType(file.toString());
			if(mimeType==null) {
				response.setContentType("application/octet-stream");
			}
			
			String downloadName=null;
			if(request.getHeader("user-agent").indexOf("MSIE")==1) {
				downloadName=new String(fileName.getBytes("UTF-8"),"8859_1");
			} else {
				downloadName=new String(fileName.getBytes("EUC-KR"),"8859_1");
			}
			
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+downloadName+"\";");
			
			FileInputStream fileInputStream=new FileInputStream(file);
			ServletOutputStream servletOutputStream=response.getOutputStream();
			
			byte b[]=new byte[1024];
			int data=0;
			
			while((data=(fileInputStream.read(b,0,b.length)))!=-1) {
				servletOutputStream.write(b,0,data);
			}
			
			servletOutputStream.flush();
			servletOutputStream.close();
			fileInputStream.close();
					
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	@RequestMapping("/modify_view")
	public ModelAndView modify_view(HttpServletRequest request) {
		String bId=request.getParameter("bId");
		BDto dto=boardservice.contentView(bId);
		
		ModelAndView view=new ModelAndView();
		view.setViewName("modify_view");
		view.addObject("content_view",dto);

		return view;
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		int size=1024*1024*10; //10M
		String file=null;
		String oriFile=null;
		String bId=null;
		int result;
		MultipartRequest multi=null;
		
		//파일 업로드
		try {
			String path=ResourceUtils
					.getFile("classpath:static/upload/").toPath().toString();
						
			multi=new MultipartRequest(request, path, size,
								"UTF-8", new DefaultFileRenamePolicy());
			Enumeration files=multi.getFileNames();
			String str=(String)files.nextElement();
			bId=multi.getParameter("bId");
			
			file=multi.getFilesystemName(str);
			oriFile=multi.getOriginalFileName(str);
				
			if(multi.getParameter("bContent")==null||multi.getParameter("bContent").trim().isEmpty()) {
					session.setAttribute("messageContent","내용을 입력해주세요.");
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					model.addAttribute("content_view",boardservice.contentView(multi.getParameter("bId")));
					return "modify_view";
				} else if(multi.getParameter("bTitle")==null||multi.getParameter("bTitle").trim().isEmpty()) {
					session.setAttribute("messageContent","제목을 입력해주세요.");
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					model.addAttribute("content_view",boardservice.contentView(multi.getParameter("bId")));
					return "modify_view";
				}
			
			Map<String, String> map=new HashMap<>();
			map.put("item1", multi.getParameter("bTitle"));
			map.put("item2", multi.getParameter("bContent"));
			String prevfile=boardservice.filename(multi.getParameter("bId"));

			if(file!=null) {
				File DelFile=new File(path+"/"+prevfile);
				if(DelFile.exists()) {
					DelFile.delete();
				}
				map.put("item3",file);
			} else {
				if(prevfile!=null) {
					map.put("item3", prevfile);
				} else {
					map.put("item3","");
				}
			}
			
			map.put("item4", multi.getParameter("bId"));
			
			result=boardservice.modify(map);

					
		} catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("messageContent", "파일 용량은 10M을 넘을 수 없습니다.");
			model.addAttribute("content_view",boardservice.contentView(multi.getParameter("bId")));
			return "modify_view";
		}
		
		if(result==1)
		{
			session.setAttribute("messageContent", "글을 수정하였습니다.");
			session.setAttribute("bId",bId);
			return "redirect:/content_view";
		} else {
			session.setAttribute("messageContent", "글 수정에 실패했습니다.");
			model.addAttribute("content_view",boardservice.contentView(multi.getParameter("bId")));
			return "modify_view";
		}
	}
	
	@RequestMapping("/bdelete")
	public String delete(HttpServletRequest request) {
		String bId=request.getParameter("bId");
		HttpSession session=request.getSession();
		String fileName=boardservice.filename(bId);
		String path;
		try {
			path=ResourceUtils
					.getFile("classpath:static/upload/").toPath().toString();
				int result=boardservice.delete(Integer.parseInt(bId));
				File file=new File(path+"/"+fileName);
				if(file.exists()) {
					file.delete();
				}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		session.setAttribute("messageContent", "글 삭제를 완료하였습니다.");
		return "redirect:/list";
	}
	
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String bId=request.getParameter("bId");
		String id=(String)session.getAttribute("id");
		String replyContent=request.getParameter("TContent");
		int replyGroup;
		int replyStep;
		
		int value=boardservice.maxGroup(Integer.parseInt(bId));
			
		if(value==0) {
			replyGroup=1;
		} else {
			replyGroup=value+1;
		}
		replyStep=1;

		
		int result=boardservice.reply(bId, id, replyContent, replyGroup, replyStep);
		session.setAttribute("bId", bId);
		session.setAttribute("messageContent", "댓글을 작성했습니다.");
		
		return "redirect:/content_view";
	}
	
	
	@RequestMapping("/reply_reply")
	public String reply_reply(HttpServletRequest request) {
		String bId=request.getParameter("bId");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		String replyContent=request.getParameter("TContent");
		int replyGroup=Integer.parseInt(request.getParameter("replyGroup"));
		int replyStep=boardservice.maxStep(Integer.parseInt(bId),replyGroup)+1;
		
		int result=boardservice.reply(bId, id, replyContent, replyGroup, replyStep);
		session.setAttribute("bId",bId);
		session.setAttribute("replyGroup",request.getParameter("replyGroup"));
		session.setAttribute("messageContent", "답글을 작성했습니다.");
		
		return "redirect:/reply_view";
	}
	
	@RequestMapping("/reply_view")
	public ModelAndView reply_view(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String bId=null;
		String replyGroup=null;
		
		if(request.getParameter("bId")!=null&&request.getParameter("replyGroup")!=null) {
			bId=request.getParameter("bId");
			replyGroup=request.getParameter("replyGroup");
		} else {
			bId=(String)session.getAttribute("bId");
			replyGroup=(String)session.getAttribute("replyGroup");			
		}
		
		BDto dto=boardservice.contentView(bId);

		ArrayList<replyDto> list=boardservice.steplist(Integer.parseInt(bId),Integer.parseInt(replyGroup));
		
		ModelAndView view=new ModelAndView();
		view.setViewName("reply_view");
		view.addObject("content_view",dto);
		if(list!=null) {
			view.addObject("reply_view",list);
		}

		return view;
	}
	
	@RequestMapping("/reply_revise")
	public String reply_revise(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String bId=request.getParameter("bId");
		String replyContent=request.getParameter("TContent");
		int replyGroup=Integer.parseInt(request.getParameter("replyGroup"));
				
		int result=boardservice.reply_revise(Integer.parseInt(bId), replyContent, replyGroup);
		session.setAttribute("bId", bId);
		session.setAttribute("messageContent", "댓글을 수정했습니다.");
		
		return "redirect:/content_view";
	}
	
	@RequestMapping("/reply_delete")
	public String reply_delete(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String bId=request.getParameter("bId");
		String replyGroup=request.getParameter("replyGroup");
		
		int result=boardservice.reply_delete(Integer.parseInt(bId),Integer.parseInt(replyGroup));
		session.setAttribute("bId", bId);
		session.setAttribute("messageContent", "댓글 삭제를 완료했습니다.");
		
		return "redirect:/content_view";
	}
	
	@RequestMapping("/reply_reply_revise")
	public String reply_reply_revise(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String bId=request.getParameter("bId");
		String replyContent=request.getParameter("TContent");
		int replyGroup=Integer.parseInt(request.getParameter("replyGroup"));
		int replyStep=Integer.parseInt(request.getParameter("replyStep"));
				
		int result=boardservice.reply_reply_revise(replyContent, Integer.parseInt(bId), replyGroup, replyStep);
		session.setAttribute("bId",bId);
		session.setAttribute("replyGroup",request.getParameter("replyGroup"));
		session.setAttribute("messageContent", "댓글을 수정했습니다.");
		
		return "redirect:/reply_view";
	}
	
	@RequestMapping("/reply_reply_delete")
	public String reply_reply_delete(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String bId=request.getParameter("bId");
		String replyGroup=request.getParameter("replyGroup");
		String replyStep=request.getParameter("replyStep");
		
		int result=boardservice.reply_reply_delete(Integer.parseInt(bId),Integer.parseInt(replyGroup),Integer.parseInt(replyStep));
		session.setAttribute("bId",bId);
		session.setAttribute("replyGroup", replyGroup);
		session.setAttribute("messageContent", "댓글 삭제를 완료했습니다.");
		
		return "redirect:/reply_view";
	}
	
	
	//여기서부터 공지사항 관련 Mapping
	@RequestMapping("/manager_write_view")
	public String manager_write_view() {
		return "write_view2";
	}
	
	@RequestMapping(value="/singleImageUploader.do")
	public String simpleImageUploader(HttpServletRequest req, SmarteditorVO smarteditorVO)
			throws UnsupportedEncodingException{
		String callback=smarteditorVO.getCallback();
		String callback_func=smarteditorVO.getCallback_func();
		String file_result="";
		String result="";
		MultipartFile multiFile=smarteditorVO.getFiledata();
		try {
			if(multiFile!=null&&multiFile.getSize()>0 &&
					multiFile.getName().trim().length()!=0) {
				if(multiFile.getContentType().toLowerCase().startsWith("image/")) {
					String oriName=multiFile.getName();
					String uploadPath=req.getServletContext().getRealPath("/img");
					String path=uploadPath+"/smarteditor";
					File file=new File(path);
					if(!file.exists()) {
						file.mkdirs();
					}
					String fileName=UUID.randomUUID().toString();
					smarteditorVO.getFiledata().transferTo(new File(path+fileName));
					file_result+="&bNewLine=true&sFileName="+oriName+
											"&sFileURL=/img/smarteditor/"+fileName;
				} else {
					file_result+="&errstr=error";
				}
			} else {
				file_result+="&errstr=error";
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		result="redirect:"+callback+
						"?callback_func="+URLEncoder.encode(callback_func,"UTF-8")+file_result;
		return result;
	}
	
	
	@RequestMapping(value="/manager_write", method=RequestMethod.POST)
	public String manager_write(HttpServletRequest request) {
		HttpSession session=request.getSession();
		int size=1024*1024*10; //10M
		String file=null;
		String oriFile=null;
		int result;
		
		//파일 업로드
		try {
			String path=ResourceUtils
					.getFile("classpath:static/upload/").toPath().toString();
						
			MultipartRequest multi=new MultipartRequest(request, path, size,
											"UTF-8", new DefaultFileRenamePolicy());
			Enumeration files=multi.getFileNames();
			String str=(String)files.nextElement();
			
			file=multi.getFilesystemName(str);
			oriFile=multi.getOriginalFileName(str);
			
			
			if(multi.getParameter("bContent")==null||multi.getParameter("bContent").trim().isEmpty()) {
					session.setAttribute("messageContent","내용을 입력해주세요.");
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					return "write_view2";
				} else if(multi.getParameter("bTitle")==null||multi.getParameter("bTitle").trim().isEmpty()) {
					session.setAttribute("messageContent","제목을 입력해주세요.");
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					return "write_view2";
				}
			
			
			Map<String, String> map=new HashMap<>();
			map.put("item1", multi.getParameter("bTitle"));
			map.put("item2", multi.getParameter("bContent"));
			if(file!=null) {
				map.put("item3",file);
			} else {
				map.put("item3", "");
			}

			result=boardservice.upload2(map);
					
		} catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("messageContent", "파일 용량은 10M을 넘을 수 없습니다.");
			return "write_view2";
		}
		
		if(result==1)
		{
			session.setAttribute("messageContent", "업로드에 성공했습니다.");
			return "redirect:/list2";
		} else {
			session.setAttribute("messageContent", "업로드에 실패했습니다.");
			return "write_view2";
		}
	}
	
	@RequestMapping("/list2")
	public ModelAndView Blist2(HttpServletRequest request) {
		int nPage=1;
		ModelAndView view=new ModelAndView();
		HttpSession session=request.getSession();
		String sPage=null;
		
		if(request.getParameter("page")!=null) {
			sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}
		
		BPageInfo pinfo=boardservice.articlePage2(nPage);
		view.addObject("page", pinfo);
		
		nPage=pinfo.getCurPage();

		session.setAttribute("cpage", nPage);
		
		ArrayList<BDto> dtos=boardservice.list2(nPage);
		view.setViewName("list2");
		view.addObject("list", dtos);
		return view;
	}
	
	@RequestMapping("/content_view2")
	public ModelAndView content_view2(HttpServletRequest request) {
		String bId=null;
		HttpSession session=request.getSession();
		if(request.getParameter("bId")!=null) {
			bId=request.getParameter("bId");
		} else {
			bId=(String)session.getAttribute("bId");
		}

		BDto dto=boardservice.contentView2(bId);
		
		ModelAndView view=new ModelAndView();
		view.setViewName("content_view2");
		view.addObject("content_view",dto);


		return view;
	}
	
	@RequestMapping("/search")
	public ModelAndView search(HttpServletRequest request) {
		String Sel=request.getParameter("Sel");
		String strTitle=request.getParameter("strTitle");
		HttpSession session=request.getSession();
		ModelAndView view=new ModelAndView();
		BPageInfo pinfo=null;
		String sPage=null;
		view.setViewName("searchlist");

		int nPage=1;
		if(request.getParameter("page")!=null) {
			sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}
		
		if(Sel.equals("제목")) {
			pinfo=boardservice.SearchPageForTitle(nPage, strTitle);
			view.addObject("page", pinfo);
			
			nPage=pinfo.getCurPage();
			
			session.setAttribute("cpage", nPage);
			session.setAttribute("Sel", Sel);
			
			ArrayList<BDto> dtos=boardservice.SearchlistForTitle(nPage, strTitle);
			view.addObject("list", dtos);
			view.addObject("strlist", strTitle);
		} else if(Sel.equals("내용")) {
			pinfo=boardservice.SearchPageForContent(nPage,strTitle);
			view.addObject("page",pinfo);
			
			nPage=pinfo.getCurPage();
			session.setAttribute("cpage", nPage);
			session.setAttribute("Sel", Sel);
			
			ArrayList<BDto> dtos=boardservice.SearchlistForContent(nPage, strTitle);
			view.addObject("list", dtos);
			view.addObject("strlist", strTitle);
		} else if(Sel.equals("작성자")) {
			pinfo=boardservice.SearchPageForWriter(nPage, strTitle);
			view.addObject("page",pinfo);
			
			nPage=pinfo.getCurPage();
			
			session.setAttribute("cpage", nPage);
			session.setAttribute("Sel", Sel);
			
			ArrayList<BDto> dtos=boardservice.SearchlistForWriter(nPage, strTitle);
			view.addObject("list", dtos);
			view.addObject("strlist",strTitle);
		} else {			
			pinfo=boardservice.articlePage(nPage);
			view.addObject("page", pinfo);
			
			nPage=pinfo.getCurPage();

			session.setAttribute("cpage", nPage);
			session.setAttribute("messageContent", "검색할 항목을 선택해주세요.");
			
			ArrayList<BDto> dtos=boardservice.list(nPage);
			view.setViewName("list");
			view.addObject("list", dtos);
		}
		
		return view;
	}
	
	@RequestMapping("/manager_modify_view")
	public ModelAndView manager_modify_view(HttpServletRequest request) {
		String bId=request.getParameter("bId");
		BDto dto=boardservice.contentView2(bId);
		
		ModelAndView view=new ModelAndView();
		view.setViewName("modify_view2");
		view.addObject("content_view",dto);

		return view;
	}
	
	@RequestMapping(value="/manager_modify", method=RequestMethod.POST)
	public String manager_modify(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		int size=1024*1024*10; //10M
		String file=null;
		String oriFile=null;
		String bId=null;
		int result;
		MultipartRequest multi=null;
		
		//파일 업로드
		try {
			String path=ResourceUtils
					.getFile("classpath:static/upload/").toPath().toString();
						
			multi=new MultipartRequest(request, path, size,
								"UTF-8", new DefaultFileRenamePolicy());
			Enumeration files=multi.getFileNames();
			String str=(String)files.nextElement();
			bId=multi.getParameter("bId");
			
			file=multi.getFilesystemName(str);
			oriFile=multi.getOriginalFileName(str);
				
			if(multi.getParameter("bContent")==null||multi.getParameter("bContent").trim().isEmpty()) {
					session.setAttribute("messageContent","내용을 입력해주세요.");
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					model.addAttribute("content_view",boardservice.contentView2(multi.getParameter("bId")));
					return "manager_modify_view";
				} else if(multi.getParameter("bTitle")==null||multi.getParameter("bTitle").trim().isEmpty()) {
					session.setAttribute("messageContent","제목을 입력해주세요.");
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					model.addAttribute("content_view",boardservice.contentView2(multi.getParameter("bId")));
					return "manager_modify_view";
				}
			
			Map<String, String> map=new HashMap<>();
			map.put("item1", multi.getParameter("bTitle"));
			map.put("item2", multi.getParameter("bContent"));
			String prevfile=boardservice.manager_filename(multi.getParameter("bId"));

			if(file!=null) {
				File DelFile=new File(path+"/"+prevfile);
				if(DelFile.exists()) {
					DelFile.delete();
				}
				map.put("item3",file);
			} else {
				if(prevfile!=null) {
					map.put("item3", prevfile);
				} else {
					map.put("item3","");
				}
			}
			
			map.put("item4", multi.getParameter("bId"));
			
			result=boardservice.manager_modify(map);

					
		} catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("messageContent", "파일 용량은 10M을 넘을 수 없습니다.");
			model.addAttribute("content_view",boardservice.contentView2(multi.getParameter("bId")));
			return "manager_modify_view";
		}
		
		if(result==1)
		{
			session.setAttribute("messageContent", "글을 수정하였습니다.");
			session.setAttribute("bId", bId);
			return "redirect:/content_view2";
		} else {
			session.setAttribute("messageContent", "글 수정에 실패했습니다.");
			model.addAttribute("content_view",boardservice.contentView2(multi.getParameter("bId")));
			return "manager_modify_view";
		}
	}
	
	@RequestMapping("/manager_bdelete")
	public String manager_delete(HttpServletRequest request) {
		String bId=request.getParameter("bId");
		HttpSession session=request.getSession();
		String fileName=boardservice.manager_filename(bId);
		String path;
		try {
			path=ResourceUtils
					.getFile("classpath:static/upload/").toPath().toString();
				int result=boardservice.manager_delete(Integer.parseInt(bId));
				File file=new File(path+"/"+fileName);
				if(file.exists()) {
					file.delete();
				}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		session.setAttribute("messageContent", "글 삭제를 완료하였습니다.");
		return "redirect:/list2";
	}
	
	@RequestMapping("/search2")
	public ModelAndView search2(HttpServletRequest request) {
		String Sel=request.getParameter("Sel");
		String strTitle=request.getParameter("strTitle");
		HttpSession session=request.getSession();
		ModelAndView view=new ModelAndView();
		BPageInfo pinfo=null;
		String sPage=null;
		view.setViewName("searchlist2");

		int nPage=1;
		if(request.getParameter("page")!=null) {
			sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}
		
		if(Sel.equals("제목")) {
			pinfo=boardservice.SearchPageForTitle2(nPage, strTitle);
			view.addObject("page", pinfo);
			
			nPage=pinfo.getCurPage();
			
			session.setAttribute("cpage", nPage);
			session.setAttribute("Sel", Sel);
			
			ArrayList<BDto> dtos=boardservice.SearchlistForTitle2(nPage, strTitle);
			view.addObject("list", dtos);
			view.addObject("strlist", strTitle);
		} else if(Sel.equals("내용")) {
			pinfo=boardservice.SearchPageForContent2(nPage,strTitle);
			view.addObject("page",pinfo);
			
			nPage=pinfo.getCurPage();
			session.setAttribute("cpage", nPage);
			session.setAttribute("Sel", Sel);
			
			ArrayList<BDto> dtos=boardservice.SearchlistForContent2(nPage, strTitle);
			view.addObject("list", dtos);
			view.addObject("strlist", strTitle);
		} else {			
			pinfo=boardservice.articlePage2(nPage);
			view.addObject("page", pinfo);
			
			nPage=pinfo.getCurPage();

			session.setAttribute("cpage", nPage);
			session.setAttribute("messageContent", "검색할 항목을 선택해주세요.");
			
			ArrayList<BDto> dtos=boardservice.list2(nPage);
			view.setViewName("list2");
			view.addObject("list", dtos);
		}
		
		return view;
	}
	
	@RequestMapping("/company")
	public String company() {
		return "company";
	}
	
	@RequestMapping("/companyinfomation")
	public String companyinformation() {
		return "companyinfomation";
	}
	
	
}