package com.emontazysta.mapper;

import com.emontazysta.enums.ToolStatus;
import com.emontazysta.model.Attachment;
import com.emontazysta.model.Tool;
import com.emontazysta.model.ToolType;
import com.emontazysta.model.ToolsPlannedNumber;
import com.emontazysta.model.dto.ToolTypeDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.CompanyRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.ToolRepository;
import com.emontazysta.repository.ToolsPlannedNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ToolTypeMapper {

    private final AttachmentRepository attachmentRepository;
    private final ToolsPlannedNumberRepository toolsPlannedNumberRepository;
    private final ToolRepository toolRepository;
    private final CompanyRepository companyRepository;

    public ToolTypeDto toDto(ToolType toolType){

        int availableCount = (int) toolType.getTools().stream()
                .filter(tool -> !tool.isDeleted())
                .filter(tool -> ToolStatus.AVAILABLE.equals(tool.getStatus()))
                .count();

        return ToolTypeDto.builder()
                .id(toolType.getId())
                .name(toolType.getName())
                .inServiceCount(toolType.getTools().size())
                .criticalNumber(toolType.getCriticalNumber())
                .availableCount(availableCount)
                .attachments(toolType.getAttachments().stream()
                        .filter(attachment -> !attachment.isDeleted())
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .ListOfToolsPlannedNumber(toolType.getListOfToolsPlannedNumber().stream()
                        .filter(orderStage -> !orderStage.isDeleted())
                        .map(ToolsPlannedNumber::getId)
                        .collect(Collectors.toList()))
                .tools(toolType.getTools().stream()
                        .filter(tool -> !tool.isDeleted())
                        .map(Tool::getId)
                        .collect(Collectors.toList()))
                .companyId(toolType.getCompany().getId())
                .build();
    }

    public ToolType toEntity(ToolTypeDto toolTypeDto) {

        List<Attachment> attachmentList = new ArrayList<>();
        toolTypeDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolsPlannedNumber> toolsPlannedNumberList = new ArrayList<>();
        toolTypeDto.getListOfToolsPlannedNumber().forEach(toolsPlanedNumberId -> toolsPlannedNumberList.add(toolsPlannedNumberRepository.findById(toolsPlanedNumberId).orElseThrow(EntityNotFoundException::new)));

        List<Tool> toolList = new ArrayList<>();
        toolTypeDto.getTools().forEach(toolId -> toolList.add(toolRepository.findById(toolId).orElseThrow(EntityNotFoundException::new)));

        return ToolType.builder()
                .id(toolTypeDto.getId())
                .name(toolTypeDto.getName())
                .criticalNumber(toolTypeDto.getCriticalNumber())
                .attachments(attachmentList)
                .listOfToolsPlannedNumber(toolsPlannedNumberList)
                .tools(toolList)
                .company(toolTypeDto.getCompanyId() == null ? null : companyRepository.findById(toolTypeDto.getCompanyId()).orElseThrow(EntityNotFoundException::new))
                .build();
    }
}
