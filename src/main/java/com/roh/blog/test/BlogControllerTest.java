package com.roh.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//스프링이 com.roh.demo패키지 이하를 스캔해서 모든 파일을 메모리에 new 하는 것은 아니고
// 특정 어노테이션이 붙이었는 클래스 파일들을 new해서 (IoC)스프링 컨테이너에 관리해줌
@RestController 
public class BlogControllerTest {
	@GetMapping("/test/hello")
	//http://localhost:7070/test/hello
	public String hello() {
		return "<h1>Hello</h1>";
	}

}