package com.emontazysta.service.impl;

import com.emontazysta.enums.NotificationType;
import com.emontazysta.enums.OrderStageStatus;
import com.emontazysta.enums.OrderStatus;
import com.emontazysta.enums.Role;
import com.emontazysta.mapper.OrdersMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Employment;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.OrdersCompanyManagerDto;
import com.emontazysta.model.dto.OrdersDto;
import com.emontazysta.model.searchcriteria.OrdersSearchCriteria;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.criteria.OrdersCriteriaRepository;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.NotificationService;
import com.emontazysta.service.OrdersService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrderRepository repository;
    private final OrdersMapper ordersMapper;
    private final OrdersCriteriaRepository ordersCriteriaRepository;
    private final AppUserService userService;
    private final NotificationService notificationService;
    private final AuthUtils authUtils;
    private final OrderStageRepository orderStageRepository;

    @Override
    public List<OrdersDto> getAll() {
        return repository.findAll().stream()
                .map(ordersMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrdersDto getById(Long id) {
        Orders order = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return ordersMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrdersDto add(OrdersDto ordersDto) {
        Orders order = ordersMapper.toEntity(ordersDto);
        order.setCreatedAt(LocalDateTime.now());
        order.setEditedAt(null);
        order.setStatus(OrderStatus.CREATED);
        OrdersDto savedOrderDto = ordersMapper.toDto(repository.save(order));

        List<AppUser> notifiedEmployees = notificationService.createListOfEmployeesToNotificate(userService.findAllByRole(Role.SPECIALIST));
        notificationService.createNotification(notifiedEmployees, savedOrderDto.getId(), NotificationType.ORDER_CREATED);

        return savedOrderDto;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public OrdersDto update(Long id, OrdersDto ordersDto) {

        //TODO:
        if (authUtils.getLoggedUser().getRoles().contains(Role.MANAGER) && ordersDto.getForemanId()!=null){
            //zlecenie zaakceptowane przez managera
            ordersDto.setStatus(OrderStatus.ACCEPTED);

            List<AppUser> notifiedEmployees = new ArrayList<>();
            notifiedEmployees.add(userService.getById(ordersDto.getForemanId()));
            notificationService.createNotification(notifiedEmployees, ordersDto.getId(), NotificationType.FOREMAN_ASSIGNMENT);

        } else if (authUtils.getLoggedUser().getRoles().contains(Role.SALES_REPRESENTATIVE)) {
            //zlecenie edytowane przez handlowca
            ordersDto.setStatus(OrderStatus.TO_ACCEPT);
        }

        Orders updatedOrder = ordersMapper.toEntity(ordersDto);
        Orders order = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        order.setName(updatedOrder.getName());
        order.setStatus(updatedOrder.getStatus());
        order.setTypeOfPriority(updatedOrder.getTypeOfPriority());
        order.setPlannedStart(updatedOrder.getPlannedStart());
        order.setPlannedEnd(updatedOrder.getPlannedEnd());
        order.setEditedAt(LocalDateTime.now());
        order.setCompany(updatedOrder.getCompany());
        order.setManagedBy(updatedOrder.getManagedBy());
        order.setAssignedTo(updatedOrder.getAssignedTo());
        order.setSpecialist(updatedOrder.getSpecialist());
        order.setSalesRepresentative(updatedOrder.getSalesRepresentative());
        order.setLocation(updatedOrder.getLocation());
        order.setClient(updatedOrder.getClient());
        order.setOrderStages(updatedOrder.getOrderStages());
        order.setAttachments(updatedOrder.getAttachments());

        return ordersMapper.toDto(repository.save(order));
    }

    @Override
    public OrdersDto nextStatus(Long id) {
        Orders order = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        //Check if Order is from logged user company
        if(!order.getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Set<Role> loggedUserRoles = authUtils.getLoggedUser().getRoles();

        if(order.getStatus().equals(OrderStatus.CREATED) && loggedUserRoles.contains(Role.SALES_REPRESENTATIVE)) {
            order.setStatus(OrderStatus.PLANNING);
        }else if(order.getStatus().equals(OrderStatus.PLANNING) && loggedUserRoles.contains(Role.SPECIALIST)) {
            if(order.getOrderStages().size() > 0) {
                order.setStatus(OrderStatus.TO_ACCEPT);
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Podziel zlecenie na etapy!");
            }
        }else if(order.getStatus().equals(OrderStatus.TO_ACCEPT) && loggedUserRoles.contains(Role.MANAGER)) {
            if(order.getAssignedTo() != null) {
                order.setStatus(OrderStatus.ACCEPTED);

                for(OrderStage orderStage : order.getOrderStages()) {
                    orderStage.setStatus(OrderStageStatus.ADDING_FITTERS);
                    orderStageRepository.save(orderStage);
                }
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Podziel zlecenie na etapy!");
            }
        }else if(order.getStatus().equals(OrderStatus.ACCEPTED) && loggedUserRoles.contains(Role.FOREMAN)) {
            order.setStatus(OrderStatus.IN_PROGRESS);
        }else if(order.getStatus().equals(OrderStatus.IN_PROGRESS) && loggedUserRoles.contains(Role.FOREMAN)) {
            for(OrderStage orderStage : order.getOrderStages()) {
                if(!orderStage.getStatus().equals(OrderStageStatus.FINISHED)) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Niektóre etapy jeszcze trwają!");
                }
            }
            order.setStatus(OrderStatus.FINISHED);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Brak możliwości zmiany!");
        }

        return ordersMapper.toDto(repository.save(order));
    }

    @Override
    public List<OrdersCompanyManagerDto> getFilteredOrders(OrdersSearchCriteria ordersSearchCriteria, Principal principal) {
        return ordersCriteriaRepository.findAllWithFilters(ordersSearchCriteria, principal);
    }


    public OrdersDto findPrincipalCompanyId(OrdersDto ordersDto, Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        Boolean isCloudAdmin = user.getRoles().contains(Role.CLOUD_ADMIN);

        if (!isCloudAdmin) {
            Optional<Employment> takingEmployment = user.getEmployments().stream()
                    .filter(employment -> employment.getDateOfDismiss() == null)
                    .findFirst();

            if (takingEmployment.isPresent()) {
                ordersDto.setCompanyId(takingEmployment.get().getCompany().getId());
            }
        } else {
            throw new IllegalArgumentException("The logged user is not a registered employee");
        }
        return ordersDto;
    }
}

