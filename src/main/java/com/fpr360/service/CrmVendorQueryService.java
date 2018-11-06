package com.fpr360.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.fpr360.domain.CrmVendor;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.CrmVendorRepository;
import com.fpr360.service.dto.CrmVendorCriteria;

/**
 * Service for executing complex queries for CrmVendor entities in the database.
 * The main input is a {@link CrmVendorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CrmVendor} or a {@link Page} of {@link CrmVendor} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CrmVendorQueryService extends QueryService<CrmVendor> {

    private final Logger log = LoggerFactory.getLogger(CrmVendorQueryService.class);

    private final CrmVendorRepository crmVendorRepository;

    public CrmVendorQueryService(CrmVendorRepository crmVendorRepository) {
        this.crmVendorRepository = crmVendorRepository;
    }

    /**
     * Return a {@link List} of {@link CrmVendor} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CrmVendor> findByCriteria(CrmVendorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CrmVendor> specification = createSpecification(criteria);
        return crmVendorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CrmVendor} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CrmVendor> findByCriteria(CrmVendorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CrmVendor> specification = createSpecification(criteria);
        return crmVendorRepository.findAll(specification, page);
    }

    /**
     * Function to convert CrmVendorCriteria to a {@link Specification}
     */
    private Specification<CrmVendor> createSpecification(CrmVendorCriteria criteria) {
        Specification<CrmVendor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CrmVendor_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CrmVendor_.name));
            }
            if (criteria.getApiType() != null) {
                specification = specification.and(buildSpecification(criteria.getApiType(), CrmVendor_.apiType));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), CrmVendor_.website));
            }
        }
        return specification;
    }
}
