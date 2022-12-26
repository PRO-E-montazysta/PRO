package com.emontazysta.controller;

import com.emontazysta.model.Comment;
import com.emontazysta.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<Comment> getLocations() {
        return commentService.getComments();
    }

    @GetMapping("{commentId}")
    public Comment getLocation(@PathVariable("commentId") Long commentId) {
        return commentService.getComment(commentId);
    }

    @PostMapping
    public void addLocation(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }

    @DeleteMapping("{commentId}")
    public void deleteLocation(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }
}
