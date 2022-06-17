package com.roh.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.roh.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
