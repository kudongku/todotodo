package com.sparta.todotodo.todo.repository;

import com.sparta.todotodo.todo.entity.Todo;
import com.sparta.todotodo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUser(User user);
}
