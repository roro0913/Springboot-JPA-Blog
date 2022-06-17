package com.roh.blog.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.roh.blog.model.Board;
import com.roh.blog.model.RoleType;
import com.roh.blog.model.User;
import com.roh.blog.repository.BoardRepository;
import com.roh.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서  Bean에 등록을 해줌. IoC를 해준다.
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void write(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> getBoardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board contentsDetail(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}

	@Transactional
	public void delete(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void modify(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		});	//영속화 완료(영속성 컨텍스트에 board가 추가됨.) DB에 있는 board데이터랑 같아짐.
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수 종료시(=Service가 종료될 때)에 트랜잭션이 종료. 이때 더티체킹 - 자동 업테이트 됨. DB쪽으로 Flush가 일어남.
	}

}
