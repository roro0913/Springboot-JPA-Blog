package com.roh.blog.service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.roh.blog.model.RoleType;
import com.roh.blog.model.User;
import com.roh.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서  Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository; 

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@Transactional
	public void join(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);//해쉬화\
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		//이 부분이 void여도 handler에서 exception을 잡아 처리해주기 때문에 에러처리 필요 x
		userRepository.save(user);
	}
	@Transactional
	public void updateUserInfo(User user) {
		//수정시에는 영속성 컨텍스트 User오브젝트를 영속화시키고, 영속화된 User오브젝트를 수정
		//①select를 해서 User오브젝트를 DB로 부터 가져와야 영속화 시킬 수 있음.
		//영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌.
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원찾기 실패");
		});
		//Validation 체크
		if(persistance.getOauth()==null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String enxPassword = encoder.encode(rawPassword);
			persistance.setPassword(enxPassword);
			persistance.setEmail(user.getEmail());	
		}

		//회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 쳐짐.
		//영속화된 persistance객체의 변화가 감지되면 더티체킹이 되어서 변화된 것에 대한 업데이트 문을 자동으로 날려준다.
	
	}
	
//	@Transactional(readOnly = true) //Select할 때 트랜잭션이 시작, 서비스 종료시에 트랜잭션 종료(정합성 지켜줌.)
//	public User login(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	
	@Transactional(readOnly = true)
	public User findUser(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
}
