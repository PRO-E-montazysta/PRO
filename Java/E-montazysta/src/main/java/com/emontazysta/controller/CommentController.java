package com.emontazysta.controller;

import com.emontazysta.model.Comment;
import com.emontazysta.service.impl.CommentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(API_BASE_CONSTANT + "/comments")
public class CommentController {

    private final CommentServiceImpl commentService;

    @GetMapping
    @Operation(description = "Allows to get all Comments.", security = @SecurityRequirement(name = "bearer-key"))
    public List<Comment> getAll() {
        return commentService.getAll();
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get Comment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public Comment getById(@PathVariable("id") Long id) {
        return commentService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Comment.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@RequestBody Comment comment) {
        commentService.add(comment);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete Comment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable("id") Long id) {
        commentService.delete(id);
    }
}
