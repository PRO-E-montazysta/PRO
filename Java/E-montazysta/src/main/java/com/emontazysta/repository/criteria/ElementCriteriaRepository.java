package com.emontazysta.repository.criteria;


import com.emontazysta.mapper.ElementMapper;
import com.emontazysta.model.Element;
import com.emontazysta.model.dto.ElementDto;
import com.emontazysta.model.searchcriteria.ElementSearchCriteria;
import com.emontazysta.util.AuthUtils;
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
public class ElementCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final ElementMapper elementMapper;
    private final AuthUtils authUtils;

    public ElementCriteriaRepository(EntityManager entityManager, ElementMapper elementMapper, AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.elementMapper = elementMapper;
        this.authUtils = authUtils;
    }

    public List<ElementDto> findAllWithFilters(ElementSearchCriteria elementSearchCriteria ) {

        CriteriaQuery<Element> criteriaQuery = criteriaBuilder.createQuery(Element.class);
        Root<Element> elementRoot = criteriaQuery.from(Element.class);
        Predicate predicate = getPredicate(elementSearchCriteria, elementRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Element> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Element> elements = typedQuery.getResultList();

        return elements.stream().map(elementMapper::toDto).collect(Collectors.toList());
    }
    private Predicate getPredicate(ElementSearchCriteria elementSearchCriteria, Root<Element> elementRoot) {
        List<Predicate> predicates = new ArrayList<>();

        //Get elements by name
        if(Objects.nonNull(elementSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(elementRoot.get("name"), "%" + elementSearchCriteria.getName() + "%"));
        }

        //Get elements by code
        if(Objects.nonNull(elementSearchCriteria.getCode())){
            predicates.add(criteriaBuilder.like(elementRoot.get("code"), "%" + elementSearchCriteria.getCode() + "%"));
        }

        //Get elements from company
        predicates.add(criteriaBuilder.equal(elementRoot.join("elementInWarehouses").get("warehouse").get("company")
                .get("id"), authUtils.getLoggedUserCompanyId()));

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}