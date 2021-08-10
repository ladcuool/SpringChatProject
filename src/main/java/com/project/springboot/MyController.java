package com.project.springboot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.project.springboot.dto.userDto;
import com.project.springboot.service.userChatService;
import com.project.springboot.service.userDaoService;

@Controller
public class MyController {
	
	@Autowired
	userDaoService dao;
	@Autowired
	userChatService chatdao;
	@Autowired
	BCryptPasswordEncoder encoder;
	

	@RequestMapping("/")
	public String root() throws Exception {
		return "Home";
	}
		
	@RequestMapping("/userjoin")
	public String userjoin() {
		return "userjoin";
	}
	
	@RequestMapping("/join_check")
	public String join_check(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String radiovalue1="";
		String radiovalue2="";
		String radiovalue3="";
		
		if(request.getParameter("inlineRadioOptions")!=null) {
			radiovalue1=request.getParameter("inlineRadioOptions");
		}
		if(request.getParameter("inlineRadioOptions2")!=null) {
			radiovalue2=request.getParameter("inlineRadioOptions2");
		}
		if(request.getParameter("inlineRadioOptions3")!=null) {
			radiovalue3=request.getParameter("inlineRadioOptions3");
		}
		if(!radiovalue1.equals("동의함")||!radiovalue2.equals("동의함")||!radiovalue3.equals("동의함")) {
			session.setAttribute("messageContent", "모든 선택사항에 동의해주십시오.");
			return "userjoin";
		}
		
		return "redirect:/join";
	}
	
	@RequestMapping("/join")
	public String join() {
		return "join";
	}
	
	@RequestMapping("/loginForm")
	public String login(HttpServletRequest request) {
		return "loginForm";
	}
	
	@RequestMapping("/IdCheck")
	@ResponseBody
	public int userIdCheck(HttpServletRequest request) throws Exception {
		String sId=request.getParameter("userID");
		if(dao.userCheck(sId)==0) {
			HttpSession session=request.getSession();
			session.setAttribute("joinOk", "yes");
		}

		return dao.userCheck(sId);
	}
	
