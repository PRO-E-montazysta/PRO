package com.emontazysta.service.impl;

import com.emontazysta.enums.OrderStageStatus;
import com.emontazysta.enums.Role;
import com.emontazysta.enums.ToolStatus;
import com.emontazysta.mapper.*;
import com.emontazysta.model.*;
import com.emontazysta.model.dto.*;
import com.emontazysta.model.searchcriteria.OrdersStageSearchCriteria;
import com.emontazysta.repository.*;
import com.emontazysta.repository.criteria.OrdersStageCriteriaRepository;
import com.emontazysta.service.ElementService;
import com.emontazysta.service.OrderStageService;
import com.emontazysta.service.ToolService;
import com.emontazysta.service.WarehouseService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderStageImpl implements OrderStageService {

    private final OrderStageRepository repository;
    private final OrderStageMapper orderStageMapper;
    private final OrdersStageCriteriaRepository ordersStageCriteriaRepository;
    private final ToolsPlannedNumberRepository toolsPlannedNumberRepository;
    private final ToolsPlannedNumberMapper toolsPlannedNumberMapper;
    private final ElementsPlannedNumberRepository elementsPlannedNumberRepository;
    private final ElementsPlannedNumberMapper elementsPlannedNumberMapper;
    private final ToolReleaseRepository toolReleaseRepository;
    private final ElementReturnReleaseRepository elementReturnReleaseRepository;
    private final ToolService toolService;
    private final ToolMapper toolMapper;
    private final ElementService elementService;
    private final ElementMapper elementMapper;
    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;
    private final AuthUtils authUtils;

    @Override
    public List<OrderStageDto> getAll() {
        return repository.findAll().stream()
                .map(orderStageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderStageDto getById(Long id) {
        OrderStage orderStage = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return orderStageMapper.toDto(orderStage);
    }

    @Override
    public OrderStageDto add(OrderStageDto orderStageDto) {
            orderStageDto.setFitters(new ArrayList<>());
            orderStageDto.setComments(new ArrayList<>());
            orderStageDto.setToolReleases(new ArrayList<>());
            orderStageDto.setElementReturnReleases(new ArrayList<>());
            orderStageDto.setAttachments(new ArrayList<>());
            orderStageDto.setNotifications(new ArrayList<>());
            orderStageDto.setDemandAdHocs(new ArrayList<>());
            orderStageDto.setListOfToolsPlannedNumber(new ArrayList<>());
            orderStageDto.setListOfElementsPlannedNumber(new ArrayList<>());
            orderStageDto.setPlannedDurationTime(ChronoUnit.HOURS.between(orderStageDto.getPlannedStartDate(),orderStageDto.getPlannedEndDate()));
            orderStageDto.setStatus(OrderStageStatus.PLANNING);

            OrderStage orderStage = orderStageMapper.toEntity(orderStageDto);
            return orderStageMapper.toDto(repository.save(orderStage));
    }


    @Override
    @Transactional
    public OrderStageDto addWithToolsAndElements(OrderStageWithToolsAndElementsDto orderStageDto) {
        orderStageDto.setStatus(OrderStageStatus.PLANNING);

        OrderStageWithToolsAndElementsDto modiffiedOrderStageDto = completeEmptyAttributes(orderStageDto);

        OrderStage orderStage = orderStageMapper.toEntity(modiffiedOrderStageDto);
        OrderStageDto savedOrderStageDto = orderStageMapper.toDto(repository.save(orderStage));


        if(!modiffiedOrderStageDto.getListOfToolsPlannedNumber().isEmpty()) {
            for (ToolsPlannedNumberDto toolsPlannedNumberDto : modiffiedOrderStageDto.getListOfToolsPlannedNumber()) {
                toolsPlannedNumberDto.setOrderStageId(savedOrderStageDto.getId());
                ToolsPlannedNumber toolsPlannedNumber = toolsPlannedNumberMapper.toEntity(toolsPlannedNumberDto);
                toolsPlannedNumberRepository.save(toolsPlannedNumber);
            }
        }

        if(!modiffiedOrderStageDto.getListOfElementsPlannedNumber().isEmpty()) {
            for (ElementsPlannedNumberDto elementsPlannedNumberDto : modiffiedOrderStageDto.getListOfElementsPlannedNumber()) {
                elementsPlannedNumberDto.setOrderStageId(savedOrderStageDto.getId());
                ElementsPlannedNumber elementsPlannedNumber = elementsPlannedNumberMapper.toEntity(elementsPlannedNumberDto);
                elementsPlannedNumberRepository.save(elementsPlannedNumber);
            }
        }

        return getById(savedOrderStageDto.getId());
    }

    private OrderStageWithToolsAndElementsDto completeEmptyAttributes(OrderStageWithToolsAndElementsDto orderStageDto) {
        orderStageDto.setFitters(orderStageDto.getFitters() == null ? new ArrayList<>() : orderStageDto.getFitters());
        orderStageDto.setComments(orderStageDto.getComments() == null ? new ArrayList<>() : orderStageDto.getComments());
        orderStageDto.setToolReleases(orderStageDto.getToolReleases() == null ? new ArrayList<>() : orderStageDto.getToolReleases());
        orderStageDto.setElementReturnReleases(orderStageDto.getElementReturnReleases() == null ? new ArrayList<>() : orderStageDto.getElementReturnReleases());
        orderStageDto.setAttachments(orderStageDto.getAttachments() == null ? new ArrayList<>() : orderStageDto.getAttachments());
        orderStageDto.setNotifications(orderStageDto.getNotifications() == null ? new ArrayList<>() : orderStageDto.getNotifications());
        orderStageDto.setDemandAdHocs(orderStageDto.getDemandAdHocs() == null ? new ArrayList<>() : orderStageDto.getDemandAdHocs());
        orderStageDto.setListOfToolsPlannedNumber(orderStageDto.getListOfToolsPlannedNumber() == null ? new ArrayList<>() : orderStageDto.getListOfToolsPlannedNumber());
        orderStageDto.setListOfElementsPlannedNumber(orderStageDto.getListOfElementsPlannedNumber() == null ? new ArrayList<>() : orderStageDto.getListOfElementsPlannedNumber());
        orderStageDto.setPlannedDurationTime(ChronoUnit.HOURS.between(orderStageDto.getPlannedStartDate(), orderStageDto.getPlannedEndDate()));

        return orderStageDto;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderStageDto update(Long id, OrderStageWithToolsAndElementsDto orderStageDto) {

        OrderStageWithToolsAndElementsDto modiffiedOrderStageDto = completeEmptyAttributes(orderStageDto);

        OrderStage updatedOrderStage = orderStageMapper.toEntity(modiffiedOrderStageDto);

        OrderStage orderStageDb = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        List<ToolsPlannedNumber> updatedToolsList = new ArrayList<>();
        List<ElementsPlannedNumber> updatedElementsList = new ArrayList<>();
        if (authUtils.getLoggedUser().getRoles().contains(Role.SPECIALIST)) {
            if(!updatedOrderStage.getListOfToolsPlannedNumber().isEmpty()) {
                orderStageDb.getListOfToolsPlannedNumber().stream()
                        .forEach(toolsPlannedNumber -> toolsPlannedNumberRepository.delete(toolsPlannedNumber));

                for (ToolsPlannedNumberDto toolsPlannedNumberDto : modiffiedOrderStageDto.getListOfToolsPlannedNumber()) {
                    toolsPlannedNumberDto.setOrderStageId(orderStageDb.getId());
                    ToolsPlannedNumber toolsPlannedNumber = toolsPlannedNumberMapper.toEntity(toolsPlannedNumberDto);
                    toolsPlannedNumberRepository.save(toolsPlannedNumber);
                    updatedToolsList.add(toolsPlannedNumber);
                }
            }

            if(!updatedOrderStage.getListOfElementsPlannedNumber().isEmpty()) {
                orderStageDb.getListOfElementsPlannedNumber().stream()
                        .forEach(elementsPlannedNumber -> elementsPlannedNumberRepository.delete(elementsPlannedNumber));

                for (ElementsPlannedNumberDto elementsPlannedNumberDto : modiffiedOrderStageDto.getListOfElementsPlannedNumber()) {
                    elementsPlannedNumberDto.setOrderStageId(orderStageDto.getId());
                    ElementsPlannedNumber elementsPlannedNumber = elementsPlannedNumberMapper.toEntity(elementsPlannedNumberDto);
                    elementsPlannedNumberRepository.save(elementsPlannedNumber);
                    updatedElementsList.add(elementsPlannedNumber);

                }
            }

        } else{
            updatedToolsList = updatedOrderStage.getListOfToolsPlannedNumber();
            updatedElementsList = updatedOrderStage.getListOfElementsPlannedNumber();
        }

        orderStageDb.setName(updatedOrderStage.getName());
        orderStageDb.setStatus(updatedOrderStage.getStatus());
        orderStageDb.setPrice(updatedOrderStage.getPrice());
        orderStageDb.setPlannedStartDate(updatedOrderStage.getPlannedStartDate());
        orderStageDb.setPlannedEndDate(updatedOrderStage.getPlannedEndDate());
        orderStageDb.setStartDate(updatedOrderStage.getStartDate());
        orderStageDb.setEndDate(updatedOrderStage.getEndDate());
        orderStageDb.setPlannedDurationTime(ChronoUnit.HOURS.between(updatedOrderStage.getPlannedStartDate(),updatedOrderStage.getPlannedEndDate()));
        orderStageDb.setPlannedFittersNumber(updatedOrderStage.getPlannedFittersNumber());
        orderStageDb.setMinimumImagesNumber(updatedOrderStage.getMinimumImagesNumber());
        orderStageDb.setAssignedTo(updatedOrderStage.getAssignedTo());
        orderStageDb.setManagedBy(updatedOrderStage.getManagedBy());
        orderStageDb.setComments(updatedOrderStage.getComments());
        orderStageDb.setAttachments(updatedOrderStage.getAttachments());
        orderStageDb.setNotifications(updatedOrderStage.getNotifications());
        orderStageDb.setDemandsAdHoc(updatedOrderStage.getDemandsAdHoc());
        orderStageDb.setListOfToolsPlannedNumber(updatedToolsList);
        orderStageDb.setListOfElementsPlannedNumber(updatedElementsList);

        return orderStageMapper.toDto(repository.save(orderStageDb));
    }

    @Override
    public List<OrderStageDto> getFilteredOrders(OrdersStageSearchCriteria ordersStageSearchCriteria, Principal principal) {
        return  ordersStageCriteriaRepository.findAllWithFilters(ordersStageSearchCriteria, principal);
    }

    @Override
    @Transactional
    public OrderStageDto releaseTools(Long id, List<ToolSimpleReturnReleaseDto> toolCodes) {
        OrderStage orderStage = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        //Sprawdzenie, czy etap jest z firmy użytkownika
        if(!orderStage.getOrders().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //Sprawdzenie czy można wydać narzędzia- Status etapu na PICK_UP i zapytanie wysłane przez WAREHOUSE_MAN lub WAREHOUSE_MANAGER
        if(orderStage.getStatus().equals(OrderStageStatus.PICK_UP) && (
                authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MAN) ||
                        authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MANAGER))) {
            //Zmienna przechowująca błędne kody
            StringBuilder errorCodes = new StringBuilder();

            for(ToolSimpleReturnReleaseDto toolSimpleReturnReleaseDto : toolCodes) {
                String toolCode = toolSimpleReturnReleaseDto.getToolCode();
                try {
                    Tool tool = toolMapper.toEntity(toolService.getByCode(toolCode));
                    //Sprawdzenie, czy narzędzie jest dostępne do wydania
                    if(tool.getStatus().equals(ToolStatus.AVAILABLE)) {
                        ToolRelease toolRelease = toolReleaseRepository.save(ToolRelease.builder()
                                .releaseTime(LocalDateTime.now())
                                .releasedBy((Warehouseman) authUtils.getLoggedUser())
                                .tool(tool)
                                .orderStage(orderStage)
                                .build());
                        orderStage.getToolReleases().add(toolRelease);
                    }else {
                        //Kody narzędzi, które nie są aktualnie dostępne
                        errorCodes.append(toolCodes + "- niedostępne ");
                    }
                }catch (EntityNotFoundException e) {
                    //Kody narzędzi, które nie istnieją, bądź są z innej firmy
                    errorCodes.append(toolCodes + "- nie znaleziono ");
                }
            }

            if(errorCodes.isEmpty()){
                return orderStageMapper.toDto(repository.save(orderStage));
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nie można wykonać operacji. Błędne kody: " + errorCodes);
            }
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public OrderStageDto returnTools(Long id, List<ToolSimpleReturnReleaseDto> toolCodes) {
        OrderStage orderStage = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        //Sprawdzenie, czy etap jest z firmy użytkownika
        if(!orderStage.getOrders().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //Sprawdzenie czy można zwrócić narzędzia- Status etapu na RETURN i zapytanie wysłane przez WAREHOUSE_MAN lub WAREHOUSE_MANAGER
        if(orderStage.getStatus().equals(OrderStageStatus.RETURN) && (
                authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MAN) ||
                        authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MANAGER))) {
            //Zmienna przechowująca błędne kody
            StringBuilder errorCodes = new StringBuilder();

            for(ToolSimpleReturnReleaseDto toolSimpleReturnReleaseDto : toolCodes) {
                String toolCode = toolSimpleReturnReleaseDto.getToolCode();
                try {
                    Tool tool = toolMapper.toEntity(toolService.getByCode(toolCode));
                    //Sprawdzenie, czy narzędzie można zwrócić
                    if(tool.getStatus().equals(ToolStatus.RELEASED)) {
                        //Znalezienie wydania dla narzędzia bez daty zwrotu
                        Optional<ToolRelease> toolReleaseOptional = orderStage.getToolReleases().stream()
                                .filter(o -> o.getTool().getCode().equals(toolCode))
                                .filter(o -> o.getReturnTime() == null)
                                .findFirst();

                        //Ustawienie daty zwrotu, jeśli znaleziono
                        if(toolReleaseOptional.isPresent()) {
                            ToolRelease toolRelease = toolReleaseOptional.get();
                            toolRelease.setReturnTime(LocalDateTime.now());
                            toolReleaseRepository.save(toolRelease);
                        }else {
                            //Kody narzędzi, których nie można zwrócić w tym etapie
                            errorCodes.append(toolCodes + "- brak możliwości ");
                        }
                    }else {
                        //Kody narzędzi, które nie są aktualnie wydane
                        errorCodes.append(toolCodes + "- niewydane ");
                    }
                }catch (EntityNotFoundException e) {
                    //Kody narzędzi, które nie istnieją, bądź są z innej firmy
                    errorCodes.append(toolCodes + "- nie znaleziono ");
                }
            }

            if(errorCodes.isEmpty()){
                return orderStageMapper.toDto(repository.save(orderStage));
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nie można wykonać operacji. Błędne kody: " + errorCodes);
            }
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public OrderStageDto releaseElements(Long id, List<ElementSimpleReturnReleaseDto> elements) {
        OrderStage orderStage = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        //Sprawdzenie, czy etap jest z firmy użytkownika
        if(!orderStage.getOrders().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
        Warehouse warehouse = warehouseMapper.toEntity(warehouseService.getById(1L));//TODO:PASS WAREHOUSE ID

        //Sprawdzenie czy można wydać elementy- Status etapu na PICK_UP i zapytanie wysłane przez WAREHOUSE_MAN lub WAREHOUSE_MANAGER
        if(orderStage.getStatus().equals(OrderStageStatus.PICK_UP) && (
                authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MAN) ||
                        authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MANAGER))) {
            //Zmienna przechowująca błędne kody
            StringBuilder errorCodes = new StringBuilder();

            for(ElementSimpleReturnReleaseDto elementSimpleReturnReleaseDto : elements) {
                String elementCode = elementSimpleReturnReleaseDto.getElementCode();

                try {
                    Element element = elementMapper.toEntity(elementService.getByCode(elementCode));
                    //Sprawdzenie, czy można wydać element
                    if(element.getInWarehouseCount(warehouse) - elementSimpleReturnReleaseDto.getQuantity() >= 0) {
                        ElementReturnRelease elementReturnRelease = elementReturnReleaseRepository.save(ElementReturnRelease.builder()
                                .releaseTime(LocalDateTime.now())
                                .releasedBy((Warehouseman) authUtils.getLoggedUser())
                                .element(element)
                                .orderStage(orderStage)
                                .build());
                        orderStage.getElementReturnReleases().add(elementReturnRelease);
                        element.changeInWarehouseCountByQuantity(warehouse, -elementSimpleReturnReleaseDto.getQuantity());
                    }else {
                        //Kody elementów, które nie są aktualnie dostępne
                        errorCodes.append(elementCode + "- niedostępne ");
                    }
                }catch (EntityNotFoundException e) {
                    //Kody elementów, które nie istnieją, bądź są z innej firmy
                    errorCodes.append(elementCode + "- nie znaleziono ");
                }
            }

            if(errorCodes.isEmpty()){
                return orderStageMapper.toDto(repository.save(orderStage));
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nie można wykonać operacji. Błędne kody: " + errorCodes);
            }
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public OrderStageDto returnElements(Long id, List<ElementSimpleReturnReleaseDto> elements) {
        OrderStage orderStage = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        //Sprawdzenie, czy etap jest z firmy użytkownika
        if(!orderStage.getOrders().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Warehouse warehouse = warehouseMapper.toEntity(warehouseService.getById(1L));//TODO:PASS WAREHOUSE ID

        //Sprawdzenie czy można zwrócić elementy- Status etapu na RETURN i zapytanie wysłane przez WAREHOUSE_MAN lub WAREHOUSE_MANAGER
        if(orderStage.getStatus().equals(OrderStageStatus.RETURN) && (
                authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MAN) ||
                        authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MANAGER))) {
            //Zmienna przechowująca błędne kody
            StringBuilder errorCodes = new StringBuilder();

            for(ElementSimpleReturnReleaseDto elementSimpleReturnReleaseDto : elements) {
                String elementCode = elementSimpleReturnReleaseDto.getElementCode();

            }

            if(errorCodes.isEmpty()){
                return orderStageMapper.toDto(repository.save(orderStage));
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nie można wykonać operacji. Błędne kody: " + errorCodes);
            }
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public OrderStageDto nextStatus(Long id) {
        OrderStage orderStage = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        //Check if OrderStage is from logged user company
        if(!orderStage.getOrders().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Set<Role> loggedUserRoles = authUtils.getLoggedUser().getRoles();

        if(orderStage.getStatus().equals(OrderStageStatus.PLANNING) && loggedUserRoles.contains(Role.SPECIALIST)) {
            orderStage.setStatus(OrderStageStatus.ADDING_FITTERS);
        }else if(orderStage.getStatus().equals(OrderStageStatus.ADDING_FITTERS) && loggedUserRoles.contains(Role.FOREMAN)) {
            //Check if fitters are assigned
            if(orderStage.getAssignedTo().size() > 0) {
                //Check if needs to PICK_UP tools
                if(orderStage.getListOfToolsPlannedNumber().size() == 0 &&
                        orderStage.getListOfElementsPlannedNumber().size() == 0){
                    orderStage.setStatus(OrderStageStatus.REALESED);
                } else {
                    orderStage.setStatus(OrderStageStatus.PICK_UP);
                }
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Przypisz montażystów do etapu!");
            }
        }else if(orderStage.getStatus().equals(OrderStageStatus.PICK_UP) && (loggedUserRoles.contains(Role.WAREHOUSE_MAN)
                || loggedUserRoles.contains(Role.WAREHOUSE_MANAGER))) {
            orderStage.setStatus(OrderStageStatus.REALESED);
        }else if(orderStage.getStatus().equals(OrderStageStatus.REALESED) && loggedUserRoles.contains(Role.FOREMAN)) {
            orderStage.setStatus(OrderStageStatus.ON_WORK);
        }else if(orderStage.getStatus().equals(OrderStageStatus.ON_WORK) && loggedUserRoles.contains(Role.FOREMAN)) {
            orderStage.setStatus(OrderStageStatus.RETURN);
        }else if(orderStage.getStatus().equals(OrderStageStatus.RETURN) && (loggedUserRoles.contains(Role.WAREHOUSE_MAN)
                || loggedUserRoles.contains(Role.WAREHOUSE_MANAGER))) {
            //Check if all tools are returned
            for(ToolRelease toolRelease : orderStage.getToolReleases()) {
                if(toolRelease.getReturnTime() == null) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Zwróć wszystkie narzędzia!");
                }
                orderStage.setStatus(OrderStageStatus.RETURNED);
            }
        }else if(orderStage.getStatus().equals(OrderStageStatus.RETURNED) && loggedUserRoles.contains(Role.FOREMAN)) {
            orderStage.setStatus(OrderStageStatus.FINISHED);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Brak możliwości zmiany!");
        }

        return orderStageMapper.toDto(repository.save(orderStage));
    }

    @Override
    public OrderStageDto previousStatus(Long id) {
        OrderStage orderStage = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        //Check if OrderStage is from logged user company
        if(!orderStage.getOrders().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Set<Role> loggedUserRoles = authUtils.getLoggedUser().getRoles();

        if(orderStage.getStatus().equals(OrderStageStatus.ADDING_FITTERS) && loggedUserRoles.contains(Role.FOREMAN)) {
            orderStage.setStatus(OrderStageStatus.PLANNING);
        }else if(orderStage.getStatus().equals(OrderStageStatus.PICK_UP) && (loggedUserRoles.contains(Role.WAREHOUSE_MAN)
                || loggedUserRoles.contains(Role.WAREHOUSE_MANAGER))) {
            orderStage.setStatus(OrderStageStatus.ADDING_FITTERS);
        }else if(orderStage.getStatus().equals(OrderStageStatus.REALESED) && loggedUserRoles.contains(Role.FOREMAN)) {
            orderStage.setStatus(OrderStageStatus.PICK_UP);
        }else if(orderStage.getStatus().equals(OrderStageStatus.ON_WORK) && loggedUserRoles.contains(Role.FOREMAN)) {
            orderStage.setStatus(OrderStageStatus.REALESED);
        }else if(orderStage.getStatus().equals(OrderStageStatus.RETURN) && (loggedUserRoles.contains(Role.WAREHOUSE_MAN)
                || loggedUserRoles.contains(Role.WAREHOUSE_MANAGER))) {
            orderStage.setStatus(OrderStageStatus.ON_WORK);
        }else if(orderStage.getStatus().equals(OrderStageStatus.RETURNED) && loggedUserRoles.contains(Role.FOREMAN)) {
            orderStage.setStatus(OrderStageStatus.RETURN);
        }else if(orderStage.getStatus().equals(OrderStageStatus.FINISHED) && loggedUserRoles.contains(Role.FOREMAN)) {
            orderStage.setStatus(OrderStageStatus.RETURNED);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Brak możliwości zmiany!");
        }

        return orderStageMapper.toDto(repository.save(orderStage));
    }
}
