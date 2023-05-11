package com.emontazysta.repository.criteria;

import com.emontazysta.mapper.DemandAdHocMapper;
import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.dto.filterDto.DemandAdHocFilterDto;
import com.emontazysta.model.searchcriteria.DemandAdHocSearchCriteria;
import com.emontazysta.util.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class DemandAdHocCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final DemandAdHocMapper demandAdHocMapper;
    private final AuthUtils authUtils;

    public DemandAdHocCriteriaRepository(EntityManager entityManager, DemandAdHocMapper demandAdHocMapper,
                                         AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.demandAdHocMapper = demandAdHocMapper;
        this.authUtils = authUtils;
    }

    public List<DemandAdHocFilterDto> findAllWithFilters(DemandAdHocSearchCriteria demandAdHocSearchCriteria) {

        CriteriaQuery<DemandAdHoc> criteriaQuery = criteriaBuilder.createQuery(DemandAdHoc.class);
        Root<DemandAdHoc> root = criteriaQuery.from(DemandAdHoc.class);
        Predicate predicate = getPredicate(demandAdHocSearchCriteria, root);
        criteriaQuery.where(predicate);

        TypedQuery<DemandAdHoc> typedQuery = entityManager.createQuery(criteriaQuery);
        List<DemandAdHoc> orders = typedQuery.getResultList();

        return orders.stream().map(demandAdHocMapper::toFilerDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(DemandAdHocSearchCriteria demandAdHocSearchCriteria, Root<DemandAdHoc> root) {
        List<Predicate> predicates = new ArrayList<>();

        //Get DemandsAdHoc between dateFrom and dateTo
        //Check if dateFrom isn't after dateTo
        if (Objects.nonNull(demandAdHocSearchCriteria.getDateFrom())
                && Objects.nonNull(demandAdHocSearchCriteria.getDateTo())) {
            if (LocalDateTime.parse(demandAdHocSearchCriteria.getDateFrom())
                    .isAfter(LocalDateTime.parse(demandAdHocSearchCriteria.getDateTo()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        //Check dateFrom
        if (Objects.nonNull(demandAdHocSearchCriteria.getDateFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"),
                    LocalDateTime.parse(demandAdHocSearchCriteria.getDateFrom())));
        }
        //Check dateTo
        if (Objects.nonNull(demandAdHocSearchCriteria.getDateTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"),
                    LocalDateTime.parse(demandAdHocSearchCriteria.getDateTo())));
        }

        //Get DemandsAdHoc by createdByName
        if (Objects.nonNull(demandAdHocSearchCriteria.getCreatedByName())) {
            Expression<String> exp1 = criteriaBuilder.concat(root.get("createdBy").get("firstName"), " ");
            exp1 = criteriaBuilder.concat(exp1, root.get("createdBy").get("lastName"));

            Expression<String> exp2 = criteriaBuilder.concat(root.get("createdBy").get("lastName"), " ");
            exp2 = criteriaBuilder.concat(exp2, root.get("createdBy").get("firstName"));

            predicates.add(criteriaBuilder.or(criteriaBuilder.like(exp1, "%" + demandAdHocSearchCriteria.getCreatedByName() + "%"),
                    criteriaBuilder.like(exp2, "%" + demandAdHocSearchCriteria.getCreatedByName() + "%")));
        }

        //Get DemandsAdHoc by orderStageName
        if (Objects.nonNull(demandAdHocSearchCriteria.getOrderStageName())) {
            predicates.add(criteriaBuilder.like(root.get("orderStage").get("name"),
                    "%" + demandAdHocSearchCriteria.getOrderStageName() + "%"));
        }

        //Get DemandsAdHoc from company
        predicates.add(criteriaBuilder.equal(root.get("orderStage").get("orders").get("company").get("id"),
                authUtils.getLoggedUserCompanyId()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
