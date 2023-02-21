package com.emontazysta.repository.criteria;

import com.emontazysta.mapper.ToolMapper;
import com.emontazysta.model.Tool;
import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.ToolType;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.ToolDto;
import com.emontazysta.model.page.ToolPage;
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

    public Page<ToolDto> finadAllWithFilter(ToolPage toolPage, ToolSearchCriteria toolSearchCriteria, ToolReleaseSearchCriteria toolReleaseSearchCriteria){
        CriteriaQuery<Tool> criteriaQuery = criteriaBuilder.createQuery(Tool.class);
        CriteriaQuery<ToolRelease> criteriaQueryToolRelease = criteriaBuilder.createQuery(ToolRelease.class);
        Root<ToolRelease> toolReleaseRoot = criteriaQueryToolRelease.from(ToolRelease.class);
        Root<Tool> toolRoot = criteriaQuery.from(Tool.class);
        toolRoot.join("toolType");
        toolRoot.join("warehouse");
        toolRoot.join("toolReleases");


        Predicate predicate = getPredicate(toolSearchCriteria, toolRoot, toolReleaseRoot, toolReleaseSearchCriteria);
        criteriaQuery.where(predicate);
        setOrder(toolPage, criteriaQuery,toolRoot);

        TypedQuery<Tool> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(toolPage.getPageNumber() * toolPage.getPageSize());
        typedQuery.setMaxResults(toolPage.getPageSize());

        Pageable pageable = getPageable(toolPage);

        long toolsCount = getToolsCount(predicate);
        List<Tool> tools = typedQuery.getResultList();

        return new PageImpl<>(typedQuery.getResultList().stream().map(ToolMapper::toolToDto).collect(Collectors.toList()), pageable, toolsCount);
    }



    private Predicate getPredicate(ToolSearchCriteria toolSearchCriteria, Root<Tool> toolRoot, Root<ToolRelease> toolReleaseRoot, ToolReleaseSearchCriteria toolReleaseSearchCriteria) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(toolSearchCriteria.getId())){
            predicates.add(criteriaBuilder.equal(toolRoot.get("id"),toolSearchCriteria.getId()));
        }

        if(Objects.nonNull(toolSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(toolRoot.get("name"),
                    "%" + toolSearchCriteria.getName() + "%"));
        }
        if(Objects.nonNull(toolSearchCriteria.getCreatedAt())){
            predicates.add(criteriaBuilder.like(toolRoot.get("createdAt"),
                    "%" + toolSearchCriteria.getCreatedAt() + "%"));
        }
        if(Objects.nonNull(toolSearchCriteria.getCode())){
            predicates.add(criteriaBuilder.like(toolRoot.get("code"),
                    "%" + toolSearchCriteria.getCode() + "%"));
        }
        if(Objects.nonNull(toolSearchCriteria.getToolReleases_Id())){
            predicates.add(criteriaBuilder.equal(toolReleaseRoot.get("id"),toolSearchCriteria.getToolReleases_Id()));
        }
        if(Objects.nonNull(toolSearchCriteria.getWarehouse_Id())){
            predicates.add(criteriaBuilder.equal(toolRoot.get("warehouse").get("id"),
                    toolSearchCriteria.getWarehouse_Id()));
        }
        if(Objects.nonNull(toolSearchCriteria.getToolEvent_Id())){
            predicates.add(criteriaBuilder.equal(toolRoot.get("toolEvent").get("id"),
                    toolSearchCriteria.getToolEvent_Id()));
        }
        if(Objects.nonNull(toolSearchCriteria.getToolEvent_Id())){
            predicates.add(criteriaBuilder.like(toolRoot.get("toolEvent"),
                    "%" + toolSearchCriteria.getToolEvent_Id() + "%"));
        }
        if(Objects.nonNull(toolSearchCriteria.getToolType_Id())){
            predicates.add(criteriaBuilder.equal(toolRoot.get("toolType").get("id"),
                    toolSearchCriteria.getToolType_Id()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(ToolPage toolPage, CriteriaQuery<Tool> criteriaQuery, Root<Tool> toolRoot) {
        if(toolPage.getSortDirection().equals((Sort.Direction.ASC))){
            criteriaQuery.orderBy(criteriaBuilder.asc(toolRoot.get(toolPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(toolRoot.get(toolPage.getSortBy())));
        }
    }

    private Pageable getPageable(ToolPage toolPage) {
        Sort sort = Sort.by(toolPage.getSortDirection(), toolPage.getSortBy());
        return PageRequest.of(toolPage.getPageNumber(), toolPage.getPageSize(), sort);
    }


    private long getToolsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Tool> countRoot = countQuery.from(Tool.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
     }
}
