package com.emontazysta.service.impl;

import com.emontazysta.mapper.ToolEventMapper;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.dto.ToolEventDto;
import com.emontazysta.repository.ToolEventRepository;
import com.emontazysta.service.ToolEventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToolEventServiceImpl implements ToolEventService {

    private final ToolEventRepository repository;
    private final ToolEventMapper toolEventMapper;

    public List<ToolEventDto> getAll() {
        return repository.findAll().stream()
                .map(toolEventMapper::toDto)
                .collect(Collectors.toList());
    }

    public ToolEventDto getById(Long id) {
        ToolEvent toolEvent = repository.findById(id).orElseThrow(() -> new RuntimeException("Tool event with id " + id + " not found!"));
        return toolEventMapper.toDto(toolEvent);

    }

    public ToolEventDto add(ToolEventDto toolEventDto) {
        ToolEvent toolEvent = toolEventMapper.toEntity(toolEventDto);
        return toolEventMapper.toDto(repository.save(toolEvent));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
