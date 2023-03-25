package com.emontazysta.service.impl;

import com.emontazysta.mapper.CommentMapper;
import com.emontazysta.model.Comment;
import com.emontazysta.model.dto.CommentDto;
import com.emontazysta.repository.CommentRepository;
import com.emontazysta.service.CommentService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper commentMapper;
    private final AuthUtils authUtils;

    @Override
    public List<CommentDto> getAll() {
        return repository.findAll().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(Long id) {

        Comment comment = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDto add(CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setMessageCreator(authUtils.getLoggedUser());
        return commentMapper.toDto(repository.save(comment));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public CommentDto update(Long id, CommentDto commentDto) {
        Comment updatedComment = commentMapper.toEntity(commentDto);
        Comment comment = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        comment.setContent(updatedComment.getContent());
        comment.setMessageCreator(updatedComment.getMessageCreator());
        comment.setOrderStage(updatedComment.getOrderStage());
        comment.setAttachments(updatedComment.getAttachments());
        return commentMapper.toDto(repository.save(comment));
    }
}
