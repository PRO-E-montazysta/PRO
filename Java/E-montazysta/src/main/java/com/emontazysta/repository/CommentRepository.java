package com.emontazysta.repository;

import com.emontazysta.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndDeletedIsFalse(Long id);
    List<Comment> findAllByDeletedIsFalse();
}
