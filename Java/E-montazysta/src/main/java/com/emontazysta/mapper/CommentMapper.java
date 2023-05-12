package com.emontazysta.mapper;


import com.emontazysta.model.Attachment;
import com.emontazysta.model.Comment;
import com.emontazysta.model.dto.CommentDto;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.OrderStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final AppUserRepository appUserRepository;
    private final OrderStageRepository orderStageRepository;
    private final AttachmentRepository attachmentRepository;

    public CommentDto toDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .messageCreatorId(comment.getMessageCreator() == null ? null : comment.getMessageCreator().getId())
                .orderStageId(comment.getOrderStage() == null ? null : comment.getOrderStage().getId())
                .attachments(comment.getAttachments().stream()
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .deleted(comment.isDeleted())
                .build();
    }

    public Comment toEntity(CommentDto commentDto) {

        List<Attachment> attachmentList = new ArrayList<>();
        commentDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        return Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .createdAt(commentDto.getCreatedAt())
                .messageCreator(commentDto.getMessageCreatorId() == null ? null : appUserRepository.findById(commentDto.getMessageCreatorId()).orElseThrow(EntityNotFoundException::new))
                .orderStage(commentDto.getOrderStageId() == null ? null : orderStageRepository.findById(commentDto.getOrderStageId()).orElseThrow(EntityNotFoundException::new))
                .attachments(attachmentList)
                .build();
    }
}
