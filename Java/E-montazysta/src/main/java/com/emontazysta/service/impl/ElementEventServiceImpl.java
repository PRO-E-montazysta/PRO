package com.emontazysta.service.impl;

import com.emontazysta.enums.EventStatus;
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

<<<<<<< HEAD
import javax.persistence.EntityNotFoundException;
=======
import java.time.LocalDateTime;
import java.util.ArrayList;
>>>>>>> master
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
    public ElementEventDto add(ElementEventDto elementEventDto) {
        elementEventDto.setAttachments(new ArrayList<>());

        ElementEvent elementEvent = elementEventMapper.toEntity(elementEventDto);
        elementEvent.setCreatedBy(authUtils.getLoggedUser());
        elementEvent.setEventDate(LocalDateTime.now());
        elementEvent.setStatus(EventStatus.CREATED);

        return elementEventMapper.toDto(repository.save(elementEvent));
    }

    @Override
    public void delete(Long id) {
<<<<<<< HEAD
=======
        ElementEvent elementEvent = repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!elementEvent.getElement().getElementInWarehouses().stream().findFirst().get().getWarehouse()
                .getCompany().getId().equals(authUtils.getLoggedUserCompanyId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
>>>>>>> master

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

            if(updatedElementEvent.getStatus() == EventStatus.IN_PROGRESS && elementEvent.getStatus() != EventStatus.IN_PROGRESS){
                    elementEvent.setMovingDate(LocalDateTime.now());
            }

            if((updatedElementEvent.getStatus() != EventStatus.CREATED && updatedElementEvent.getStatus() != EventStatus.IN_PROGRESS)
                    && (elementEvent.getStatus() == EventStatus.CREATED || elementEvent.getStatus() == EventStatus.IN_PROGRESS)){
                elementEvent.setCompletionDate(LocalDateTime.now());
            }

            elementEvent.setStatus(updatedElementEvent.getStatus());
        }

        elementEvent.setDescription(updatedElementEvent.getDescription());
        elementEvent.setQuantity(updatedElementEvent.getQuantity());
        elementEvent.setElement(updatedElementEvent.getElement());
        elementEvent.setAttachments(updatedElementEvent.getAttachments());

        return elementEventMapper.toDto(repository.save(elementEvent));
    }
}
