package com.project.springboot.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.project.springboot.dao.IuserDao;
import com.project.springboot.dto.userDto;

@Service
public class userDaoService implements IuserDaoService
{
	@Autowired
	IuserDao dao;
	
	@Override
	public int userCheck(String id) {
		return dao.userCheckDao(id);
	}
	
	@Override
	public int register(Map<String, String> map) {
		return dao.registerDao(map);
	}
	
	@Override
	public userDto user(String id) {
		return dao.userDao(id);
	}
	
	@Override
	public int update(Map<String, String> map) {
		return dao.update(map);
	}
	
	@Override
	public int delete(String id) {
		return dao.delete(id);
	}
	
	
	//네이버 이미지 캡차 키
	public Map<String,String> NaverCaptcha() {  
		String clientId="";	//애플리케이션 클라이언트 아이디값
		String clientSecret="";	//애플리케이션 클라이언트 시크릿값";
		JSONParser jsonParser=new JSONParser();
		JSONObject jsonObject=null;
		String code="0"; //키 발급시 0, 캡차 이미지 비교시 1로 세팅
		
		String apiURL="https://openapi.naver.com/v1/captcha/nkey?code="+code;

		Map<String, String> requestHeaders=new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		
		try {
			jsonObject=(JSONObject)jsonParser.parse(get(apiURL,requestHeaders));
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		//네이버 캡차 이미지 수신
		String key=(String)jsonObject.get("key");
		apiURL="https://openapi.naver.com/v1/captcha/ncaptcha.bin?key="+key;
		
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody=get2(apiURL, requestHeaders);
		
		Map<String,String> map=new HashMap<>();
		map.put("key", key);
		map.put("Image",responseBody);
		
		return map;
	}
	
	private static String get(String apiUrl, Map<String, String> requestHeaders) {

		HttpURLConnection con=connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for(Map.Entry<String, String> header:requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}
			
			int responseCode=con.getResponseCode();
			if(responseCode==HttpURLConnection.HTTP_OK) {	//정상 호출
				return readBody(con.getInputStream());
			} else {	//에러 발생
				return readBody(con.getErrorStream());
			}
		} catch(IOException e) {
			throw new RuntimeException("API요청과 응답 실패",e);
		} finally {
			con.disconnect();
		}
	}
	
	private static String get2(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con=connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for(Map.Entry<String, String> header:requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}
			
			int responseCode=con.getResponseCode();
			if(responseCode==HttpURLConnection.HTTP_OK) {	//정상 호출
				return getImage(con.getInputStream());
			} else {	//에러 발생
				return error(con.getErrorStream());
			}
		} catch(IOException e) {
			throw new RuntimeException("API요청과 응답 실패",e);
		} finally {
			con.disconnect();
		}
	}
	
	public static HttpURLConnection connect(String apiUrl) {
		try {
			URL url=new URL(apiUrl);
			return (HttpURLConnection)url.openConnection();
		} catch(MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : "+ apiUrl, e);
		} catch(IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : "+apiUrl, e);
		}
	}
	
	private static String readBody(InputStream body) {
		InputStreamReader streamReader= new InputStreamReader(body);
		
		
		try(BufferedReader lineReader=new BufferedReader(streamReader)) {
			StringBuilder responseBody=new StringBuilder();
			
			String line;
			while((line=lineReader.readLine())!=null) {
				responseBody.append(line);
			}
			
			return responseBody.toString();
		} catch(IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
	
	private static String getImage(InputStream is) {
		int read;
		byte[] bytes=new byte[1024];
		String path="";
		
		try {
			path=ResourceUtils
				.getFile("classpath:static/navercaptcha").toPath().toString();
			File file=new File(path);
			File[] fileList=file.listFiles();
			for(int i=0;i<fileList.length;i++) {
				fileList[i].delete();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		//랜덤한 이름으로 파일 생성
		String filename=Long.valueOf(new Date().getTime()).toString();
		File f=new File(path+"/"+filename+".jpg");
		try(OutputStream outputStream=new FileOutputStream(f)) {
			f.createNewFile();
			while((read=is.read(bytes))!=-1) {
				outputStream.write(bytes, 0, read);
			}
			return filename+".jpg";
		} catch(IOException e) {
			throw new RuntimeException("이미지 캡차 파일 생성에 실패 했습니다.",e);
		}
	}
	
	private static String error(InputStream body) {
		InputStreamReader streamReader=new InputStreamReader(body);
		
		try(BufferedReader lineReader=new BufferedReader(streamReader)) {
			StringBuilder responseBody=new StringBuilder();
			
			String line;
			while((line=lineReader.readLine())!=null) {
				responseBody.append(line);
			}
			
			return responseBody.toString();
		} catch(IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
	
	public int captchaResult(String value, String key) {
		String clientId="";	//애플리케이션 클라이언트 아이디값
		String clientSecret="";	//애플리케이션 클라이언트 시크릿값";
		String code="1"; //키 발급시 0, 캡차 이미지 비교시 1로 세팅
		JSONParser jsonParser=new JSONParser();
		JSONObject jsonObject=null;
		
		String apiURL="https://openapi.naver.com/v1/captcha/nkey?code="+code+"&key="+key+"&value="+value;

		Map<String, String> requestHeaders=new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);			
		try {
			jsonObject=(JSONObject)jsonParser.parse(get(apiURL,requestHeaders));
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		boolean result=(boolean)jsonObject.get("result");
		if(result) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public String getuserProfile(String id) {
		return dao.getuserProfile(id);
	}
	
	@Override
	public String member_check(String password) {
		return dao.member_check(password);
	}
	
	@Override
	public int member_register(Map<String, String> map) {
		return dao.member_registerDao(map);
	}
}
