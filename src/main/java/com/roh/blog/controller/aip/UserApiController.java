package com.roh.blog.controller.aip;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.roh.blog.config.auth.PrincipalDetail;
import com.roh.blog.dto.ResponseDto;
import com.roh.blog.model.RoleType;
import com.roh.blog.model.User;
import com.roh.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	//	@Autowired
	//	private HttpSession session;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	// 요청 받는게 json이므로 @Requestbody
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출됨");
		// 실제로 DB에 insert하고 아래에서 return
		userService.join(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON으로 변환해서 리턴(Jackson이라는 라이브러리가)
	}
	
	/**
	//전통적인 로그인 방식!!
	@PostMapping("/api/user/login")
	//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){ //이렇게 매개변수로 session을 줘도 되고, 전역 변수로 써서 사용해도 됨
	public ResponseDto<Integer> login(@RequestBody User user) {
		System.out.println("UserApiController : login 호출됨");
		User prinsipal = userService.login(user); // prisipal(접근 주체라는 말)
		if (prinsipal != null) {
			session.setAttribute("principal", prinsipal); // 세션 생성
		}
		userService.login(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON으로 변환해서 리턴(Jackson이라는 라이브러리가)
	}
	*/
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){ //requestBody는 json 데이터를 받기 위함 : 없으면 못받음.
		//만약 requestBody가 없으면 key = value타입(x-www-form-urlencoded)의 데이터만 받을 수 있다
		userService.updateUserInfo(user);
		//여기서는 트랜잭션이 종료되기 때문에 DB값은 변경이되지만 세션값은 변경되지 않기 때문에
		//직접 세션 값 변경이 필요
		
		//세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	
}
