package com.roh.blog.repository;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roh.blog.model.User;

//jsp로 치면 DAO로 생각하면 됨. 즉, 비워두면 JpaRepository가 가진 모든 함수를 상속함.
//이렇게 하면  bean으로 등록이 되나요? =Spring ioc에서 객체로 가지고 있나요? 
//그래야지만  필요한 곳에서 injection해서 DI(Dependency Injection:의존성 주입)를 할 수 있음 : 

//원래라면 @Repository로 해야 처음 spring이 스캔할 때 메모리에 띄워주는데
//이렇게 하면 자동으로 bean등록이 된다.
public interface UserRepository extends JpaRepository<User, Integer> {
	
	//SELECT * FROM user WHERE username = ?1; 
	Optional<User> findByUsername(String username);
	
	
	
	
	
	
	
	
	// 방법 1
	// JPA Naming전략(메소드 이름을 형식에 맞게 작성하면 알아서 sql작성
	// SELECT * FROM user WHERE username=? AND password=?;
	//User findByUsernameAndPassword(String username, String password);

	// 방법 2
	// Query에 써 넣은 sql이 작동
	//	@Query(value = "SELECT * FROM user WHERE username=?1 AND password=?2", nativeQuery = true)
	//	User login(String username, String password); //return은 User객체로 
}
