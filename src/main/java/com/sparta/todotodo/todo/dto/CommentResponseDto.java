package com.sparta.todotodo.todo.dto;

import com.sparta.todotodo.todo.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private String content;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
    }
}
