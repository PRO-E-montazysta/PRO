package com.emontazysta.repository.criteria;

import com.emontazysta.enums.OrderStageStatus;
import com.emontazysta.enums.Role;
import com.emontazysta.mapper.OrderStageMapper;
import com.emontazysta.model.*;
import com.emontazysta.model.dto.OrderStageDto;
import com.emontazysta.model.searchcriteria.OrdersStageSearchCriteria;
import com.emontazysta.util.AuthUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OrdersStageCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final OrderStageMapper orderStageMapper;
    private final AuthUtils authUtils;

    public OrdersStageCriteriaRepository(EntityManager entityManager, OrderStageMapper orderStageMapper, AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.orderStageMapper = orderStageMapper;
        this.authUtils = authUtils;
    }

    public List<OrderStageDto> findAllWithFilters(OrdersStageSearchCriteria ordersStageSearchCriteria) {

        CriteriaQuery<OrderStage> criteriaQuery = criteriaBuilder.createQuery(OrderStage.class);
        Root<OrderStage> ordersStageRoot = criteriaQuery.from(OrderStage.class);
        Predicate predicate = getPredicate(ordersStageSearchCriteria, ordersStageRoot);
        criteriaQuery.where(predicate);

        TypedQuery<OrderStage> typedQuery = entityManager.createQuery(criteriaQuery);
        List<OrderStage> ordersStages = typedQuery.getResultList();

        return ordersStages.stream().sorted(Comparator.comparing(OrderStage::getPlannedStartDate)).map(orderStageMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(OrdersStageSearchCriteria ordersStageSearchCriteria, Root<OrderStage> ordersStageRoot) {
        List<Predicate> predicates = new ArrayList<>();

        //Get not-deleted
        predicates.add(criteriaBuilder.equal(ordersStageRoot.get("deleted"), false));

        //Get from user compnay
        predicates.add(criteriaBuilder.equal(ordersStageRoot.get("orders").get("company").get("id"),
                authUtils.getLoggedUserCompanyId()));

        //Get for given order_Id
        if (Objects.nonNull(ordersStageSearchCriteria.getOrder_Id())) {
            predicates.add(criteriaBuilder.equal(ordersStageRoot.get("orders").get("id"), ordersStageSearchCriteria.getOrder_Id()));
        }

        //Strict displayed for warehouse-man & -manager
        if(authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MAN)
                || authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MANAGER)) {
            List<Predicate> warehousePredicates = new ArrayList<>();

            warehousePredicates.add(criteriaBuilder.equal(ordersStageRoot.get("status"), OrderStageStatus.PICK_UP));
            warehousePredicates.add(criteriaBuilder.equal(ordersStageRoot.get("status"), OrderStageStatus.RETURN));

            predicates.add(criteriaBuilder.or(warehousePredicates.toArray(new Predicate[0])));
        }

        //Strict displayed for fitter
        if(authUtils.getLoggedUser().getRoles().contains(Role.FITTER)) {
            predicates.add(criteriaBuilder.isMember(authUtils.getLoggedUser(), ordersStageRoot.get("assignedTo")));
        }

        //Strict displayed for foreman
        if(authUtils.getLoggedUser().getRoles().contains(Role.FOREMAN)) {
            predicates.add(criteriaBuilder.equal(ordersStageRoot.get("orders").get("assignedTo").get("id"), authUtils.getLoggedUser().getId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
