package com.emontazysta.repository.criteria;

import com.emontazysta.mapper.ElementReturnReleaseMapper;
import com.emontazysta.model.ElementReturnRelease;
import com.emontazysta.model.dto.ElementReturnReleaseDto;
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
public class ElementReleaseCriteriaRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final ElementReturnReleaseMapper elementReturnReleaseMapper;
    private final AuthUtils authUtils;

    public ElementReleaseCriteriaRepository(EntityManager entityManager, ElementReturnReleaseMapper elementReturnReleaseMapper, AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.elementReturnReleaseMapper = elementReturnReleaseMapper;
        this.authUtils = authUtils;
    }

    public List<ElementReturnReleaseDto> findAllFromCompany(){
        CriteriaQuery<ElementReturnRelease> criteriaQuery = criteriaBuilder.createQuery(ElementReturnRelease.class);
        Root<ElementReturnRelease> elementReturnReleaseRoot = criteriaQuery.from(ElementReturnRelease.class);
        Predicate predicate = getPredicate(elementReturnReleaseRoot);
        criteriaQuery.where(predicate);

        TypedQuery<ElementReturnRelease> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ElementReturnRelease> elementReturnReleaseList = typedQuery.getResultList();

        return elementReturnReleaseList.stream().map(elementReturnReleaseMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(Root<ElementReturnRelease> elementReturnReleaseRoot) {
        List<Predicate> predicates = new ArrayList<>();

        //Get elements releases from user company
        predicates.add(criteriaBuilder.equal(elementReturnReleaseRoot.join("element").join("elementInWarehouses").get("warehouse").get("company")
                .get("id"), authUtils.getLoggedUserCompanyId()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
