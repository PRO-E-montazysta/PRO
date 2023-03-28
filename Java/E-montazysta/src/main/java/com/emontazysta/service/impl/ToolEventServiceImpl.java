package com.emontazysta.service.impl;

import com.emontazysta.mapper.ToolEventMapper;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.dto.ToolEventDto;
import com.emontazysta.repository.ToolEventRepository;
import com.emontazysta.service.ToolEventService;
import com.emontazysta.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToolEventServiceImpl implements ToolEventService {

    private final ToolEventRepository repository;
    private final ToolEventMapper toolEventMapper;
    private final AuthUtils authUtils;

    @Override
    public List<ToolEventDto> getAll() {
        return repository.findAll().stream()
                .map(toolEventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ToolEventDto getById(Long id) {
        ToolEvent toolEvent = repository.findById(id).orElseThrow(() -> new RuntimeException("Tool event with id " + id + " not found!"));
        return toolEventMapper.toDto(toolEvent);

    }

    @Override
    public ToolEventDto add(ToolEventDto toolEventDto) {
        ToolEvent toolEvent = toolEventMapper.toEntity(toolEventDto);
        toolEvent.setCreatedBy(authUtils.getLoggedUser());
        return toolEventMapper.toDto(repository.save(toolEvent));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ToolEventDto update(Long id, ToolEventDto toolEventDto) {
        ToolEvent updatedToolEvent = toolEventMapper.toEntity(toolEventDto);
        ToolEvent toolEvent = repository.findById(id).orElseThrow(() -> new RuntimeException("Tool event with id " + id + " not found!"));
        toolEvent.setEventDate(updatedToolEvent.getEventDate());
        toolEvent.setMovingDate(updatedToolEvent.getMovingDate());
        toolEvent.setCompletionDate(updatedToolEvent.getCompletionDate());
        toolEvent.setDescription(updatedToolEvent.getDescription());
        toolEvent.setStatus(updatedToolEvent.getStatus());
        toolEvent.setCreatedBy(updatedToolEvent.getCreatedBy());
        toolEvent.setAcceptedBy(updatedToolEvent.getAcceptedBy());
        toolEvent.setTool(updatedToolEvent.getTool());
        toolEvent.setAttachments(updatedToolEvent.getAttachments());
        return toolEventMapper.toDto(repository.save(toolEvent));
    }
}
