package com.emontazysta.controller;

import com.emontazysta.model.Comment;
import com.emontazysta.service.impl.CommentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(API_BASE_CONSTANT + "/comments")
public class CommentController {

    private final CommentServiceImpl commentService;

    @GetMapping
    public List<Comment> getComments() {
        return commentService.getAll();
    }

    @GetMapping("{commentId}")
    public Comment getComment(@PathVariable("commentId") Long commentId) {
        return commentService.getById(commentId);
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        commentService.add(comment);
    }

    @DeleteMapping("{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }
}
