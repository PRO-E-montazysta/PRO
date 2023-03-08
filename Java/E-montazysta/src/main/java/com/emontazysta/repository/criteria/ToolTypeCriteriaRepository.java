package com.emontazysta.repository.criteria;

import com.emontazysta.mapper.ToolTypeMapper;
import com.emontazysta.model.ToolType;
import com.emontazysta.model.dto.ToolTypeDto;
import com.emontazysta.model.searchcriteria.ToolTypeSearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ToolTypeCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final ToolTypeMapper toolTypeMapper;

    public ToolTypeCriteriaRepository(EntityManager entityManager, ToolTypeMapper toolTypeMapper) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.toolTypeMapper = toolTypeMapper;

    }

    public List<ToolTypeDto> findAllWithFilters(ToolTypeSearchCriteria toolTypeSearchCriteria ) {

        CriteriaQuery<ToolType> criteriaQuery = criteriaBuilder.createQuery(ToolType.class);
        Root<ToolType> toolTypeRoot = criteriaQuery.from(ToolType.class);
        Predicate predicate = getPredicate(toolTypeSearchCriteria, toolTypeRoot);
        criteriaQuery.where(predicate);

        TypedQuery<ToolType> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ToolType> toolTypes = typedQuery.getResultList();

        return toolTypes.stream().map(toolTypeMapper::toDto).collect(Collectors.toList());
    }
    private Predicate getPredicate(ToolTypeSearchCriteria toolTypeSearchCriteria, Root<ToolType> toolTypeRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(toolTypeSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(toolTypeRoot.get("name"), "%" + toolTypeSearchCriteria.getName() + "%"));
        }

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
