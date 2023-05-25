package com.emontazysta.repository.criteria;

import com.emontazysta.enums.NotificationType;
import com.emontazysta.mapper.NotificationMapper;
import com.emontazysta.model.Notification;
import com.emontazysta.model.dto.NotificationDto;
import com.emontazysta.model.searchcriteria.NotificationSearchCriteria;
import com.emontazysta.util.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class NotificationCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final NotificationMapper notificationMapper;
    private final AuthUtils authUtils;

    public NotificationCriteriaRepository(EntityManager entityManager, NotificationMapper notificationMapper,
                                          AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.notificationMapper = notificationMapper;
        this.authUtils = authUtils;
    }

    public List<NotificationDto> findAllWithFilters(NotificationSearchCriteria searchCriteria) {

        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        Root<Notification> root = criteriaQuery.from(Notification.class);
        Predicate predicate = getPredicate(searchCriteria, root);
        criteriaQuery.where(predicate);

        TypedQuery<Notification> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Notification> notifications = typedQuery.getResultList();

        return notifications.stream().map(notificationMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(NotificationSearchCriteria searchCriteria, Root<Notification> root) {
        List<Predicate> predicates = new ArrayList<>();

        //Get Notifications between createdAtMin and createdAtMax
        //Check if createdAtMin isn't after createdAtMax
        if (Objects.nonNull(searchCriteria.getCreatedAtMin())
                && Objects.nonNull(searchCriteria.getCreatedAtMax())) {
            if (LocalDateTime.parse(searchCriteria.getCreatedAtMin())
                    .isAfter(LocalDateTime.parse(searchCriteria.getCreatedAtMax()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        //Check dateFrom
        if (Objects.nonNull(searchCriteria.getCreatedAtMin())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"),
                    LocalDateTime.parse(searchCriteria.getCreatedAtMin())));
        }
        //Check dateTo
        if (Objects.nonNull(searchCriteria.getCreatedAtMax())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"),
                    LocalDateTime.parse(searchCriteria.getCreatedAtMax())));
        }

        //Get Notifications by NotificationType
        if (Objects.nonNull(searchCriteria.getNotificationTypes())) {
            List<Predicate> statusPredicates = new ArrayList<>();
            for (String type : searchCriteria.getNotificationTypes()) {
                statusPredicates.add(criteriaBuilder.equal(root.get("notificationType"), NotificationType.valueOf(type)));
            }
            predicates.add(criteriaBuilder.or(statusPredicates.toArray(new Predicate[0])));
        }

        //Get logged user notifications
        predicates.add(criteriaBuilder.equal(root.get("notifiedEmployee").get("id"),
                authUtils.getLoggedUser().getId()));

        //Get not-deleted
        predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
