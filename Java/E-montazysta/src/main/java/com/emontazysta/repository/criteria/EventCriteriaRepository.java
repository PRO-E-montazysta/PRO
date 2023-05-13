package com.emontazysta.repository.criteria;

import com.emontazysta.enums.EventStatus;
import com.emontazysta.enums.Role;
import com.emontazysta.mapper.filterMapper.EventFilterMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.ToolEvent;
import com.emontazysta.model.dto.filterDto.EventFilterDto;
import com.emontazysta.model.searchcriteria.EventSearchCriteria;
import com.emontazysta.service.EmploymentService;
import com.emontazysta.util.AuthUtils;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class EventCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final EventFilterMapper eventFilterMapper;
    private final AuthUtils authUtils;

    public EventCriteriaRepository(EntityManager entityManager, EventFilterMapper eventFilterMapper, AuthUtils authUtils, EmploymentService employmentService) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.eventFilterMapper = eventFilterMapper;
        this.authUtils = authUtils;
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

        if (Objects.nonNull(eventSearchCriteria.getEventType())) {
            List<Predicate> eventTypePredicates = new ArrayList<>();
            for (String type : eventSearchCriteria.getEventType()) {
                eventTypePredicates.add(criteriaBuilder.equal(criteriaBuilder.substring(toolEventRoot.get("tool").get("code"), 0, 1), type));
            }
            predicates.add(criteriaBuilder.or(eventTypePredicates.toArray(new Predicate[0])));
        }

        if (Objects.nonNull(eventSearchCriteria.getEventDateMin())
                && Objects.nonNull(eventSearchCriteria.getEventDateMax())) {

            if (LocalDateTime.parse(eventSearchCriteria.getEventDateMin())
                    .isBefore(LocalDateTime.parse(eventSearchCriteria.getEventDateMax())) ||
                    LocalDateTime.parse(eventSearchCriteria.getEventDateMin())
                            .isEqual(LocalDateTime.parse(eventSearchCriteria.getEventDateMax()))) {

                predicates.add(criteriaBuilder.greaterThanOrEqualTo(toolEventRoot.get("eventDate"),
                        LocalDateTime.parse(eventSearchCriteria.getEventDateMin())));

                predicates.add(criteriaBuilder.lessThanOrEqualTo(toolEventRoot.get("eventDate"),
                        LocalDateTime.parse(eventSearchCriteria.getEventDateMax())));
            } else {
                throw new IllegalArgumentException("The start time must precede the end time");
            }
        } else if (Objects.nonNull(eventSearchCriteria.getEventDateMin())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(toolEventRoot.get("eventDate"),
                    LocalDateTime.parse(eventSearchCriteria.getEventDateMin())));
        } else if (Objects.nonNull(eventSearchCriteria.getEventDateMax())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(toolEventRoot.get("eventDate"),
                    LocalDateTime.parse(eventSearchCriteria.getEventDateMax())));
        }

        AppUser user =  authUtils.getLoggedUser();
        Boolean displayOwn = user.getRoles().contains(Role.FITTER) || user.getRoles().contains(Role.FOREMAN);
        if(displayOwn) {
            predicates.add(criteriaBuilder.equal(toolEventRoot.get("createdBy").get("id"), user.getId()));
        }else{
            predicates.add(criteriaBuilder.equal(toolEventRoot.get("tool").get("warehouse").get("company").get("id"),
                    authUtils.getLoggedUserCompanyId()));
        }

        //Get not-deleted
        predicates.add(criteriaBuilder.equal(toolEventRoot.get("deleted"), false));

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getElementEventPredicate(EventSearchCriteria eventSearchCriteria, Root<ElementEvent> elementEventRoot, Principal principal) {
        List<Predicate> predicates = new ArrayList<>();

        //Get events by name
        if (Objects.nonNull(eventSearchCriteria.getItemName())) {
            predicates.add(criteriaBuilder.like(elementEventRoot.get("element").get("name"), "%" + eventSearchCriteria.getItemName() + "%"));
        }

        //Get events by typeOfStatus
        if (Objects.nonNull(eventSearchCriteria.getTypeOfStatus())) {
            List<Predicate> typeOfStatusPredicates = new ArrayList<>();
            for (String type : eventSearchCriteria.getTypeOfStatus()) {
                typeOfStatusPredicates.add(criteriaBuilder.equal(elementEventRoot.get("status"), EventStatus.valueOf(type)));
            }
            predicates.add(criteriaBuilder.or(typeOfStatusPredicates.toArray(new Predicate[0])));
        }

        //Get events by eventType
        if (Objects.nonNull(eventSearchCriteria.getEventType())) {
            List<Predicate> eventTypePredicates = new ArrayList<>();
            for (String type : eventSearchCriteria.getEventType()) {
                eventTypePredicates.add(criteriaBuilder.equal(criteriaBuilder.substring(elementEventRoot.get("element").get("code"), 0, 1), type));
            }
            predicates.add(criteriaBuilder.or(eventTypePredicates.toArray(new Predicate[0])));
        }

        //Get events by eventDate between start and end date
        if (Objects.nonNull(eventSearchCriteria.getEventDateMin())
                && Objects.nonNull(eventSearchCriteria.getEventDateMax())) {

            if (LocalDateTime.parse(eventSearchCriteria.getEventDateMin())
                    .isBefore(LocalDateTime.parse(eventSearchCriteria.getEventDateMax())) ||
                    LocalDateTime.parse(eventSearchCriteria.getEventDateMin())
                            .isEqual(LocalDateTime.parse(eventSearchCriteria.getEventDateMax()))) {

                predicates.add(criteriaBuilder.greaterThanOrEqualTo(elementEventRoot.get("eventDate"),
                        LocalDateTime.parse(eventSearchCriteria.getEventDateMin())));

                predicates.add(criteriaBuilder.lessThanOrEqualTo(elementEventRoot.get("eventDate"),
                        LocalDateTime.parse(eventSearchCriteria.getEventDateMax())));
            } else {
                throw new IllegalArgumentException("The start time must precede the end time");
            }
        } else if (Objects.nonNull(eventSearchCriteria.getEventDateMin())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(elementEventRoot.get("eventDate"),
                    LocalDateTime.parse(eventSearchCriteria.getEventDateMin())));
        } else if (Objects.nonNull(eventSearchCriteria.getEventDateMax())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(elementEventRoot.get("eventDate"),
                    LocalDateTime.parse(eventSearchCriteria.getEventDateMax())));
        }

        //Get events by createdBy or from user company
        AppUser user =  authUtils.getLoggedUser();
        Boolean displayOwn = user.getRoles().contains(Role.FITTER) || user.getRoles().contains(Role.FOREMAN);
        if(displayOwn) {
            predicates.add(criteriaBuilder.equal(elementEventRoot.get("createdBy").get("id"), user.getId()));
        }else{
            predicates.add(criteriaBuilder.equal(elementEventRoot.join("element").join("elementInWarehouses")
                            .get("warehouse").get("company").get("id"), authUtils.getLoggedUserCompanyId()));
        }

        //Get not-deleted
        predicates.add(criteriaBuilder.equal(elementEventRoot.get("deleted"), false));

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
