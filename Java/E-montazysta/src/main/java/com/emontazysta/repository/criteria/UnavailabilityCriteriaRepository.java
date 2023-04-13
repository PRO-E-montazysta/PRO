package com.emontazysta.repository.criteria;

import com.emontazysta.enums.TypeOfUnavailability;
import com.emontazysta.mapper.UnavailabilityMapper;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.filterDto.UnavailabilityFilterDto;
import com.emontazysta.model.searchcriteria.UnavailabilitySearchCriteria;
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
public class UnavailabilityCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final UnavailabilityMapper unavailabilityMapper;
    private final AuthUtils authUtils;

    public UnavailabilityCriteriaRepository(EntityManager entityManager, UnavailabilityMapper unavailabilityMapper,
                                            AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.unavailabilityMapper = unavailabilityMapper;
        this.authUtils = authUtils;
    }

    public List<UnavailabilityFilterDto> findAllWithFilters(UnavailabilitySearchCriteria unavailabilitySearchCriteria) {

        CriteriaQuery<Unavailability> criteriaQuery = criteriaBuilder.createQuery(Unavailability.class);
        Root<Unavailability> unavailabilityRoot = criteriaQuery.from(Unavailability.class);
        Predicate predicate = getPredicate(unavailabilitySearchCriteria, unavailabilityRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Unavailability> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Unavailability> unavailabilities = typedQuery.getResultList();

        return unavailabilities.stream().map(unavailabilityMapper::toFilterDto).collect(Collectors.toList());
    }
    private Predicate getPredicate(UnavailabilitySearchCriteria unavailabilitySearchCriteria, Root<Unavailability> unavailabilityRoot) {
        List<Predicate> predicates = new ArrayList<>();

        //Get unavailabilities by typeOfUnavailability
        if (Objects.nonNull(unavailabilitySearchCriteria.getTypeOfUnavailability())) {
            List<Predicate> typeOfUnavailabilityPredicates = new ArrayList<>();
            for (String type : unavailabilitySearchCriteria.getTypeOfUnavailability()) {
                typeOfUnavailabilityPredicates.add(criteriaBuilder.equal(unavailabilityRoot.get("typeOfUnavailability"), TypeOfUnavailability.valueOf(type)));
            }
            predicates.add(criteriaBuilder.or(typeOfUnavailabilityPredicates.toArray(new Predicate[0])));
        }
        //Remove TypeOfUnavailability.BUSY from filtering - this unavailability is added by system while worker is on job
        predicates.add(criteriaBuilder.notEqual(unavailabilityRoot.get("typeOfUnavailability"), TypeOfUnavailability.valueOf("BUSY")));

        //Get unavailabilities between unavailableFrom and unavailableTo
        //Check if unavailableFrom isn't after unavailableTo
        if (Objects.nonNull(unavailabilitySearchCriteria.getUnavailableFrom())
                && Objects.nonNull(unavailabilitySearchCriteria.getUnavailableTo())) {
            if (LocalDateTime.parse(unavailabilitySearchCriteria.getUnavailableFrom()+"T00:00")
                    .isAfter(LocalDateTime.parse(unavailabilitySearchCriteria.getUnavailableTo()+"T23:59"))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        //Check unavailableFrom
        if (Objects.nonNull(unavailabilitySearchCriteria.getUnavailableFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(unavailabilityRoot.get("unavailableFrom"),
                    LocalDateTime.parse(unavailabilitySearchCriteria.getUnavailableFrom()+"T00:00")));
        }
        //Check unavailableTo
        if (Objects.nonNull(unavailabilitySearchCriteria.getUnavailableTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(unavailabilityRoot.get("unavailableTo"),
                    LocalDateTime.parse(unavailabilitySearchCriteria.getUnavailableTo()+"T23:59")));
        }

        //Get unavailabilities by employee names (exp1-"firstName lastName" | exp2-"lastName firstName")
        if(Objects.nonNull(unavailabilitySearchCriteria.getAssignedTo())){
            Expression<String> exp1 = criteriaBuilder.concat(unavailabilityRoot.get("assignedTo").get("firstName"), " ");
            exp1 = criteriaBuilder.concat(exp1, unavailabilityRoot.get("assignedTo").get("lastName"));

            Expression<String> exp2 = criteriaBuilder.concat(unavailabilityRoot.get("assignedTo").get("lastName"), " ");
            exp2 = criteriaBuilder.concat(exp2, unavailabilityRoot.get("assignedTo").get("firstName"));

            predicates.add(criteriaBuilder.or(criteriaBuilder.like(exp1, "%" + unavailabilitySearchCriteria.getAssignedTo() + "%"),
                    criteriaBuilder.like(exp2, "%" + unavailabilitySearchCriteria.getAssignedTo() + "%")));
        }

        //Get unavailabilities from company
        predicates.add(criteriaBuilder.equal(unavailabilityRoot.join("assignedBy").join("employments").get("company").get("id"),
                authUtils.getLoggedUserCompanyId()));

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
