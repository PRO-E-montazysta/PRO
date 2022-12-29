package com.emontazysta.controller;

import com.emontazysta.data.CommentRequest;
import com.emontazysta.model.Comment;
import com.emontazysta.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(API_BASE_CONSTANT + "/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<Comment> getComments() {
        return commentService.getComments();
    }

    @GetMapping("{commentId}")
    public Comment getComment(@PathVariable("commentId") Long commentId) {
        return commentService.getComment(commentId);
    }

    @PostMapping
    public void addComment(@RequestBody CommentRequest comment) {
        commentService.addComment(comment);
    }

    @DeleteMapping("{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }
}
