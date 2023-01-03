package com.emontazysta.service;

import com.emontazysta.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAll();
    Comment getById(Long id);
    void add(Comment comment);
    void delete(Long id);
}
