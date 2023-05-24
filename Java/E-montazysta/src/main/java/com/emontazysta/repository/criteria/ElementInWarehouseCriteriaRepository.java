package com.emontazysta.repository.criteria;

import com.emontazysta.mapper.ElementInWarehouseMapper;
import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.dto.filterDto.ElementInWarehouseFilterDto;
import com.emontazysta.model.searchcriteria.ElementInWarehouseSearchCriteria;
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
public class ElementInWarehouseCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final ElementInWarehouseMapper elementInWarehouseMapper;
    private final AuthUtils authUtils;

    public ElementInWarehouseCriteriaRepository(EntityManager entityManager, ElementInWarehouseMapper elementInWarehouseMapper, AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.elementInWarehouseMapper = elementInWarehouseMapper;
        this.authUtils = authUtils;
    }

    public List<ElementInWarehouseFilterDto> findElementInWarehouseCounts(ElementInWarehouseSearchCriteria searchCriteria) {

        CriteriaQuery<ElementInWarehouse> criteriaQuery = criteriaBuilder.createQuery(ElementInWarehouse.class);
        Root<ElementInWarehouse> elementRoot = criteriaQuery.from(ElementInWarehouse.class);
        Predicate predicate = getPredicate(searchCriteria, elementRoot);
        criteriaQuery.where(predicate).distinct(true);

        TypedQuery<ElementInWarehouse> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ElementInWarehouse> elements = typedQuery.getResultList();

        return elements.stream().map(elementInWarehouseMapper::toFilterDto).collect(Collectors.toList());
    }
    private Predicate getPredicate(ElementInWarehouseSearchCriteria searchCriteria, Root<ElementInWarehouse> root) {
        List<Predicate> predicates = new ArrayList<>();

        //Get elements by minCount
        if(Objects.nonNull(searchCriteria.getMinCount())){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("inWarehouseCount"), Long.valueOf(searchCriteria.getMinCount())));
        }

        //Get elements by warehouse
        if(Objects.nonNull(searchCriteria.getWarehouseId())){
            List<Predicate> warehouseIdPredicates = new ArrayList<>();

            for (String warehouseId: searchCriteria.getWarehouseId()) {
                warehouseIdPredicates.add(criteriaBuilder.equal(root.get("warehouse").get("id"),
                        Long.valueOf(warehouseId)));
            }
            predicates.add(criteriaBuilder.or(warehouseIdPredicates.toArray(new Predicate[0])));
        }

        //Get by element
        predicates.add(criteriaBuilder.equal(root.get("element").get("id"), searchCriteria.getElementId()));

        //Get not-deleted
        predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}