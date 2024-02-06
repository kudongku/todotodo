package com.sparta.todotodo.todo.entity;

import com.sparta.todotodo.todo.dto.CommentRequestDto;
import com.sparta.todotodo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    public Comment(CommentRequestDto commentRequestDto, User user, Todo todo) {
        this.content = commentRequestDto.getContent();
        this.user = user;
        this.todo = todo;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
