package com.emontazysta.service.impl;

import com.emontazysta.enums.NotificationType;
import com.emontazysta.enums.OrderStatus;
import com.emontazysta.enums.Role;
import com.emontazysta.mapper.OrdersMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Orders;
import com.emontazysta.model.SalesRepresentative;
import com.emontazysta.model.dto.OrdersCompanyManagerDto;
import com.emontazysta.model.dto.OrdersDto;
import com.emontazysta.model.searchcriteria.OrdersSearchCriteria;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.SalesRepresentativeRepository;
import com.emontazysta.repository.criteria.OrdersCriteriaRepository;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.NotificationService;
import com.emontazysta.service.OrdersService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final SalesRepresentativeRepository salesRepresentativeRepository;

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
        order.setEditedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        OrdersDto savedOrderDto = ordersMapper.toDto(repository.save(order));

        List<AppUser> notifiedEmployees = createListOfEmployeesToNotificate(userService.findAllByRole(Role.SPECIALIST));
        notificationService.createNotification(savedOrderDto.getId(), NotificationType.ORDER_CREATED.getMessage(), authUtils.getLoggedUser().getId(), notifiedEmployees);

        return ordersMapper.toDto(repository.findById(savedOrderDto.getId()).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public OrdersDto update(Long id, OrdersDto ordersDto) {

        if (authUtils.getLoggedUser().getRoles().contains(Role.MANAGER) && ordersDto.getForemanId()!=null){
            //zlecenie zaakceptowane przez managera
            ordersDto.setStatus(OrderStatus.ACCEPTED);

            List<AppUser> notifiedEmployees = new ArrayList<>();
            notifiedEmployees.add(userService.getById(ordersDto.getForemanId()));
            notificationService.createNotification(ordersDto.getId(), NotificationType.FOREMAN_ASSIGNMENT.getMessage(),authUtils.getLoggedUser().getId(),notifiedEmployees);

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

    private List<AppUser> createListOfEmployeesToNotificate(List<AppUser> allByRole) {
        Long companyId = authUtils.getLoggedUserCompanyId();
        List<AppUser> filteredUsers = new ArrayList<>();

        for (AppUser user : allByRole) {
            Optional<Employment> takingEmployment = user.getEmployments().stream()
                    .filter(employment -> employment.getDateOfDismiss() == null)
                    .findFirst();
            if (takingEmployment.isPresent()) {
                Long employeeCompanyId = takingEmployment.get().getCompany().getId();
                if (employeeCompanyId==companyId) {
                    filteredUsers.add(user);
                }
            }
        }
        return filteredUsers;
    }

}

