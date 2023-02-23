package com.emontazysta.repository.criteria;
import com.emontazysta.enums.TypeOfStatus;
import com.emontazysta.mapper.OrdersMapper;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.OrdersDto;
import com.emontazysta.model.page.OrdersPage;
import com.emontazysta.model.searchcriteria.OrdersSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class OrdersCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    public OrdersCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<OrdersDto> findAllWithFilters( OrdersPage ordersPage, OrdersSearchCriteria ordersSearchCriteria ) {

        CriteriaQuery<Orders> criteriaQuery = criteriaBuilder.createQuery(Orders.class);
        Root<Orders> ordersRoot = criteriaQuery.from(Orders.class);
        Predicate predicate = getPredicate(ordersSearchCriteria, ordersRoot);
        criteriaQuery.where(predicate);
        setOrder(ordersPage, criteriaQuery, ordersRoot);

        TypedQuery<Orders> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(ordersPage.getPageNumber() * ordersPage.getPageSize());
        typedQuery.setMaxResults(ordersPage.getPageSize());

        Pageable pageable = getPageable(ordersPage);

        long ordersCount = getOrdersCount(predicate);

        return new PageImpl<>(typedQuery.getResultList().stream().map(OrdersMapper::ordersToDto).collect(Collectors.toList()), pageable, ordersCount);
    }

    private long getOrdersCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Orders> countRoot = countQuery.from(Orders.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return  entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(OrdersPage ordersPage) {
        Sort sort = Sort.by(ordersPage.getSortDirection(), ordersPage.getSortBy());
        return PageRequest.of(ordersPage.getPageNumber(), ordersPage.getPageSize(),sort);
    }

    private void setOrder(OrdersPage ordersPage, CriteriaQuery<Orders> criteriaQuery, Root<Orders> ordersRoot) {
        if(ordersPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(ordersRoot.get(ordersPage.getSortBy())));
        }
    }

    private Predicate getPredicate(OrdersSearchCriteria ordersSearchCriteria, Root<Orders> ordersRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(ordersSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(ordersRoot.get("name"), "%" + ordersSearchCriteria.getName() + "%"));
        }

        if(Objects.nonNull(ordersSearchCriteria.getPlannedStart())){
            predicates.add(criteriaBuilder.equal(ordersRoot.get("plannedStart"),(Date)(ordersSearchCriteria.getPlannedStart())));
        }

        if(Objects.nonNull(ordersSearchCriteria.getTypeOfStatus())){
            List<Predicate> typeStatusPredicates = new ArrayList<>();
            for (TypeOfStatus type: ordersSearchCriteria.getTypeOfStatus()) {
                typeStatusPredicates.add(criteriaBuilder.equal(ordersRoot.get("typeOfStatus"),type));
            }
            return  criteriaBuilder.or(typeStatusPredicates.toArray(new Predicate[0]));
        }

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}