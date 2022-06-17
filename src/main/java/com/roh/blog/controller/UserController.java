package com.roh.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roh.blog.model.KakaoProfile;
import com.roh.blog.model.OauthToken;
import com.roh.blog.model.User;
import com.roh.blog.service.KakaoTokenService;
import com.roh.blog.service.UserService;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/**허용
//그냥 주소가 /이면 index.jsp 허용
// static이하에 있는 /js/**, /css/**, image/**등은 허용
//=>인증이 필요 없는 곳에는 다 auth를 붙임
@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private KakaoTokenService kakaoTokenService;

	@Autowired
	private  UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/auth/joinForm")
	public String join() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String login() {
		return "user/loginForm";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}

	// @JsonIgnoreProperties(ignoreUnknown=true)
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallBack(String code) throws JsonMappingException, JsonProcessingException {
		
		KakaoProfile kakaoProfile = kakaoTokenService.kakaoLogin(code);
		String kakaoUsername = kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId();
		User kakaoUser = User.builder()
				.username(kakaoUsername)
				.password(cosKey)
				.oauth("kakao")
				.email(kakaoProfile.getKakao_account().getEmail()).build();
		
		//가입자 or 비가입자 나눠서 처리
		User originUser = userService.findUser(kakaoUser.getUsername());
		if(originUser.getUsername()==null) {
			System.out.println("기존 회원이 아닙니다.");
			userService.join(kakaoUser);
		}
		//기존 회원의 경우 로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaoUser.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/"; //클래스에 @ResponseBody 지워줘야 viewResolve호출해서 파일을 찾아갈 것.(아니면 String형으로 data리턴)
	}

}
