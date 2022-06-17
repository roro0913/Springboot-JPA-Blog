package com.roh.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.roh.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	// 매개변수로 @AuthenticationPrincipal PrincipalDetail principal을 받을 수 있다.
	// "/WEB-INF/views/index.jsp"
	@GetMapping({ "", "/" })
	public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("boards", boardService.getBoardList(pageable)); // "boards"는 컬렉션 변수
		return "index"; //viewResolver(view이름으로 사용될 view객체 맵핑=응답뷰를 랜더링)가 작동!
	}

	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board" ,boardService.contentsDetail(id));
		return "board/detail";
	}
	
	@GetMapping({ "/board/saveForm" })
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.contentsDetail(id));
		return "board/updateForm";
	}
}
