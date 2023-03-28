package com.emontazysta.repository.criteria;

import com.emontazysta.enums.EventStatus;
import com.emontazysta.mapper.filterMapper.EventFilterMapper;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.dto.filterDto.EventFilterDto;
import com.emontazysta.model.searchcriteria.EventSearchCriteria;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class EventCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final EventFilterMapper eventFilterMapper;

    public EventCriteriaRepository(EntityManager entityManager, EventFilterMapper eventFilterMapper) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.eventFilterMapper = eventFilterMapper;
    }

    public List<EventFilterDto> findAllWithFilters(EventSearchCriteria eventSearchCriteria, Principal principal) {

        CriteriaQuery<ToolEvent> toolEventCriteriaQuery = criteriaBuilder.createQuery(ToolEvent.class);
        Root<ToolEvent> toolEventRoot = toolEventCriteriaQuery.from(ToolEvent.class);
        Predicate toolEventPredicate = getToolEventPredicate(eventSearchCriteria, toolEventRoot, principal);
        toolEventCriteriaQuery.where(toolEventPredicate);

        TypedQuery<ToolEvent> toolEventTypedQuery = entityManager.createQuery(toolEventCriteriaQuery);
        List<ToolEvent> toolEvents = toolEventTypedQuery.getResultList();


        CriteriaQuery<ElementEvent> elementEventCriteriaQuery = criteriaBuilder.createQuery(ElementEvent.class);
        Root<ElementEvent> elementEventRoot = elementEventCriteriaQuery.from(ElementEvent.class);
        Predicate elementEventPredicate = getElementEventPredicate(eventSearchCriteria, elementEventRoot, principal);
        elementEventCriteriaQuery.where(elementEventPredicate);

        TypedQuery<ElementEvent> typedQuery = entityManager.createQuery(elementEventCriteriaQuery);
        List<ElementEvent> elementEvents = typedQuery.getResultList();

        List<EventFilterDto> result = new ArrayList<>();
        result.addAll(toolEvents.stream().map(eventFilterMapper::toolToDto).collect(Collectors.toList()));
        result.addAll(elementEvents.stream().map(eventFilterMapper::elementToDto).collect(Collectors.toList()));

        return result;
    }

    private Predicate getToolEventPredicate(EventSearchCriteria eventSearchCriteria, Root<ToolEvent> toolEventRoot, Principal principal) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(eventSearchCriteria.getItemName())) {
            predicates.add(criteriaBuilder.like(toolEventRoot.get("tool").get("name"), "%" + eventSearchCriteria.getItemName() + "%"));
        }

        if (Objects.nonNull(eventSearchCriteria.getTypeOfStatus())) {
            List<Predicate> typeOfStatusPredicates = new ArrayList<>();
            for (String type : eventSearchCriteria.getTypeOfStatus()) {
                typeOfStatusPredicates.add(criteriaBuilder.equal(toolEventRoot.get("status"), EventStatus.valueOf(type)));
            }
            predicates.add(criteriaBuilder.or(typeOfStatusPredicates.toArray(new Predicate[0])));
        }

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getElementEventPredicate(EventSearchCriteria eventSearchCriteria, Root<ElementEvent> elementEventRoot, Principal principal) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(eventSearchCriteria.getItemName())) {
            predicates.add(criteriaBuilder.like(elementEventRoot.get("element").get("name"), "%" + eventSearchCriteria.getItemName() + "%"));
        }

        if (Objects.nonNull(eventSearchCriteria.getTypeOfStatus())) {
            List<Predicate> typeOfStatusPredicates = new ArrayList<>();
            for (String type : eventSearchCriteria.getTypeOfStatus()) {
                typeOfStatusPredicates.add(criteriaBuilder.equal(elementEventRoot.get("status"), EventStatus.valueOf(type)));
            }
            predicates.add(criteriaBuilder.or(typeOfStatusPredicates.toArray(new Predicate[0])));
        }

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
