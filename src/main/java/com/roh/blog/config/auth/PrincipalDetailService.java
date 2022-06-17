package com.roh.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.roh.blog.model.User;
import com.roh.blog.repository.UserRepository;


@Service //Bean등록
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//스프링이 로그인 요청을 가로챌 때, username, password 변수 2개 가로채는데
	//psssword부분 처리는 알아서 함.
	//useranme이 DB에 있는지만 확인해주면 되는데, 이를 loadUserByUsername여기서 해준다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
						return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
						});
		return new PrincipalDetail(principal); //시큐리티의 세션에 유저정보가 저장이 됨.(타입 : userDetails)
		//이것 처럼 userDetails를 새로 만들어서 유저 정보 넣어서 넘겨주지 않으면 처음 id는 user이고 pw는 콘솔창에 뜬 유저 정보만 전달됨.
	}
	

}
