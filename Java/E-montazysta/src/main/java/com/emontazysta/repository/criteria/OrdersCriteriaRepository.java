package com.emontazysta.repository.criteria;
import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import com.emontazysta.mapper.OrdersMapper;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.OrdersDto;

import com.emontazysta.model.searchcriteria.OrdersSearchCriteria;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

@Repository
public class OrdersCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final OrdersMapper ordersMapper;
    public OrdersCriteriaRepository(EntityManager entityManager, OrdersMapper ordersMapper) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.ordersMapper = ordersMapper;
    }

    public List<OrdersDto> findAllWithFilters(OrdersSearchCriteria ordersSearchCriteria ) {

        CriteriaQuery<Orders> criteriaQuery = criteriaBuilder.createQuery(Orders.class);
        Root<Orders> ordersRoot = criteriaQuery.from(Orders.class);
        Predicate predicate = getPredicate(ordersSearchCriteria, ordersRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Orders> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Orders> orders = typedQuery.getResultList();

        return orders.stream().map(ordersMapper::toDto).collect(Collectors.toList());
    }
    private Predicate getPredicate(OrdersSearchCriteria ordersSearchCriteria, Root<Orders> ordersRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(ordersSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(ordersRoot.get("name"), "%" + ordersSearchCriteria.getName() + "%"));
        }

        if(Objects.nonNull(ordersSearchCriteria.getPlannedStartMin())){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(ordersRoot.get("plannedStart"),
                    LocalDateTime.parse(ordersSearchCriteria.getPlannedStartMin())));;
        }

        if(Objects.nonNull(ordersSearchCriteria.getPlannedStartMax())){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(ordersRoot.get("plannedStart"),
                    LocalDateTime.parse(ordersSearchCriteria.getPlannedStartMax())));;
        }

        if(Objects.nonNull(ordersSearchCriteria.getTypeOfStatus())){
            List<Predicate> typeOfStatusPredicates = new ArrayList<>();
            for (String type: ordersSearchCriteria.getTypeOfStatus()) {
                typeOfStatusPredicates.add(criteriaBuilder.equal(ordersRoot.get("typeOfStatus"),TypeOfStatus.valueOf(type)));
            }
            return  criteriaBuilder.or(typeOfStatusPredicates.toArray(new Predicate[0]));
        }

        if(Objects.nonNull(ordersSearchCriteria.getTypeOfPriority())){
            List<Predicate> typeOfPriorityPredicates = new ArrayList<>();
            for (String type: ordersSearchCriteria.getTypeOfPriority()) {
                typeOfPriorityPredicates.add(criteriaBuilder.equal(ordersRoot.get("typeOfPriority"), TypeOfPriority.valueOf(type)));
            }
            return  criteriaBuilder.or(typeOfPriorityPredicates.toArray(new Predicate[0]));
        }

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}