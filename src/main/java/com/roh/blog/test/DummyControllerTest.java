package com.roh.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.apache.catalina.util.RequestUtil;
import org.hibernate.annotations.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roh.blog.model.RoleType;
import com.roh.blog.model.User;
import com.roh.blog.repository.UserRepository;



@RestController //html파일이 아니라 data를 리턴해주는 controller
public class DummyControllerTest {
	
	//http://localhost:7070/blog/dummy/join(요청)
	//http의 body에 username, password, email데이터를 가지고 요청
//	@PostMapping("/dummy/join")
//	public String join(String username, String password, String email) {  // 변수에 적기만 하면 key=value형을 변수로 받아준다.(약속된 규칙)
//		System.out.println("username " + username);
//		System.out.println("password " + password);
//		System.out.println("email " + email);
//		
//		return "회원가입이 완료되었습니다.";
//	}
	@Autowired //의존성 주입
	//Autowired를 붙여주면 DummyControllerTest클래스가 메모리에 뜰 때
	//같이 메모리에 띄워준다. 
	//단 UserRepository라는 타입으로 스프링이 관리하고 있는 객체가 있다면(=UserRepository가 스프링 메모리에 떠있다면) 
	//userRepository변수에 쏙 넣어줘라 라는 의미
	private UserRepository userRepository;
	
	/*
	 * update
	 */
	
	//save함수는 id를 전달하지 않으면 insert를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update
	//sqve함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해요
	@Transactional //함수 종료시 자동 커밋, 변경이 있을 때만 ( 변경을 체크 = 더티 체킹)
	//주소가 똑같아도 요청 방식이 get이냐 put이냐에 따라 구분됨.
	@PutMapping("/dummy/user/{id}")
	// @RequestBody : json을 받을 수 있음.
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {//@RequestBody : json데이터를 요청=> Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아줌.
		System.out.println(id);
		System.out.println(requestUser.getUsername());
		System.out.println(requestUser.getPassword());
		System.out.println(requestUser.getEmail());
		
		//java는 매개변수에 함수를 집어넣을 수가 없다
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당유저는 없습니다. id : " + id);
		});
		user.setPassword(requestUser.getPassword());
		user.setUsername(requestUser.getUsername());
		user.setEmail(requestUser.getEmail());
		
		//save는 insert를 위해 사용, 만약 값을 지정하지 않으면 null로 들어감.
		//userRepository.save(user);
		
		//@@Transactional을 붙였을 때 save를 하지 않아도 저장됨 : 더티 체킹
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * insert
	 */
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
		Page<User> pagingUsers = userRepository.findAll(pageable);
		List<User> users = pagingUsers.getContent();
		
		if(pagingUsers.isFirst()) {
			
		}
		else if (pagingUsers.isLast()) {
			
		}
		return pagingUsers;
	}
	
	//{id}주소로 파라미터를 전달 받을 수 있음.
	// http://localhost:7070/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) { //변수 이름을 id로 꼭 맞춰줘야 함. 그래야 자동으로 넣어줌.
		
		// findById리턴 타입이 왜 optional이냐, user/4을 찾으면 db에서 못찾아 옴 : user가 null이 됨.
		//그럼 프로그램에 문제가 있음. 그러므로 optional로 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해
		
		// 첫번째 옵션 : get() -> 나는 null이 return될 일이 절대없어.
		//User user = userRepository.findById(id).get();
		
		// 두번째 옵션 : 빈객체 리턴
//		User user = userRepository.findById(id).orElseGet(new Supplier<User> () {
//			//Supplier는 인터페이스인데 인터페이스를 new 하려면 익명클래스를 만들어야함.
//			@Override
//			public User get() {
//				// TODO Auto-generated method stub
//				return new User(); //비어있는 User타입 객체를 user에 넣어줌.
//			}
//		});
		
		
		// 세번째 옵션 : 에러 메세지 리턴
//		User user = userRepository.findById(id).orElseThrow(new Supplier <IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				// TODO Auto-generated method stub
//				return new IllegalArgumentException("해당유저는 없습니다. id : " + id);
//			}
//		}
//		);
		
		// 람다식 
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당유저는 없습니다. id : " + id);
		});
		
		
		
		// user객체 = 자바 오브젝트 
		// >>>restController는 데이터를 돌려주는데, 브라우저는 user객체 이해 못함.
		// 변환(웹 브라우저가 이해할 수 있는 데이터)이 필요 -> json(Gson라이브러리)
		// 스프링부트 = MEssageConverter라는 애가 응답시에 자동 작동
		// 만약 자바 오브젝트를 리턴하게 되면 MEssageConverter가 Jackson라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줌
		return user;
	}
	
	
	@PostMapping("/dummy/join")
	public String join(User user) {  // orm의 강력한 힘 : 오브젝트로 받을 수 있게 해준다.
		System.out.println("id " + user.getId());
		System.out.println("username " + user.getUsername());
		System.out.println("password " + user.getPassword());
		System.out.println("email " + user.getEmail());
		user.setRole(RoleType.USER);
		userRepository.save(user);
		
		return "회원가입이 완료되었습니다.";
	}
}
