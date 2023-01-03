package com.emontazysta.service.impl;

import com.emontazysta.model.Comment;
import com.emontazysta.repository.CommentRepository;
import com.emontazysta.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    public List<Comment> getAll() {
        return repository.findAll();
    }

    @Override
    public Comment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Comment comment) {
        repository.save(comment);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
