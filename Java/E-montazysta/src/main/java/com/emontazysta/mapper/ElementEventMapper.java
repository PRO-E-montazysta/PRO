package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.dto.ElementEventDto;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ElementEventMapper {

    private final ManagerRepository managerRepository;
    private final AppUserRepository appUserRepository;
    private final ElementRepository elementRepository;
    private final AttachmentRepository attachmentRepository;

    public ElementEventDto toDto(ElementEvent event){
        return ElementEventDto.builder()
                .id(event.getId())
                .eventDate(event.getEventDate())
                .movingDate(event.getMovingDate())
                .completionDate(event.getCompletionDate())
                .description(event.getDescription())
                .status(event.getStatus())
                .quantity(event.getQuantity())
                .acceptedById(event.getAcceptedBy() == null ? null : event.getAcceptedBy().getId())
                .createdById(event.getCreatedBy() == null ? null : event.getCreatedBy().getId())
                .elementId(event.getElement() == null ? null : event.getElement().getId())
                .attachments(event.getAttachments().stream().map(Attachment::getId).collect(Collectors.toList()))
                .build();
    }

    public ElementEvent toEntity(ElementEventDto elementEventDto) {

        List<Attachment> attachmentList = new ArrayList<>();
        elementEventDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.getReferenceById(attachmentId)));

        return ElementEvent.builder()
                .id(elementEventDto.getId())
                .eventDate(elementEventDto.getEventDate())
                .movingDate(elementEventDto.getMovingDate())
                .completionDate(elementEventDto.getCompletionDate())
                .description(elementEventDto.getDescription())
                .status(elementEventDto.getStatus())
                .quantity(elementEventDto.getQuantity())
                .acceptedBy(elementEventDto.getAcceptedById() == null ? null : managerRepository.getReferenceById(elementEventDto.getAcceptedById()))
                .createdBy(elementEventDto.getCreatedById() == null ? null : appUserRepository.getReferenceById(elementEventDto.getCreatedById()))
                .element(elementEventDto.getElementId() == null ? null : elementRepository.getReferenceById(elementEventDto.getElementId()))
                .attachments(attachmentList)
                .build();
    }
}
