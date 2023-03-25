package com.emontazysta.repository.criteria;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.OrderStageMapper;
import com.emontazysta.model.*;
import com.emontazysta.model.dto.OrderStageDto;
import com.emontazysta.model.dto.OrdersDto;
import com.emontazysta.model.searchcriteria.OrdersStageSearchCriteria;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.OrdersService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrdersStageCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final OrderStageMapper orderStageMapper;
    private final AppUserService userService;
    private final OrdersService ordersService;

    public OrdersStageCriteriaRepository(EntityManager entityManager, OrderStageMapper orderStageMapper, AppUserService userService, OrdersService ordersService) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.orderStageMapper = orderStageMapper;
        this.userService = userService;
        this.ordersService = ordersService;
    }

    public List<OrderStageDto> findAllWithFilters(OrdersStageSearchCriteria ordersStageSearchCriteria, Principal principal) {

        CriteriaQuery<OrderStage> criteriaQuery = criteriaBuilder.createQuery(OrderStage.class);
        Root<OrderStage> ordersStageRoot = criteriaQuery.from(OrderStage.class);
        Predicate predicate = getPredicate(ordersStageSearchCriteria, ordersStageRoot, principal);
        criteriaQuery.where(predicate);

        TypedQuery<OrderStage> typedQuery = entityManager.createQuery(criteriaQuery);
        List<OrderStage> ordersStages = typedQuery.getResultList();

        return ordersStages.stream().map(orderStageMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(OrdersStageSearchCriteria ordersStageSearchCriteria, Root<OrderStage> ordersStageRoot, Principal principal) {
        List<Predicate> predicates = new ArrayList<>();
        List<Long> orderStagesIds = new ArrayList<>();
        Long orderCompanyId = null;

        if (Objects.nonNull(ordersStageSearchCriteria.getOrder_Id())) {
            List<Predicate> ordersStageIdspredicates = new ArrayList<>();
            OrdersDto order = ordersService.getById(Long.valueOf(ordersStageSearchCriteria.getOrder_Id()));
            if (order != null) {
                orderCompanyId = order.getCompanyId();
                orderStagesIds = order.getOrderStages();
            }

            AppUser user = userService.findByUsername(principal.getName());
            Boolean isCloudAdmin = user.getRoles().contains(Role.CLOUD_ADMIN);

            if (!isCloudAdmin) {
                Optional<Employment> takingEmployment = user.getEmployments().stream()
                        .filter(employment -> employment.getDateOfDismiss() == null)
                        .findFirst();

                if (takingEmployment.isPresent() && orderCompanyId != null) {
                    Long companyId = takingEmployment.get().getCompany().getId();
                    if (companyId == orderCompanyId) {

                        for (Long orderStageId : orderStagesIds) {
                            ordersStageIdspredicates.add(criteriaBuilder.equal(ordersStageRoot.get("id"), orderStageId));
                        }
                        predicates.add(criteriaBuilder.or(ordersStageIdspredicates.toArray(new Predicate[0])));
                    } else {
                        throw new IllegalArgumentException("The logged user is not authorized to view this information");
                    }
                } else {
                    throw new IllegalArgumentException("The logged user is not authorized to view this information");
                }
            } else {
                throw new IllegalArgumentException("The logged user is CLOUD_ADMIN and is not authorized to view this information");
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
