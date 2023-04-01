package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.dto.ToolEventDto;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.ManagerRepository;
import com.emontazysta.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ToolEventMapper {

    private final AppUserRepository appUserRepository;
    private final ManagerRepository managerRepository;
    private final ToolRepository toolRepository;
    private final AttachmentRepository attachmentRepository;

    public ToolEventDto toDto(ToolEvent event){
        return ToolEventDto.builder()
                .id(event.getId())
                .eventDate(event.getEventDate())
                .movingDate(event.getEventDate())
                .completionDate(event.getCompletionDate())
                .description(event.getDescription())
                .status(event.getStatus())
                .createdById(event.getCreatedBy() == null ? null : event.getCreatedBy().getId())
                .acceptedById(event.getAcceptedBy() == null ? null : event.getAcceptedBy().getId())
                .toolId(event.getTool() == null ? null : event.getTool().getId())
                .attachments(event.getAttachments().stream().map(Attachment::getId).collect(Collectors.toList()))
                .build();
    }

    public ToolEvent toEntity(ToolEventDto toolEventDto) {

        List<Attachment> attachmentList = new ArrayList<>();
        toolEventDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.getReferenceById(attachmentId)));

        return ToolEvent.builder()
                .id(toolEventDto.getId())
                .eventDate(toolEventDto.getEventDate())
                .movingDate(toolEventDto.getMovingDate())
                .completionDate(toolEventDto.getCompletionDate())
                .description(toolEventDto.getDescription())
                .status(toolEventDto.getStatus())
                .createdBy(toolEventDto.getCreatedById() == null ? null : appUserRepository.getReferenceById(toolEventDto.getCreatedById()))
                .acceptedBy(toolEventDto.getAcceptedById() == null ? null : managerRepository.getReferenceById(toolEventDto.getAcceptedById()))
                .tool(toolEventDto.getToolId() == null ? null : toolRepository.getReferenceById(toolEventDto.getToolId()))
                .attachments(attachmentList)
                .build();
    }
}
