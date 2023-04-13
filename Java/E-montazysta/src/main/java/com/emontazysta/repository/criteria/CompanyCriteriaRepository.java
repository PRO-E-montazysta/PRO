package com.emontazysta.repository.criteria;

import com.emontazysta.enums.CompanyStatus;
import com.emontazysta.mapper.CompanyMapper;
import com.emontazysta.model.Company;
import com.emontazysta.model.dto.CompanyDto;
import com.emontazysta.model.searchcriteria.CompanySearchCriteria;
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
public class CompanyCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final CompanyMapper companyMapper;

    public CompanyCriteriaRepository(EntityManager entityManager, CompanyMapper companyMapper) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.companyMapper = companyMapper;
    }

    public List<CompanyDto> findAllWithFilters(CompanySearchCriteria companySearchCriteria) {
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> companyRoot = criteriaQuery.from(Company.class);
        Predicate predicate = getPredicate(companySearchCriteria, companyRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Company> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Company> companies = typedQuery.getResultList();

        return companies.stream().map(companyMapper::toDto).collect(Collectors.toList());
    }

    private Predicate getPredicate(CompanySearchCriteria companySearchCriteria, Root<Company> companyRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(companySearchCriteria.getCompanyName())) {
            predicates.add(criteriaBuilder.like(companyRoot.get("companyName"), "%" + companySearchCriteria.getCompanyName() + "%"));
        }

        if (Objects.nonNull(companySearchCriteria.getStatus())) {
            List<Predicate> statusPredicates = new ArrayList<>();
            for (String type : companySearchCriteria.getStatus()) {
                statusPredicates.add(criteriaBuilder.equal(companyRoot.get("status"), CompanyStatus.valueOf(type)));
            }
            predicates.add(criteriaBuilder.or(statusPredicates.toArray(new Predicate[0])));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
