package com.roh.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.roh.blog.config.auth.PrincipalDetailService;


@Configuration //빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것 (IoC관리)
@EnableWebSecurity //필터를 거는 것.(시큐리티 필터가 등록 됨) 시큐리티 필터 추가 = 스프링 시큐리티가 활성화 되어있는데 어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 것.
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //이때부터는 IoC가 된다. return값 자체를 스프링이 관리
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	//시큐리티가 대신 로그인 해줄 때 password가로채기를 하는데
	//해당 password가 뭘로 해쉬암호화되어 회원가입이 되었는지 알아야
	//같은 해쉬로 암호화시켜 DB에 있는 해쉬암호와 비교가 가능
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD()); //principalDetailService가 없으면 pw비교 못함.
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //csrf토큰 비활성화 (테스트 시 걸어두는 게 좋다.
			.authorizeHttpRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**","/image/**","/dummy/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해줌.
				; 
	}

	//authentificationManager을 하나 만듬
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
