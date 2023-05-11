package com.emontazysta.service.impl;

import com.emontazysta.enums.EventStatus;
import com.emontazysta.enums.NotificationType;
import com.emontazysta.enums.Role;
import com.emontazysta.mapper.ToolEventMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.dto.ToolEventDto;
import com.emontazysta.repository.ToolEventRepository;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.NotificationService;
import com.emontazysta.service.ToolEventService;
import com.emontazysta.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToolEventServiceImpl implements ToolEventService {

    private final ToolEventRepository repository;
    private final ToolEventMapper toolEventMapper;
    private final AuthUtils authUtils;
    private final AppUserService userService;
    private final NotificationService notificationService;

    @Override
    public List<ToolEventDto> getAll() {
        return repository.findAll().stream()
                .map(toolEventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ToolEventDto getById(Long id) {
        ToolEvent toolEvent = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        AppUser user =  authUtils.getLoggedUser();
        Boolean displayOwn = user.getRoles().contains(Role.FITTER) || user.getRoles().contains(Role.FOREMAN);
        if(displayOwn) {
            if(!toolEvent.getCreatedBy().getId().equals(user.getId()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }else {
            if(!toolEvent.getTool().getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return toolEventMapper.toDto(toolEvent);

    }

    @Override
    public ToolEventDto add(ToolEventDto toolEventDto) {
        toolEventDto.setAttachments(new ArrayList<>());

        ToolEvent toolEvent = toolEventMapper.toEntity(toolEventDto);

        //Check if tool in event is from user company
        if(!toolEvent.getTool().getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        toolEvent.setCreatedBy(authUtils.getLoggedUser());
        toolEvent.setEventDate(LocalDateTime.now());
        toolEvent.setStatus(EventStatus.CREATED);

        ToolEvent savedToolEvent = repository.save(toolEvent);

        //Wysłanie powiadomienia do specjalistów o utworzeniu zlecenia
        List<AppUser> notifiedEmployees = notificationService.createListOfEmployeesToNotificate(userService.findAllByRole(Role.MANAGER));
        notifiedEmployees.addAll(notificationService.createListOfEmployeesToNotificate(userService.findAllByRole(Role.WAREHOUSE_MANAGER)));
        notificationService.createNotification(notifiedEmployees, savedToolEvent.getId(), NotificationType.TOOL_EVENT);

        return toolEventMapper.toDto(savedToolEvent);
    }

    @Override
    public void delete(Long id) {
        ToolEvent toolEvent = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        if(!toolEvent.getTool().getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        repository.deleteById(id);
    }

    @Override
    public ToolEventDto update(Long id, ToolEventDto toolEventDto) {
        ToolEvent updatedToolEvent = toolEventMapper.toEntity(toolEventDto);
        ToolEvent toolEvent = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //Check if tool in event is from user company
        if(!updatedToolEvent.getTool().getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        AppUser user =  authUtils.getLoggedUser();
        Boolean isFitter = user.getRoles().contains(Role.FITTER);
        Boolean isWarehouseMan = user.getRoles().contains(Role.WAREHOUSE_MAN);
        Boolean isForeman = user.getRoles().contains(Role.FOREMAN);
        if(isFitter || isWarehouseMan || isForeman) {
            if(!toolEvent.getCreatedBy().getId().equals(user.getId()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }else {
            if(!toolEvent.getTool().getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);

            if(updatedToolEvent.getStatus() == EventStatus.IN_PROGRESS && toolEvent.getStatus() != EventStatus.IN_PROGRESS){
                toolEvent.setMovingDate(LocalDateTime.now());
            }

            if((updatedToolEvent.getStatus() != EventStatus.CREATED && updatedToolEvent.getStatus() != EventStatus.IN_PROGRESS)
                    && (toolEvent.getStatus() == EventStatus.CREATED || toolEvent.getStatus() == EventStatus.IN_PROGRESS)){
                toolEvent.setCompletionDate(LocalDateTime.now());
            }

            toolEvent.setStatus(updatedToolEvent.getStatus());
        }

        toolEvent.setDescription(updatedToolEvent.getDescription());
        toolEvent.setTool(updatedToolEvent.getTool());

        return toolEventMapper.toDto(repository.save(toolEvent));
    }
}
