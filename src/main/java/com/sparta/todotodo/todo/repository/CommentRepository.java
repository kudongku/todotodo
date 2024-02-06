package com.sparta.todotodo.todo.repository;

import com.sparta.todotodo.todo.entity.Comment;
import com.sparta.todotodo.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTodo(Todo todo);
}
