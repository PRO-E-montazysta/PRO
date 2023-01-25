package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.dto.AttachmentDto;

public class AttachmentMapper {

    public static AttachmentDto attachmentDto (Attachment attachment) {
        return AttachmentDto.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .url(attachment.getUrl())
                .description(attachment.getDescription())
                .typeOfAttachment(attachment.getTypeOfAttachment())
                .createdAt(attachment.getCreatedAt())
                .build();
    }
}
