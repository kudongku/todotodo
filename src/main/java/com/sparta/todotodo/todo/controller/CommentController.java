package com.sparta.todotodo.todo.controller;

import com.sparta.todotodo.todo.dto.CommentRequestDto;
import com.sparta.todotodo.todo.dto.CommentResponseDto;
import com.sparta.todotodo.todo.service.CommentService;
import com.sparta.todotodo.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}")
    public void createComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto
    ){
        commentService.createComment(id, commentRequestDto, userDetails.getUser());
    }

    @GetMapping("/{id}")
    public List<CommentResponseDto> getComments( @PathVariable Long id){
        return commentService.getComments(id);
    }

    @PutMapping("/{commentId}")
    public void editComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        commentService.editComment(userDetails.getUser(), commentId, commentRequestDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId){
        commentService.deleteComment(userDetails.getUser(), commentId);
    }
}