	@RequestMapping(value="/memberlist")
	@ResponseBody
	public List<userDto> memberlist(HttpServletRequest request) throws Exception {
		return chatdao.memberList();
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerUser(@ModelAttribute("dto") @Valid userDto dto, BindingResult result, HttpServletRequest request) {
		String idCheck="";
		HttpSession session=request.getSession();

		if(session.getAttribute("joinOk")!=null) {
			idCheck=(String)session.getAttribute("joinOk");
		}
		
		if(result.getFieldError("userID")!=null) {
			session.setAttribute("messageContent",result.getFieldError("userID").getDefaultMessage());
			return "join";
		} else if(!idCheck.equals("yes")) {
			session.setAttribute("messageContent","아이디 중복확인을 해주세요.");
			session.removeAttribute("joinOk");
			return "join";
		} else if(result.getFieldError("userPassword")!=null) {
			session.setAttribute("messageContent", result.getFieldError("userPassword").getDefaultMessage());
			return "join";
		} else if(result.getFieldError("userName")!=null) {
			session.setAttribute("messageContent", result.getFieldError("userName").getDefaultMessage());
			return "join";
		} else if(request.getParameter("userGender").equals("선택")) {
			session.setAttribute("messageContent", "성별을 선택해주세요.");
			return "join";
		} else if(request.getParameter("userAge").toString().length()!=8) {
			session.setAttribute("messageContent", "생년월일을 8자로 입력해주세요.");
			return "join";
		} else if(result.getFieldError("userAddress")!=null) {
			session.setAttribute("messageContent", result.getFieldError("userAddress").getDefaultMessage());
			return "join";
		} else if(result.getFieldError("userEmail")!=null) {
			session.setAttribute("messageContent", result.getFieldError("userEmail").getDefaultMessage());
			return "join";
		}
		
		dto.setUserPassword(encoder.encode(dto.getUserPassword()));
		
		Map<String, String> map=new HashMap<String, String>();
		map.put("item1", dto.getUserID());
		map.put("item2",dto.getUserPassword());
		map.put("item3", dto.getUserName());
		map.put("item4", dto.getUserGender());
		map.put("item5", dto.getUserAge());
		map.put("item6", dto.getUserAddress());
		map.put("item7",dto.getUserEmail());
			
		int nResult=dao.register(map);
			
		session.setAttribute("messageContent","회원가입이 완료되었습니다.");
		
		return "redirect:/loginForm";
	}
	
	@RequestMapping("/userInfo")
	public String userInfo(Model model, HttpServletRequest request) {
		HttpSession session=request.getSession();
		model.addAttribute("dto",dao.user((String)session.getAttribute("id")));
		return "users";
	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String userUpdate(HttpServletRequest request, Model model) {
		
		HttpSession session=request.getSession();
		int size=1024*1024*10; //10M
		String file=null;
		String oriFile=null;
		int result;
		String prev="";
		
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
			
			
			if(multi.getParameter("userPassword")==null||multi.getParameter("userPassword").trim().isEmpty()||
					multi.getParameter("userPassword").length()<8||multi.getParameter("userPassword").length()>20) {
					session.setAttribute("messageContent","비밀번호는 최소 8자리 최대 20자리입니다.");
					model.addAttribute("dto",dao.user((String)session.getAttribute("id")));
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					return "users";
				} else if(!multi.getParameter("userPassword").equals(multi.getParameter("userPassword2"))) {
					session.setAttribute("messageContent","비밀번호를 한번 더 입력해주세요.");
					model.addAttribute("dto",dao.user((String)session.getAttribute("id")));
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					return "users";
				} else if(multi.getParameter("userAddress")==null||multi.getParameter("userAddress").trim().isEmpty()) {
					session.setAttribute("messageContent","주소를 입력해주세요.");
					model.addAttribute("dto",dao.user((String)session.getAttribute("id")));
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					return "users";
				} else if(multi.getParameter("userEmail")==null||multi.getParameter("userEmail").trim().isEmpty()) {
					session.setAttribute("messageContent","이메일을 입력해주세요.");
					model.addAttribute("dto",dao.user((String)session.getAttribute("id")));
					File DelFile=new File(path+"/"+file);
					if(DelFile.exists()) {
						DelFile.delete();
					}
					return "users";
				}
			
			prev=dao.user(multi.getParameter("userID")).getUserProfile();
			File prevFile=new File(path+"/"+prev);
			
			if(file!=null) {
				if(prevFile.exists()&!prev.equals("noimage.png")) {
					prevFile.delete();
				}
			}

			
			Map<String, String> map=new HashMap<>();
			
			map.put("item1", multi.getParameter("userID"));
			map.put("item2", encoder.encode(multi.getParameter("userPassword")));
			map.put("item3", multi.getParameter("userAddress"));
			map.put("item4", multi.getParameter("userEmail"));
			if(file!=null) {
				map.put("item5",file);
			} else {
				map.put("item5",prev);
			}

			result=dao.update(map);
					
		} catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("messageContent", "파일 용량은 10M을 넘을 수 없습니다.");
			model.addAttribute("dto",dao.user((String)session.getAttribute("id")));
			return "users";
		}
		
		if(result==1)
		{
			session.setAttribute("messageContent", "회원정보 수정에 성공했습니다.");
			return "redirect:/userInfo";
		} else {
			session.setAttribute("messageContent", "회원정보 수정에 실패했습니다.");
			model.addAttribute("dto",dao.user((String)session.getAttribute("id")));
			return "users";
		}
	}
		
	@RequestMapping("/userwithdrawal")
	public ModelAndView userout(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView();

		mv.setViewName("userwithdrawal");
		mv.addObject("ncaptcha",dao.NaverCaptcha());
		
		return mv;
	}
	
	@RequestMapping("/delete")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session=request.getSession();
		PrintWriter out=response.getWriter();
		String capvalue=request.getParameter("capvalue");
		String capkey=request.getParameter("key");
		String radiovalue1="";
		String radiovalue2="";
		
		if(request.getParameter("inlineRadioOptions")!=null) {
			radiovalue1=request.getParameter("inlineRadioOptions");
		}
		if(request.getParameter("inlineRadioOptions2")!=null) {
			radiovalue2=request.getParameter("inlineRadioOptions2");
		}

