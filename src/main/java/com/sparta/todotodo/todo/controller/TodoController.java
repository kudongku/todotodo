package com.sparta.todotodo.todo.controller;


import com.sparta.todotodo.todo.dto.TodoRequestDto;
import com.sparta.todotodo.todo.dto.TodoResponseDto;
import com.sparta.todotodo.todo.service.TodoService;
import com.sparta.todotodo.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("")
    public void createTodo(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody TodoRequestDto todoRequestDto) {
        todoService.createTodo(userDetails.getUser(), todoRequestDto);
    }

    @GetMapping("")
    public List<TodoResponseDto> getTodoList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.getTodoList(userDetails.getUser());
    }

    @GetMapping("/{id}")
    public TodoResponseDto getTodo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return todoService.getTodo(userDetails.getUser(), id);
    }

    @PutMapping("/{id}")
    public void updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto todoRequestDto){
        todoService.updateTodo(todoRequestDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id){
        todoService.deleteTodo(userDetails.getUser(), id);
    }

    @GetMapping("/doing/{id}")
    public void doingTodo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        todoService.doingTodo(userDetails.getUser(), id);
    }

    @GetMapping("/done/{id}")
    public void doneTodo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        todoService.doneTodo(userDetails.getUser(), id);
    }
}