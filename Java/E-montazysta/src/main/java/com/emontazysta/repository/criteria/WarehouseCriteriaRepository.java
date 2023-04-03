package com.emontazysta.repository.criteria;

import com.emontazysta.mapper.WarehouseLocationMapper;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.WarehouseLocationDto;
import com.emontazysta.model.searchcriteria.WarehouseSearchCriteria;
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
public class WarehouseCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final WarehouseLocationMapper warehouseLocationMapper;
    private final AuthUtils authUtils;

    public WarehouseCriteriaRepository(EntityManager entityManager, WarehouseLocationMapper warehouseLocationMapper,
                                       AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.warehouseLocationMapper = warehouseLocationMapper;
        this.authUtils = authUtils;

    }

    public List<WarehouseLocationDto> findAllWithFilters(WarehouseSearchCriteria warehouseSearchCriteria ) {

        CriteriaQuery<Warehouse> criteriaQuery = criteriaBuilder.createQuery(Warehouse.class);
        Root<Warehouse> warehouseRoot = criteriaQuery.from(Warehouse.class);
        Predicate predicate = getPredicate(warehouseSearchCriteria, warehouseRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Warehouse> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Warehouse> warehouses = typedQuery.getResultList();

        return warehouses.stream().map(warehouseLocationMapper::toDto).collect(Collectors.toList());
    }
    private Predicate getPredicate(WarehouseSearchCriteria warehouseSearchCriteria, Root<Warehouse> warehouseRoot) {
        List<Predicate> predicates = new ArrayList<>();

        //Get warehouse by name
        if(Objects.nonNull(warehouseSearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(warehouseRoot.get("name"), "%" + warehouseSearchCriteria.getName() + "%"));
        }

        //Get warehouse by description
        if(Objects.nonNull(warehouseSearchCriteria.getDescription())){
            predicates.add(criteriaBuilder.like(warehouseRoot.get("description"),
                    "%" + warehouseSearchCriteria.getDescription() + "%"));
        }

        //Get warehouses by zipcode
        if(Objects.nonNull(warehouseSearchCriteria.getZipCode())){
            predicates.add(criteriaBuilder.like(warehouseRoot.get("location").get("zipCode"),
                    "%" +warehouseSearchCriteria.getZipCode() + "%"));
        }

        //Get warehouses from company
        predicates.add(criteriaBuilder.equal(warehouseRoot.get("company").get("id"),
                authUtils.getLoggedUserCompanyId()));

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
