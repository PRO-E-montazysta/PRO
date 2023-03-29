package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.ElementEventMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.dto.ElementEventDto;
import com.emontazysta.repository.ElementEventRepository;
import com.emontazysta.service.ElementEventService;
import com.emontazysta.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ElementEventServiceImpl implements ElementEventService {

    private final ElementEventRepository repository;
    private final ElementEventMapper elementEventMapper;
    private final AuthUtils authUtils;

    @Override
    public List<ElementEventDto> getAll() {
        return repository.findAll().stream()
                .map(elementEventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ElementEventDto getById(Long id){
        ElementEvent elementEvent = repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        AppUser user =  authUtils.getLoggedUser();
        Boolean isFitter = user.getRoles().contains(Role.FITTER);
        if(isFitter) {
            if(!elementEvent.getCreatedBy().getId().equals(user.getId()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }else {
            if(!elementEvent.getElement().getElementInWarehouses().stream().findFirst().get().getWarehouse()
                    .getCompany().getId().equals(authUtils.getLoggedUserCompanyId()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return elementEventMapper.toDto(elementEvent);
    }

    @Override
    public ElementEventDto add(ElementEventDto event) {
        ElementEvent elementEvent = elementEventMapper.toEntity(event);
        elementEvent.setCreatedBy(authUtils.getLoggedUser());
        elementEvent.setEventDate(LocalDateTime.now());
        return elementEventMapper.toDto(repository.save(elementEvent));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ElementEventDto update(Long id, ElementEventDto event) {
        ElementEvent updatedElementEvent = elementEventMapper.toEntity(event);
        ElementEvent elementEvent = repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        AppUser user =  authUtils.getLoggedUser();
        Boolean isFitter = user.getRoles().contains(Role.FITTER);
        Boolean isWarehouseMan = user.getRoles().contains(Role.WAREHOUSE_MAN);
        if(isFitter || isWarehouseMan) {
            if(!elementEvent.getCreatedBy().getId().equals(user.getId()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }else {
            if(!elementEvent.getElement().getElementInWarehouses().stream().findFirst().get().getWarehouse()
                    .getCompany().getId().equals(authUtils.getLoggedUserCompanyId()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        elementEvent.setEventDate(updatedElementEvent.getEventDate());
        elementEvent.setMovingDate(updatedElementEvent.getMovingDate());
        elementEvent.setCompletionDate(updatedElementEvent.getCompletionDate());
        elementEvent.setDescription(updatedElementEvent.getDescription());
        elementEvent.setStatus(updatedElementEvent.getStatus());
        elementEvent.setQuantity(updatedElementEvent.getQuantity());
        elementEvent.setAcceptedBy(updatedElementEvent.getAcceptedBy());
        elementEvent.setCreatedBy(updatedElementEvent.getCreatedBy());
        elementEvent.setElement(updatedElementEvent.getElement());
        elementEvent.setAttachments(elementEvent.getAttachments());
        return elementEventMapper.toDto(repository.save(elementEvent));
    }
}
