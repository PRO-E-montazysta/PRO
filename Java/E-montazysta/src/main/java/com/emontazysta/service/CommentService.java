package com.emontazysta.service;

import com.emontazysta.model.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getAll();
    CommentDto getById(Long id);
    CommentDto add(CommentDto comment);
    void delete(Long id);
    CommentDto update(Long id, CommentDto comment);
}
