package com.roh.blog.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roh.blog.model.KakaoProfile;
import com.roh.blog.model.OauthToken;

@Service
public class KakaoTokenService {

	private static String grant_type = "authorization_code";
	private static String client_id = "8cbf61db5f2d4ba1ebf22575adbcedbc";
	private static String redirect_uri = "http://localhost:7070/auth/kakao/callback";

	public KakaoProfile kakaoLogin(String code) throws JsonMappingException, JsonProcessingException {
		
		/* * kakao에 토큰요청 및 생성 */
		OauthToken oauthToken = getAccessToken(code);
		
		/*	 * kakao에 토큰으로 사용자 정보 요청*/
		KakaoProfile kakaoProfile = getKakaoProfile(code, oauthToken);
		return kakaoProfile;
	}

	public OauthToken getAccessToken(String code) throws JsonMappingException, JsonProcessingException {
		// 여기(controller)에서 jsp의 Form처럼 데이터를 post형식으로 kakao 서버 쪽으로 던져야하는데
				// HttpsURLConnection
				// Retrofit2 (안드로이드에서 많이 사용)
				// OkHttp
				// Rest Template
				RestTemplate rt = new RestTemplate();

				// HttpHeader 오브젝트 생성
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

				// HttpBody오브젝트 생성
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				params.add("grant_type", grant_type);
				params.add("client_id", client_id);
				params.add("redirect_uri", redirect_uri);
				params.add("code", code);

				// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
				HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

				// Http요청하기 - Post방식으로 - 그리고 response변수의 응답 받움
				ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", // 보낼 주소
						HttpMethod.POST, // 보내는 형식
						kakaoTokenRequest, // param, header 한 번에 보냄
						String.class// 응답받을 타입
				);

				// Gson, Json Simple, ObjectMapper 등을 이용해 json형식으로 맵핑
				ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(response.getBody(), OauthToken.class);
	}
	
	
	public KakaoProfile getKakaoProfile(String code, OauthToken oauthToken)throws JsonMappingException, JsonProcessingException {
		RestTemplate rt = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

		// Http요청하기 - Post방식으로 - 그리고 response변수의 응답 받움
		ResponseEntity<String> response = rt.exchange("https://kapi.kakao.com/v2/user/me", // 보낼 주소
				HttpMethod.POST, // 보내는 형식
				kakaoProfileRequest, // param, header 한 번에 보냄
				String.class// 응답받을 타입
		);

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(response.getBody(), KakaoProfile.class);
	}
}