		int result=dao.captchaResult(capvalue,capkey);
		
		if(!radiovalue1.equals("동의함")||!radiovalue2.equals("동의함")) {
			session.setAttribute("messageContent", "선택사항에 모두 동의하지 않았습니다.");
			response.sendRedirect("userwithdrawal");
			out.flush();
			out.close();
		} else if(result!=1) {
			session.setAttribute("messageContent", "입력 사항이 맞지 않습니다. 다시 입력해주십시오.");
			response.sendRedirect("userwithdrawal");
			out.flush();
			out.close();
		} else {
			String userProfile=dao.getuserProfile((String)session.getAttribute("id"));
			dao.delete((String)session.getAttribute("id"));
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
			
			session.invalidate();
			out.println("<script>alert('회원 탈퇴를 완료하였습니다.');</script>");
			out.println("<script>location.href='/'</script>");
			out.flush();
			out.close();
		} 
	}
	
	@RequestMapping("/chatuserInfo")
	@ResponseBody
	public userDto chatuserInfo(HttpServletRequest request) {
		String userId=request.getParameter("userID");
		return dao.user(userId);
	}
	
	@RequestMapping("/membercheck")
	public String membercheck() {
		return "membercheck";
	}
	
	@RequestMapping(value="/member_join", method=RequestMethod.POST)
	public String member_join(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String Password=request.getParameter("memberPassword");
		String result=dao.member_check(Password);
		String url=null;
		
		if(result!=null) {
			url="member_join";
		} else {
			session.setAttribute("messageContent", "입력하신 사원번호는 존재하지 않습니다.");
			url="redirect:/membercheck";
		}
		
		return url;
	}
	
	@RequestMapping(value="/member_register", method=RequestMethod.POST)
	public String member_register(@ModelAttribute("dto") @Valid userDto dto, BindingResult result, HttpServletRequest request) {
		String idCheck="";
		HttpSession session=request.getSession();

		if(session.getAttribute("joinOk")!=null) {
			idCheck=(String)session.getAttribute("joinOk");
		}
		
		if(result.getFieldError("userID")!=null) {
			session.setAttribute("messageContent",result.getFieldError("userID").getDefaultMessage());
			return "member_join";
		} else if(!idCheck.equals("yes")) {
			session.setAttribute("messageContent","아이디 중복확인을 해주세요.");
			session.removeAttribute("joinOk");
			return "member_join";
		} else if(result.getFieldError("userPassword")!=null) {
			session.setAttribute("messageContent", result.getFieldError("userPassword").getDefaultMessage());
			return "member_join";
		} else if(result.getFieldError("userName")!=null) {
			session.setAttribute("messageContent", result.getFieldError("userName").getDefaultMessage());
			return "member_join";
		} else if(request.getParameter("userGender").equals("선택")) {
			session.setAttribute("messageContent", "성별을 선택해주세요.");
			return "member_join";
		} else if(request.getParameter("userAge").toString().length()!=8) {
			session.setAttribute("messageContent", "생년월일을 8자로 입력해주세요.");
			return "member_join";
		} else if(result.getFieldError("userAddress")!=null) {
			session.setAttribute("messageContent", result.getFieldError("userAddress").getDefaultMessage());
			return "member_join";
		} else if(result.getFieldError("userEmail")!=null) {
			session.setAttribute("messageContent", result.getFieldError("userEmail").getDefaultMessage());
			return "member_join";
		}
		
		dto.setUserPassword(encoder.encode(dto.getUserPassword()));
		
		Map<String, String> map=new HashMap<String, String>();
		map.put("item1", dto.getUserID());
		map.put("item2",dto.getUserPassword());
		map.put("item3", dto.getUserName());
		map.put("item4", dto.getUserGender());
		map.put("item5", dto.getUserAge());
		map.put("item6", dto.getUserAddress());
		map.put("item7",dto.getUserEmail());
			
		int nResult=dao.member_register(map);
			
		session.setAttribute("messageContent","회원가입이 완료되었습니다.");
		
		return "redirect:/loginForm";
	}
}
