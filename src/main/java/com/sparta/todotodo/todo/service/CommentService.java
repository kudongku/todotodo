package com.sparta.todotodo.todo.service;

import com.sparta.todotodo.todo.dto.CommentRequestDto;
import com.sparta.todotodo.todo.dto.CommentResponseDto;
import com.sparta.todotodo.todo.entity.Comment;
import com.sparta.todotodo.todo.entity.Todo;
import com.sparta.todotodo.todo.repository.CommentRepository;
import com.sparta.todotodo.todo.repository.TodoRepository;
import com.sparta.todotodo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    public void createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 적이 없는 할일입니다.")
        );
        commentRepository.save(new Comment(commentRequestDto, user, todo));
    }

    public List<CommentResponseDto> getComments(Long id, User user) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 적이 없는 할일입니다.")
        );
        List<Comment> todoList = commentRepository.findAllByTodo(todo);
        return todoList.stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    @Transactional
    public void editComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("등록된 적이 없는 댓글입니다.")
        );
        comment.update(commentRequestDto);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("등록된 적이 없는 댓글입니다.")
        );
        commentRepository.delete(comment);
    }
}
