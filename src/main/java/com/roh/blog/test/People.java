package com.roh.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
@Data //getter, setter 둘 다 하는 역할
//@AllArgsConstructor //생성자
//@RequiredArgsConstructor //final 붙은 애들에 대한 contructor을 만들어준다.
@NoArgsConstructor
/**
 * Lombok을 사용하면 생성자도 자동으로 생성할 수 있습니다. 
 * @NoArgsConstructor 어노테이션은 파라미터가 없는 기본 생성자를 생성해주고, 
 * @AllArgsConstructor 어노테이션은 모든 필드 값을 파라미터로 받는 생성자를 만들어줍니다. 
 * 마지막으로 @RequiredArgsConstructor 어노테이션은 final이나 
 * @NonNull인 필드 값만 파라미터로 받는 생성자를 만들어줍니다.
 */
public class People {
	private int Id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public People(int id, String username, String password, String email) {
		super();
		this.Id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
	
	
	
}


