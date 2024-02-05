package com.sparta.todotodo.todo.service;

import com.sparta.todotodo.todo.dto.TodoRequestDto;
import com.sparta.todotodo.todo.dto.TodoResponseDto;
import com.sparta.todotodo.todo.entity.Todo;
import com.sparta.todotodo.todo.repository.TodoRepository;
import com.sparta.todotodo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public void createTodo(User user, TodoRequestDto todoRequestDto) {
        Todo todo = new Todo(user, todoRequestDto);
        todoRepository.save(todo);
    }

    public List<TodoResponseDto> getTodoList(User user) {
        return todoRepository.findAllByUser(user)
                .stream()
                .map(TodoResponseDto::new)
                .toList();
    }

    public TodoResponseDto getTodo(User user, Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 적이 없는 할일입니다.")
        );

        return new TodoResponseDto(todo);
    }
}