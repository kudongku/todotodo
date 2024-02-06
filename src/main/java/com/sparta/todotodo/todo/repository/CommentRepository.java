package com.sparta.todotodo.todo.repository;

import com.sparta.todotodo.todo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
