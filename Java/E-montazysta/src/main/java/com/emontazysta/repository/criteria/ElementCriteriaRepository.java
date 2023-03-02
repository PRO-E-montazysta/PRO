package com.emontazysta.repository.criteria;

import com.emontazysta.enums.TypeOfUnit;
import com.emontazysta.mapper.ElementMapper;
import com.emontazysta.model.Element;
import com.emontazysta.model.dto.ElementDto;
import com.emontazysta.model.searchcriteria.ElementSearchCriteria;
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

    public ElementCriteriaRepository(EntityManager entityManager, ElementMapper elementMapper) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.elementMapper = elementMapper;
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

        if(Objects.nonNull(elementSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(elementRoot.get("name"), "%" + elementSearchCriteria.getName() + "%"));
        }

        if(Objects.nonNull(elementSearchCriteria.getCode())){
            predicates.add(criteriaBuilder.like(elementRoot.get("code"), "%" + elementSearchCriteria.getCode() + "%"));
        }

        if(Objects.nonNull(elementSearchCriteria.getTypeOfUnit())){
            List<Predicate> typeOfUnitPredicates = new ArrayList<>();
            for (String type: elementSearchCriteria.getTypeOfUnit()) {
                typeOfUnitPredicates.add(criteriaBuilder.equal(elementRoot.get("typeOfUnit"), TypeOfUnit.valueOf(type)));
            }
            return  criteriaBuilder.or(typeOfUnitPredicates.toArray(new Predicate[0]));
        }


        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}