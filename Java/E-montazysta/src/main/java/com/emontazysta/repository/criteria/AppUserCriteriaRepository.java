package com.emontazysta.repository.criteria;

import com.emontazysta.enums.CompanyStatus;
import com.emontazysta.enums.Role;
import com.emontazysta.mapper.EmployeeMapper;
import com.emontazysta.model.AppUser;
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
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class AppUserCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final EmployeeMapper employeeMapper;
    private final UnavailabilityRepository unavailabilityRepository;
    private final AppUserRepository usersRepository;


    public AppUserCriteriaRepository(EntityManager entityManager, EmployeeMapper employeeMapper, UnavailabilityRepository unavailabilityRepository, AppUserRepository usersRepository) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.employeeMapper = employeeMapper;
        this.unavailabilityRepository = unavailabilityRepository;
        this.usersRepository = usersRepository;
    }

    public List<EmployeeDto> findAllWithFilters(AppUserSearchCriteria appUserSearchCriteria, Principal principal) {

        CriteriaQuery<AppUser> criteriaQuery = criteriaBuilder.createQuery(AppUser.class);
        Root<AppUser> appUserRoot = criteriaQuery.from(AppUser.class);

        Predicate predicate = getPredicate(appUserSearchCriteria, appUserRoot);
        criteriaQuery.where(predicate);

        TypedQuery<AppUser> typedQuery = entityManager.createQuery(criteriaQuery);
        List<AppUser> appUsers = typedQuery.getResultList();
        List<AppUser> resultUsers = new ArrayList<>();
        List<EmployeeDto> resultTmp = new ArrayList<>();
        AppUser loggedUser = usersRepository.findByUsername(principal.getName());
        Long loggedUserCompanyId = loggedUser.getEmployments().get(0).getCompany().getId();

        for(AppUser appUser : appUsers) {
            if(!appUser.getRoles().contains(Role.CLOUD_ADMIN)) {
                if(appUser.getEmployments() != null && appUser.getEmployments().size() > 0) {
                    if(loggedUserCompanyId.equals(appUser.getEmployments().get(0).getCompany().getId())) {
                        resultUsers.add(appUser);
                    }
                }
            }
        }

        //Hide Username and Pesel for non Admin users and filter by Pesel
        for(AppUser appUser : resultUsers) {
            EmployeeDto resultEmp = employeeMapper.toDto(appUser);
            if(!loggedUser.getRoles().contains(Role.ADMIN)) {
                resultEmp.setUsername(null);
                resultEmp.setPesel(null);
                resultTmp.add(resultEmp);
            }else {
                //Filter by Pesel
                if(Objects.nonNull(appUserSearchCriteria.getPesel())){
                    if(resultEmp.getPesel() != null && resultEmp.getPesel().contains(appUserSearchCriteria.getPesel())) {
                        resultTmp.add(resultEmp);
                    }
                }else {
                    resultTmp.add(resultEmp);
                }
            }
        }

        List<EmployeeDto> resultDto = new ArrayList<>(List.copyOf(resultTmp));
        resultTmp = new ArrayList<>();

        //Filter by Roles
        if(Objects.nonNull(appUserSearchCriteria.getRoles())){
            for(EmployeeDto emp : resultDto) {
                if(emp.getRoles().stream().anyMatch(role -> appUserSearchCriteria.getRoles().contains(role.toString()))) {
                    resultTmp.add(emp);
                }
            }

            resultDto = new ArrayList<>(List.copyOf(resultTmp));
            resultTmp = new ArrayList<>();
        }

        //Filter by Phone
        if(Objects.nonNull(appUserSearchCriteria.getPhone())){
            for(EmployeeDto emp : resultDto) {
                if(emp.getPhone().contains(appUserSearchCriteria.getPhone())) {
                    resultTmp.add(emp);
                }
            }
            resultDto = new ArrayList<>(List.copyOf(resultTmp));
        }

        return resultDto;
    }

    private Predicate getPredicate(AppUserSearchCriteria appUserSearchCriteria, Root<AppUser> appUserRoot) {
        List<Predicate> predicates = new ArrayList<>();

        //Filter by firstName
        if(Objects.nonNull(appUserSearchCriteria.getFirstName())){
            predicates.add(criteriaBuilder.like(appUserRoot.get("firstName"),
                    "%" + appUserSearchCriteria.getFirstName() + "%"));
        }

        //Filter by LastName
        if(Objects.nonNull(appUserSearchCriteria.getLastName())){
            predicates.add(criteriaBuilder.like(appUserRoot.get("lastName"),
                    "%" + appUserSearchCriteria.getLastName() + "%"));
        }

        //Filter by Email
        if(Objects.nonNull(appUserSearchCriteria.getEmail())){
            predicates.add(criteriaBuilder.like(appUserRoot.get("email"),
                    "%" + appUserSearchCriteria.getEmail()+ "%"));
        }

        //Filter by Available in AvailableFrom and AvailableTo
        if(Objects.nonNull(appUserSearchCriteria.getAvailableFrom()) && Objects.nonNull(appUserSearchCriteria.getAvailableTo())){
            predicates.add(appUserRoot.get("id").in(findAvailableUsers(appUserSearchCriteria)));
        }

        //Get not-deleted
        predicates.add(criteriaBuilder.equal(appUserRoot.get("deleted"), false));

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private List<Long> findAvailableUsers(AppUserSearchCriteria appUserSearchCriteria) {
        List <Long> availableEmployees = usersRepository.findAllByRolesNotContaining(Role.CLOUD_ADMIN)
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
                    availableEmployees.remove(unavailability.getAssignedTo().getId());
                }
            }
        }
        return availableEmployees;
    }
}
