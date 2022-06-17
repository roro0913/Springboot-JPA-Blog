package com.roh.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //Controller라는 어노테이션이 붙으면 보통 file을 리턴해준다.
//@Controller는 src/main/resources/statics (해당경로)의 파일을 리턴
//@ restController는 문자열을 리턴했음.
public class TempControlleTest {
	
	//http://localhost:7070/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		//파일 리턴 기본 경로 : src/main/resources/statics의 파일을 리턴하라는 것
		// 그러므로 return "home.html";은 src/main/resources/staticshome.html이 리턴된 것.
		System.out.println("tempHome");
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImage() {
		return "/a.png";
	}
	
	//http://localhost:7070/blog/temp/jsp
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//prefix : /WEB-INF/views/
		//suffix : .jsp
		// 풀네임(경로) : /WEB-INF/views/test.jsp
		return "test";
	}
}
