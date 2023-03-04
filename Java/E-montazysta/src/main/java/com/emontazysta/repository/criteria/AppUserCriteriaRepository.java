package com.emontazysta.repository.criteria;

import com.emontazysta.mapper.UserMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.AppUserDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.repository.UnavailabilityRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class AppUserCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final UserMapper userMapper;

    private final UnavailabilityRepository unavailabilityRepository;


    public AppUserCriteriaRepository(EntityManager entityManager, UserMapper userMapper, UnavailabilityRepository unavailabilityRepository) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.userMapper = userMapper;
        this.unavailabilityRepository = unavailabilityRepository;
    }

    public List<AppUserDto> findAllWithFilters(AppUserSearchCriteria appUserSearchCriteria) {

        CriteriaQuery<AppUser> criteriaQuery = criteriaBuilder.createQuery(AppUser.class);
        Root<AppUser> appUserRoot = criteriaQuery.from(AppUser.class);

        Predicate predicate = getPredicate(appUserSearchCriteria, appUserRoot);
        criteriaQuery.where(predicate);

        TypedQuery<AppUser> typedQuery = entityManager.createQuery(criteriaQuery);
        List<AppUser> orders = typedQuery.getResultList();

        return orders.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(AppUserSearchCriteria appUserSearchCriteria, Root<AppUser> appUserRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(appUserSearchCriteria.getFirstname())){
            predicates.add(criteriaBuilder.like(appUserRoot.get("firstName"),
                    "%" + appUserSearchCriteria.getFirstname() + "%"));
        }

        if(Objects.nonNull(appUserSearchCriteria.getLastName())){
            predicates.add(criteriaBuilder.like(appUserRoot.get("lastName"),
                    "%" + appUserSearchCriteria.getLastName() + "%"));
        }

        if(Objects.nonNull(appUserSearchCriteria.getEmail())){
            predicates.add(criteriaBuilder.equal(appUserRoot.get("email"),  appUserSearchCriteria.getEmail()));
        }

        if(Objects.nonNull(appUserSearchCriteria.getAvailableFrom()) && Objects.nonNull(appUserSearchCriteria.getAvailableTo())){
            List <Long> avaibleUsers = new ArrayList<>();
            List <Unavailability> unavailabilities = new ArrayList();
            unavailabilities = unavailabilityRepository.findAll();
            if(unavailabilities != null && !unavailabilities.isEmpty()){
                for (Unavailability unavailability: unavailabilities) {
                    if(!((LocalDateTime.parse(appUserSearchCriteria.getAvailableFrom()).isAfter(unavailability.getUnavailableFrom()) &&
                            LocalDateTime.parse(appUserSearchCriteria.getAvailableFrom()).isBefore(unavailability.getUnavailableTo())) ||
                            (LocalDateTime.parse(appUserSearchCriteria.getAvailableTo()).isAfter(unavailability.getUnavailableFrom()) &&
                             LocalDateTime.parse(appUserSearchCriteria.getAvailableTo()).isBefore(unavailability.getUnavailableTo())) ||
                            (unavailability.getUnavailableFrom().isAfter(LocalDateTime.parse(appUserSearchCriteria.getAvailableFrom())) &&
                             unavailability.getUnavailableFrom().isBefore(LocalDateTime.parse(appUserSearchCriteria.getAvailableTo()))) ||
                            (unavailability.getUnavailableTo().isAfter(LocalDateTime.parse(appUserSearchCriteria.getAvailableFrom())) &&
                            unavailability.getUnavailableTo().isBefore(LocalDateTime.parse(appUserSearchCriteria.getAvailableTo()))))){
                                 avaibleUsers.add(unavailability.getAssignedTo().getId());
                    }
                }
            }
            predicates.add(appUserRoot.get("id").in(avaibleUsers));
        }
            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
