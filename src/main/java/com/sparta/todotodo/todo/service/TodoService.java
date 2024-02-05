package com.sparta.todotodo.todo.service;

import com.sparta.todotodo.todo.dto.TodoRequestDto;
import com.sparta.todotodo.todo.dto.TodoResponseDto;
import com.sparta.todotodo.todo.entity.Todo;
import com.sparta.todotodo.todo.repository.TodoRepository;
import com.sparta.todotodo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.sparta.todotodo.user.entity.UserRole.ADMIN;
import static com.sparta.todotodo.user.entity.UserRole.USER;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public void createTodo(User user, TodoRequestDto todoRequestDto) {
        Todo todo = new Todo(user, todoRequestDto);
        todoRepository.save(todo);
    }

    public List<TodoResponseDto> getTodoList(User user) {
        System.out.println("user.getRole() = " + user.getRole());
        if (user.getRole().equals(USER)) {
            return todoRepository.findAllByUser(user)
                    .stream()
                    .map(TodoResponseDto::new)
                    .toList();
        } else {
            return todoRepository.findAll()
                    .stream()
                    .map(TodoResponseDto::new)
                    .toList();
        }
    }

    public TodoResponseDto getTodo(User user, Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 적이 없는 할일입니다.")
        );

        if (user.getRole().equals(USER) && (!(Objects.equals(todo.getUser().getId(), user.getId())))) {
            throw new IllegalArgumentException("해당 유저가 소유한 할일 아이디가 아닙니다.");
        }

        return new TodoResponseDto(todo);
    }

    @Transactional
    public void updateTodo(TodoRequestDto todoRequestDto, Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 적이 없는 할일입니다.")
        );

        todo.update(todoRequestDto);
    }

    public void deleteTodo(User user, Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 적이 없는 할일입니다.")
        );
        if(todo.getUser().getId().equals(user.getId()) || user.getRole().equals(ADMIN)){
            todoRepository.delete(todo);
        }
    }
}
