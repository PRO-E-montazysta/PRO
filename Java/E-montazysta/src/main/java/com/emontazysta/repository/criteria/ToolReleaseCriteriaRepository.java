package com.emontazysta.repository.criteria;

import com.emontazysta.mapper.ToolReleaseMapper;
import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.dto.ToolReleaseDto;
import com.emontazysta.util.AuthUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ToolReleaseCriteriaRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final ToolReleaseMapper toolReleaseMapper;
    private final AuthUtils authUtils;

    public ToolReleaseCriteriaRepository(EntityManager entityManager, ToolReleaseMapper toolReleaseMapper, AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.toolReleaseMapper = toolReleaseMapper;
        this.authUtils = authUtils;
    }

    public List<ToolReleaseDto> findAllFromCompany(){
        CriteriaQuery<ToolRelease> criteriaQuery = criteriaBuilder.createQuery(ToolRelease.class);
        Root<ToolRelease> toolReleaseRoot = criteriaQuery.from(ToolRelease.class);
        Predicate predicate = getPredicate(toolReleaseRoot);
        criteriaQuery.where(predicate);

        TypedQuery<ToolRelease> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ToolRelease> toolReleaseList = typedQuery.getResultList();

        return toolReleaseList.stream().map(toolReleaseMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(Root<ToolRelease> toolReleaseRoot) {
        List<Predicate> predicates = new ArrayList<>();

        //Get tool releases from user company
        predicates.add(criteriaBuilder.equal(toolReleaseRoot.get("tool").get("warehouse").get("company").get("id"),
                authUtils.getLoggedUserCompanyId()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
