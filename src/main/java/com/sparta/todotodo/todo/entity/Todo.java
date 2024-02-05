package com.sparta.todotodo.todo.entity;

import com.sparta.todotodo.todo.dto.TodoRequestDto;
import com.sparta.todotodo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.sparta.todotodo.todo.entity.TodoState.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="todos")
public class Todo extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column
    @Enumerated(value = EnumType.STRING)
    private TodoState todoState;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Todo(User user, TodoRequestDto todoRequestDto) {
        this.title = todoRequestDto.getTitle();
        this.content = todoRequestDto.getContent();
        this.user = user;
        this.todoState = TODO_STATE;
    }

    public void update(TodoRequestDto todoRequestDto) {
        this.title = todoRequestDto.getTitle();
        this.content = todoRequestDto.getContent();
    }

    public void doing() {
        this.todoState = DOING_STATE;
    }

    public void done() {
        this.todoState = DONE_STATE;
    }
}
