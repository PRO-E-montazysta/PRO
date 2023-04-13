package com.emontazysta.service.impl;

import com.emontazysta.mapper.OrderStageMapper;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.dto.OrderStageDto;
import com.emontazysta.model.searchcriteria.OrdersStageSearchCriteria;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.criteria.OrdersStageCriteriaRepository;
import com.emontazysta.service.OrderStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderStageImpl implements OrderStageService {

    private final OrderStageRepository repository;
    private final OrderStageMapper orderStageMapper;
    private final OrdersStageCriteriaRepository ordersStageCriteriaRepository;

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
        orderStageDto.setPlannedDurationTime(ChronoUnit.HOURS.between(orderStageDto.getPlannedStartDate(),orderStageDto.getPlannedEndDate()));

        OrderStage orderStage = orderStageMapper.toEntity(orderStageDto);
        return orderStageMapper.toDto(repository.save(orderStage));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderStageDto update(Long id, OrderStageDto orderStageDto) {

        OrderStage updatedOrderStage = orderStageMapper.toEntity(orderStageDto);
        OrderStage orderStageDb = repository.findById(id).orElseThrow(EntityNotFoundException::new);
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
        orderStageDb.setToolReleases(updatedOrderStage.getToolReleases());
        orderStageDb.setElementReturnReleases(updatedOrderStage.getElementReturnReleases());
        orderStageDb.setAttachments(updatedOrderStage.getAttachments());
        orderStageDb.setNotifications(updatedOrderStage.getNotifications());
        orderStageDb.setTools(updatedOrderStage.getTools());
        orderStageDb.setElements(updatedOrderStage.getElements());
        orderStageDb.setDemandsAdHoc(updatedOrderStage.getDemandsAdHoc());

        return orderStageMapper.toDto(orderStageDb);
    }

    @Override
    public List<OrderStageDto> getFilteredOrders(OrdersStageSearchCriteria ordersStageSearchCriteria, Principal principal) {
        return  ordersStageCriteriaRepository.findAllWithFilters(ordersStageSearchCriteria, principal);
    }
}
