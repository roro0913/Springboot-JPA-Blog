package com.roh.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.roh.blog.dto.ResponseDto;

@ControllerAdvice //모든 exception이 발생하면 현재 클래스로 들어옴
@RestController
public class GlobalExceptionHandler {

	//IllegalArgumentException에 대해 대응
	@ExceptionHandler(value = IllegalArgumentException.class)
	public String handleArgumentsException(IllegalArgumentException e) {
		return "<h1>" + e.getMessage() + "</h1>";
	}
	
	//모든 예외처리 한 번에 하고 싶다면
	@ExceptionHandler(value = Exception.class)
//	public String handleAllException(Exception e) { 
//		return "<h1>" + "모든 예외처리" +  e.getMessage() + "</h1>";
//	}
	public ResponseDto<String> handleAllException (Exception e) { 
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),  e.getMessage());
	}
}
