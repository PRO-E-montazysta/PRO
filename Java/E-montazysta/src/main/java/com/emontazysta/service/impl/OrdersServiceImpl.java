package com.emontazysta.service.impl;

import com.emontazysta.enums.OrderStatus;
import com.emontazysta.enums.Role;
import com.emontazysta.mapper.OrdersMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.OrdersCompanyManagerDto;
import com.emontazysta.model.dto.OrdersDto;
import com.emontazysta.model.searchcriteria.OrdersSearchCriteria;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.criteria.OrdersCriteriaRepository;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public OrdersDto add(OrdersDto ordersDto) {
        ordersDto.setCreatedAt(LocalDateTime.now());
        ordersDto.setEditedAt(null);
        ordersDto.setStatus(OrderStatus.CREATED);
        ordersDto.setOrderStages(new ArrayList<>());
        ordersDto.setAttachments(new ArrayList<>());

        Orders order = ordersMapper.toEntity(ordersDto);
        return ordersMapper.toDto(repository.save(order));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public OrdersDto update(Long id, OrdersDto ordersDto) {

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
    public List<OrdersCompanyManagerDto> getFilteredOrders(OrdersSearchCriteria ordersSearchCriteria, Principal principal){
        return ordersCriteriaRepository.findAllWithFilters(ordersSearchCriteria, principal);
    }


    public OrdersDto findPrincipalCompanyId(OrdersDto ordersDto, Principal principal) {
        AppUser user =  userService.findByUsername(principal.getName());
        Boolean isCloudAdmin = user.getRoles().contains(Role.CLOUD_ADMIN);

        if(!isCloudAdmin) {
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
