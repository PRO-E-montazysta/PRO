package com.emontazysta.repository.criteria;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.filterMapper.ToolFilterMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Tool;
import com.emontazysta.model.dto.filterDto.ToolFilterDto;
import com.emontazysta.model.searchcriteria.ToolSearchCriteria;
import com.emontazysta.service.AppUserService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ToolCriteriaRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final ToolFilterMapper toolFilterMapper;
    private final AppUserService userService;

    public ToolCriteriaRepository(EntityManager entityManager, ToolFilterMapper toolFilterMapper, AppUserService userService) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.toolFilterMapper = toolFilterMapper;
        this.userService = userService;
    }

    public List<ToolFilterDto> finadAllWithFilter(ToolSearchCriteria toolSearchCriteria, Principal principal){
        CriteriaQuery<Tool> criteriaQuery = criteriaBuilder.createQuery(Tool.class);
        Root<Tool> toolRoot = criteriaQuery.from(Tool.class);
        Predicate predicate = getPredicate(toolSearchCriteria, toolRoot, principal);
        criteriaQuery.where(predicate);

        TypedQuery<Tool> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tool> toolList = typedQuery.getResultList();

        return toolList.stream().map(toolFilterMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(ToolSearchCriteria toolSearchCriteria, Root<Tool> toolRoot, Principal principal) {
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
            List<Predicate> warehouseIdPredicates = new ArrayList<>();

            for (String warehouses: toolSearchCriteria.getWarehouse_Id()) {
                warehouseIdPredicates.add(criteriaBuilder.equal(toolRoot.get("warehouse").get("id"),
                        Long.valueOf(warehouses)));
            }
            predicates.add(criteriaBuilder.or(warehouseIdPredicates.toArray(new Predicate[0])));
        }

        if(Objects.nonNull(toolSearchCriteria.getToolType_Id())){
            List<Predicate> toolTypePredicates = new ArrayList<>();
            for (String toolID: toolSearchCriteria.getToolType_Id()) {
                toolTypePredicates.add(criteriaBuilder.equal(toolRoot.get("toolType").get("id"),
                        Long.valueOf(toolID)));
            }
            predicates.add(criteriaBuilder.or(toolTypePredicates.toArray(new Predicate[0])));
        }

        AppUser user =  userService.findByUsername(principal.getName());
        Boolean isCloudAdmin = user.getRoles().contains(Role.CLOUD_ADMIN);

        if(!isCloudAdmin) {
            Optional<Employment> takingEmployment = user.getEmployments().stream()
                    .filter(employment -> employment.getDateOfDismiss() == null)
                    .findFirst();

            if (takingEmployment.isPresent()) {
                Long companyId = takingEmployment.get().getCompany().getId();
                predicates.add(criteriaBuilder.equal(toolRoot.get("company").get("id"), companyId));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else {
                throw new IllegalArgumentException("The logged user is not a registered employee");
            }
        } else {
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }
}
