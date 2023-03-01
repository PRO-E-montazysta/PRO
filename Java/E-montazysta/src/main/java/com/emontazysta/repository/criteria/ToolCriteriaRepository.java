package com.emontazysta.repository.criteria;

import com.emontazysta.mapper.ToolMapper;
import com.emontazysta.model.Tool;
import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.dto.ToolDto;
import com.emontazysta.model.searchcriteria.ToolReleaseSearchCriteria;
import com.emontazysta.model.searchcriteria.ToolSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ToolCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ToolCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<ToolDto> finadAllWithFilter( ToolSearchCriteria toolSearchCriteria, ToolReleaseSearchCriteria toolReleaseSearchCriteria){
        CriteriaQuery<Tool> criteriaQuery = criteriaBuilder.createQuery(Tool.class);
        Root<Tool> toolRoot = criteriaQuery.from(Tool.class);
        Predicate predicate = getPredicate(toolSearchCriteria, toolRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Tool> typedQuery = entityManager.createQuery(criteriaQuery);

        return new (typedQuery.getResultList().stream().map(ToolMapper::toolToDto).collect(Collectors.toList()));
    }



    private Predicate getPredicate(ToolSearchCriteria toolSearchCriteria, Root<Tool> toolRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(toolSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(toolRoot.get("name"),
                    "%" + toolSearchCriteria.getName() + "%"));
        }
        if(Objects.nonNull(toolSearchCriteria.getCode())){
            predicates.add(criteriaBuilder.like(toolRoot.get("code"),
                    "%" + toolSearchCriteria.getCode() + "%"));
        }
        if(Objects.nonNull(toolSearchCriteria.getWarehouse_Id())){
            predicates.add(criteriaBuilder.equal(toolRoot.get("warehouse").get("id"),
                    toolSearchCriteria.getWarehouse_Id()));
        }
        if(Objects.nonNull(toolSearchCriteria.getToolType_Id())){
            predicates.add(criteriaBuilder.equal(toolRoot.get("toolType").get("id"),
                    toolSearchCriteria.getToolType_Id()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
