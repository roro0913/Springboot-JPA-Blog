package com.roh.blog.controller.aip;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.roh.blog.config.auth.PrincipalDetail;
import com.roh.blog.dto.ResponseDto;
import com.roh.blog.model.Board;
import com.roh.blog.model.RoleType;
import com.roh.blog.model.User;
import com.roh.blog.service.BoardService;
import com.roh.blog.service.UserService;

import lombok.Delegate;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	// 요청 받는게 json이므로 @Requestbody
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail printcipal ) {
		boardService.write(board, printcipal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.delete(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
		boardService.modify(id,board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
}
