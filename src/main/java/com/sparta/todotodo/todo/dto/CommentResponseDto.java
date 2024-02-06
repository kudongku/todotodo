package com.sparta.todotodo.todo.dto;

import com.sparta.todotodo.todo.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long commentId;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
    }
}
