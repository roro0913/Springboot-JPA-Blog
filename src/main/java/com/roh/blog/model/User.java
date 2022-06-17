package com.roh.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴
//ORM -> Java(다른 모든 언어) Object -> 테이블로 맵핑해주는 것
@Entity // User클래스가 MySQL에 테이블이 생성된다.
//@DynamicInsert //insert시의 null인 필드를 제외시켜줌.
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB넘버링 전략을 따라간다. 즉 Oracle을 사용하면 sequence를 사용한다는 거고, mysql에서는 auto increment를 사용하겠다는 것.
	private int id; //시퀸스, auto_increment
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; //아이디

	@Column(nullable = false, length = 100) //=>비밀번호를 해쉬로 암호화 할 것(길이가 길어짐)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("'user'") //컬럼에 디폴트 값을 줄 수 있다.
	@Enumerated(EnumType.STRING) //DB는 EnumType가 없으니, 해당 enum은 string 타입이다하고 알려줌.
	private RoleType role; //Enum을 쓰는 게 좋다. //오타 방지를 위해 admin, user, manager만 넣을 수 있도록(=도메인 : 범위)
	
	private String oauth; //kakao, google
	
	@CreationTimestamp  // 시간이 자동 입력
	private Timestamp createDate;
	
	
}
