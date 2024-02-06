package com.sparta.todotodo.todo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TodoState {
    TODO_STATE,
    DOING_STATE,
    DONE_STATE
}
