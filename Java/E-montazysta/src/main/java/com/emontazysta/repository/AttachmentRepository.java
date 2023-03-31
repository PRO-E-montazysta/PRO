package com.emontazysta.repository;

import com.emontazysta.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findAllByDeletedIsFalse();
    Optional<Attachment> findByIdAndDeletedIsFalse(Long id);
}
