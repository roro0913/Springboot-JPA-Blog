package com.roh.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.roh.blog.model.User;

import lombok.Getter;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면
//UserDetails타입의 오브젝트를 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
@Getter
public class PrincipalDetail implements UserDetails{
	
	private User user; //콤포지션 = 객체를 품고 있는 것
	
	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정이 만료되지 않았는지를 리턴한다.(true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겼는지 안잠겼는지 리턴한다. (true : 안잠김)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 만료되지 않았는지 리턴한다. (true : 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성화(사용가능)인지  리턴한다. (true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
	//계정이 가지는 권한목록을 리턴한다. (true : 활성화) (권한이 여러개 있을 수 있어서 루프를 돌아야하는데 우리는 한개만)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
//		collectors.add(new GrantedAuthority() {
//			
//			@Override
//			//자바는 오브젝트를 넣어줄 수는 있지만 메소드만 넘길 수는 없다. 그래서 아래처럼 리턴해주기 위해 클래스 전체를 때려넣음.
//			public String getAuthority() {
//				// TODO Auto-generated method stub
//				//ROLE_USER
//				return "ROLE_" + user.getRole(); //spring 에서 ROLE_을 앞에 꼭 붙여야 한다. : 규칙
//			}
//		});
		//하지만 GrantedAuthority클래스의 경우 메소드가 getAuthority하나 뿐이니 아래처럼 람다식을 사용
		collectors.add(()->{return "ROLE_" + user.getRole();});
		
		return collectors;
	}

}
