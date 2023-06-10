package com.emontazysta.repository.criteria;

import com.emontazysta.enums.OrderStatus;
import com.emontazysta.enums.Role;
import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.mapper.OrdersCompanyManagerMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.OrdersCompanyManagerDto;
import com.emontazysta.model.searchcriteria.OrdersSearchCriteria;
import com.emontazysta.service.AppUserService;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OrdersCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final OrdersCompanyManagerMapper ordersMapper;
    private final AppUserService userService;

    public OrdersCriteriaRepository(EntityManager entityManager, OrdersCompanyManagerMapper ordersMapper, AppUserService userService) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.ordersMapper = ordersMapper;
        this.userService = userService;
    }

    public List<OrdersCompanyManagerDto> findAllWithFilters(OrdersSearchCriteria ordersSearchCriteria, Principal principal) {

        CriteriaQuery<Orders> criteriaQuery = criteriaBuilder.createQuery(Orders.class);
        Root<Orders> ordersRoot = criteriaQuery.from(Orders.class);
        Predicate predicate = getPredicate(ordersSearchCriteria, ordersRoot, principal);
        criteriaQuery.where(predicate);

        TypedQuery<Orders> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Orders> orders = typedQuery.getResultList();

        return orders.stream().map(ordersMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(OrdersSearchCriteria ordersSearchCriteria, Root<Orders> ordersRoot, Principal principal) {
        List<Predicate> predicates = new ArrayList<>();

        //Get not-deleted
        predicates.add(criteriaBuilder.equal(ordersRoot.get("deleted"), false));

        if (Objects.nonNull(ordersSearchCriteria.getName())) {
            predicates.add(criteriaBuilder.like(ordersRoot.get("name"), "%" + ordersSearchCriteria.getName() + "%"));
        }

        if (Objects.nonNull(ordersSearchCriteria.getPlannedStartMin())
                && Objects.nonNull(ordersSearchCriteria.getPlannedStartMax())) {

            if (LocalDateTime.parse(ordersSearchCriteria.getPlannedStartMin())
                    .isBefore(LocalDateTime.parse(ordersSearchCriteria.getPlannedStartMax())) ||
                    LocalDateTime.parse(ordersSearchCriteria.getPlannedStartMin())
                            .isEqual(LocalDateTime.parse(ordersSearchCriteria.getPlannedStartMax()))) {

                predicates.add(criteriaBuilder.greaterThanOrEqualTo(ordersRoot.get("plannedStart"),
                        LocalDateTime.parse(ordersSearchCriteria.getPlannedStartMin())));

                predicates.add(criteriaBuilder.lessThanOrEqualTo(ordersRoot.get("plannedStart"),
                        LocalDateTime.parse(ordersSearchCriteria.getPlannedStartMax())));
            } else {
                throw new IllegalArgumentException("The start time must precede the end time");
            }
        } else if (Objects.nonNull(ordersSearchCriteria.getPlannedStartMin())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(ordersRoot.get("plannedStart"),
                    LocalDateTime.parse(ordersSearchCriteria.getPlannedStartMin())));
        } else if (Objects.nonNull(ordersSearchCriteria.getPlannedStartMax())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(ordersRoot.get("plannedStart"),
                    LocalDateTime.parse(ordersSearchCriteria.getPlannedStartMax())));
        }

        if (Objects.nonNull(ordersSearchCriteria.getStatus())) {
            List<Predicate> statusPredicates = new ArrayList<>();
            for (String type : ordersSearchCriteria.getStatus()) {
                statusPredicates.add(criteriaBuilder.equal(ordersRoot.get("status"), OrderStatus.valueOf(type)));
            }
            predicates.add(criteriaBuilder.or(statusPredicates.toArray(new Predicate[0])));
        }

        if (Objects.nonNull(ordersSearchCriteria.getTypeOfPriority())) {
            List<Predicate> typeOfPriorityPredicates = new ArrayList<>();
            for (String type : ordersSearchCriteria.getTypeOfPriority()) {
                typeOfPriorityPredicates.add(criteriaBuilder.equal(ordersRoot.get("typeOfPriority"),
                TypeOfPriority.valueOf(type)));
            }
            predicates.add(criteriaBuilder.or(typeOfPriorityPredicates.toArray(new Predicate[0])));
        }

        AppUser user =  userService.findByUsername(principal.getName());
        Boolean isCloudAdmin = user.getRoles().contains(Role.CLOUD_ADMIN);

        if(user.getRoles().contains(Role.SPECIALIST)) {
            predicates.add(criteriaBuilder.equal(ordersRoot.get("status"), OrderStatus.PLANNING));
        }

        if(user.getRoles().contains(Role.FOREMAN)) {
            predicates.add(criteriaBuilder.equal(ordersRoot.get("assignedTo"), user));
        }

        if(!isCloudAdmin) {
            Optional<Employment>  takingEmployment = user.getEmployments().stream()
                    .filter(employment -> employment.getDateOfDismiss() == null)
                    .findFirst();

            if (takingEmployment.isPresent()) {
                Long companyId = takingEmployment.get().getCompany().getId();
                predicates.add(criteriaBuilder.equal(ordersRoot.get("company").get("id"), companyId));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else {
                throw new IllegalArgumentException("The logged user is not a registered employee");
            }
        } else {
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }
}