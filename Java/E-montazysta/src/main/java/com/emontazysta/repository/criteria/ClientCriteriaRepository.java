package com.emontazysta.repository.criteria;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.ClientMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Client;
import com.emontazysta.model.Employment;
import com.emontazysta.model.dto.ClientDto;
import com.emontazysta.model.searchcriteria.ClientSearchCriteria;
import com.emontazysta.service.AppUserService;
import com.emontazysta.util.AuthUtils;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ClientCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final ClientMapper clientMapper;
    private final AuthUtils authUtils;

    public ClientCriteriaRepository(EntityManager entityManager, ClientMapper clientMapper,  AuthUtils authUtils) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.clientMapper = clientMapper;
        this.authUtils = authUtils;
    }

    public List<ClientDto> findAllWithFilters(ClientSearchCriteria clientSearchCriteria, Principal principal) {

        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> ordersRoot = criteriaQuery.from(Client.class);
        Predicate predicate = getPredicate(clientSearchCriteria, ordersRoot, principal);
        criteriaQuery.where(predicate);

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Client> orders = typedQuery.getResultList();

        return orders.stream().map(clientMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(ClientSearchCriteria clientSearchCriteria, Root<Client> clientRoot, Principal principal) {
        List<Predicate> predicates = new ArrayList<>();

        //Get clients by name
        if (Objects.nonNull(clientSearchCriteria.getName())) {
            predicates.add(criteriaBuilder.like(clientRoot.get("name"), "%" + clientSearchCriteria.getName() + "%"));
        }

        //Get clients from company
        predicates.add(criteriaBuilder.equal(clientRoot.get("company").get("id"), authUtils.getLoggedUserCompanyId()));

        //Get not-deleted
        predicates.add(criteriaBuilder.equal(clientRoot.get("deleted"), false));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
