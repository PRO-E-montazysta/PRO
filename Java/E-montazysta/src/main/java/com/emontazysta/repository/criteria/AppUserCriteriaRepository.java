package com.emontazysta.repository.criteria;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.EployeeMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Employee;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.repository.AppUserRepository;
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
    private final EployeeMapper employeeMapper;
    private final UnavailabilityRepository unavailabilityRepository;
    private final AppUserRepository usersRepository;


    public AppUserCriteriaRepository(EntityManager entityManager, EployeeMapper employeeMapper, UnavailabilityRepository unavailabilityRepository, AppUserRepository usersRepository) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.employeeMapper = employeeMapper;
        this.unavailabilityRepository = unavailabilityRepository;
        this.usersRepository = usersRepository;
    }

    public List<EmployeeDto> findAllWithFilters(AppUserSearchCriteria appUserSearchCriteria) {

        CriteriaQuery<AppUser> criteriaQuery = criteriaBuilder.createQuery(AppUser.class);
        Root<AppUser> appUserRoot = criteriaQuery.from(AppUser.class);

        Predicate predicate = getPredicate(appUserSearchCriteria, appUserRoot);
        criteriaQuery.where(predicate);

        TypedQuery<AppUser> typedQuery = entityManager.createQuery(criteriaQuery);
        List<AppUser> orders = typedQuery.getResultList();

        return orders.stream().map(employeeMapper::employeeToDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(AppUserSearchCriteria appUserSearchCriteria, Root<AppUser> appUserRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(appUserSearchCriteria.getFirstName())){
            predicates.add(criteriaBuilder.like(appUserRoot.get("firstName"),
                    "%" + appUserSearchCriteria.getFirstName() + "%"));
        }

        if(Objects.nonNull(appUserSearchCriteria.getLastName())){
            predicates.add(criteriaBuilder.like(appUserRoot.get("lastName"),
                    "%" + appUserSearchCriteria.getLastName() + "%"));
        }

        if(Objects.nonNull(appUserSearchCriteria.getEmail())){
            predicates.add(criteriaBuilder.equal(appUserRoot.get("email"),  appUserSearchCriteria.getEmail()));
        }

        if(Objects.nonNull(appUserSearchCriteria.getAvailableFrom()) && Objects.nonNull(appUserSearchCriteria.getAvailableTo())){
            predicates.add(appUserRoot.get("id").in(findAvaibleUsers(appUserSearchCriteria)));
        }

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private List<Long> findAvaibleUsers(AppUserSearchCriteria appUserSearchCriteria) {
        List <Long> avaibleEmployees = usersRepository.findAllByRolesNotContaining(Role.CLOUD_ADMIN)
                .stream()
                .map(appUser -> appUser.getId())
                .collect(Collectors.toList());

        List <Unavailability> unavailabilities = unavailabilityRepository.findAll();
        if(unavailabilities != null && !unavailabilities.isEmpty()){
            for (Unavailability unavailability: unavailabilities) {
                if((LocalDateTime.parse(appUserSearchCriteria.getAvailableFrom()).isAfter(unavailability.getUnavailableFrom()) &&
                        LocalDateTime.parse(appUserSearchCriteria.getAvailableFrom()).isBefore(unavailability.getUnavailableTo())) ||
                        (LocalDateTime.parse(appUserSearchCriteria.getAvailableTo()).isAfter(unavailability.getUnavailableFrom()) &&
                         LocalDateTime.parse(appUserSearchCriteria.getAvailableTo()).isBefore(unavailability.getUnavailableTo())) ||
                        (unavailability.getUnavailableFrom().isAfter(LocalDateTime.parse(appUserSearchCriteria.getAvailableFrom())) &&
                         unavailability.getUnavailableFrom().isBefore(LocalDateTime.parse(appUserSearchCriteria.getAvailableTo()))) ||
                        (unavailability.getUnavailableTo().isAfter(LocalDateTime.parse(appUserSearchCriteria.getAvailableFrom())) &&
                        unavailability.getUnavailableTo().isBefore(LocalDateTime.parse(appUserSearchCriteria.getAvailableTo())))){
                    avaibleEmployees.remove(unavailability.getAssignedTo().getId());
                }
            }
        }
        return avaibleEmployees;
    }
}
