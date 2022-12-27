package com.emontazysta.service;

import com.emontazysta.data.CommentRequest;
import com.emontazysta.model.Comment;
import com.emontazysta.repositoriy.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment with id " + id + " not found!"));
    }

    public void addComment(CommentRequest newComment) {
        Comment comment = new Comment();
        comment.setContent(newComment.getContent());
        comment.setCreatedAt(new Date());

        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
