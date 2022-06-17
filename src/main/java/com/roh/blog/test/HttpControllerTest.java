package com.roh.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//만약 사용자가 요청한 것에 대한 응답(HTML파일)인 경우, @Controller를 사용

// 사용자가 요청 한 것에 대한 응답(DATA)을 하는 controller
@RestController
public class HttpControllerTest {
	private static final String TAG = "HttpControllerTest";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//People p =new People(2, "soek", "1234" , "nomoons@naver.com");
		// id값은 그대로 고정이고 다른 값을 업데이트 하고 싶을 때 id 값을 지정하지 않으려면
		//id값이 없는 Constructor가 필요하다. 그치만 하나하나 만들어주기 힘든 상황에서는 Builder를 사용해서
		People p = People.builder().username("dd").email("?").build();
		System.out.println(TAG + "Getter" + p.getUsername());
		p.setUsername("AAA");
		System.out.println(TAG + "Setter" + p.getUsername());
		return null;
	}
	
	//인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
	//http://localhost:7070/http/get(select)
	@GetMapping("/http/get")
	//public String getTest(@RequestParam int id) {
	public String getTest(People p) {//MessageConverter(Spring boot)가 세팅
		return TAG + p.getId();
	}

	//http://localhost:7070/http/post(insert)
	@PostMapping("/http/post")	//text/plain데이터 , application/json
	// application/json타입으로 날아왔을 때 맵핑시켜주는 애 : MessageConverter(Spring boot)
	public String postTest(@RequestBody People m) {
		return "ID" + m.getEmail();
	}
	
	//http://localhost:7070/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody People p) {
		return "put 요청" + p.getId() + ", " + p.getPassword();
	}
	
	//http://localhost:7070/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
