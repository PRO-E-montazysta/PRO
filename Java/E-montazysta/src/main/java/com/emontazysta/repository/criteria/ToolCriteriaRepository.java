package com.emontazysta.repository.criteria;

import com.emontazysta.mapper.filterMapper.ToolFilterMapper;
import com.emontazysta.model.Tool;
import com.emontazysta.model.dto.filterDto.ToolFilterDto;
import com.emontazysta.model.searchcriteria.ToolSearchCriteria;
import com.emontazysta.util.AuthUtils;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ToolCriteriaRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final ToolFilterMapper toolFilterMapper;
    private final AuthUtils authUtils;

    public ToolCriteriaRepository(EntityManager entityManager, ToolFilterMapper toolFilterMapper, AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.toolFilterMapper = toolFilterMapper;
        this.authUtils = authUtils;
    }

    public List<ToolFilterDto> finadAllWithFilter(ToolSearchCriteria toolSearchCriteria){
        CriteriaQuery<Tool> criteriaQuery = criteriaBuilder.createQuery(Tool.class);
        Root<Tool> toolRoot = criteriaQuery.from(Tool.class);
        Predicate predicate = getPredicate(toolSearchCriteria, toolRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Tool> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tool> toolList = typedQuery.getResultList();

        return toolList.stream().map(toolFilterMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(ToolSearchCriteria toolSearchCriteria, Root<Tool> toolRoot) {
        List<Predicate> predicates = new ArrayList<>();

        //Get tools by name
        if(Objects.nonNull(toolSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(toolRoot.get("name"),
                    "%" + toolSearchCriteria.getName() + "%"));
        }

        //Get tools by code
        if(Objects.nonNull(toolSearchCriteria.getCode())){
            predicates.add(criteriaBuilder.like(toolRoot.get("code"),
                    "%" + toolSearchCriteria.getCode() + "%"));
        }

        //Get tools by warehouseId
        if(Objects.nonNull(toolSearchCriteria.getWarehouse_Id())){
            List<Predicate> warehouseIdPredicates = new ArrayList<>();

            for (String warehouses: toolSearchCriteria.getWarehouse_Id()) {
                warehouseIdPredicates.add(criteriaBuilder.equal(toolRoot.get("warehouse").get("id"),
                        Long.valueOf(warehouses)));
            }
            predicates.add(criteriaBuilder.or(warehouseIdPredicates.toArray(new Predicate[0])));
        }

        //Get tools by toolTypeId
        if(Objects.nonNull(toolSearchCriteria.getToolType_Id())){
            List<Predicate> toolTypePredicates = new ArrayList<>();
            for (String toolID: toolSearchCriteria.getToolType_Id()) {
                toolTypePredicates.add(criteriaBuilder.equal(toolRoot.get("toolType").get("id"),
                        Long.valueOf(toolID)));
            }
            predicates.add(criteriaBuilder.or(toolTypePredicates.toArray(new Predicate[0])));
        }

        //Get tools from user company
        predicates.add(criteriaBuilder.equal(toolRoot.get("warehouse").get("company").get("id"),
                authUtils.getLoggedUserCompanyId()));

        //Get not-deleted
        predicates.add(criteriaBuilder.equal(toolRoot.get("deleted"), false));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
