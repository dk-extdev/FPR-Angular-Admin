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

import com.fpr360.domain.MerchantAccount;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.MerchantAccountRepository;
import com.fpr360.service.dto.MerchantAccountCriteria;

/**
 * Service for executing complex queries for MerchantAccount entities in the database.
 * The main input is a {@link MerchantAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MerchantAccount} or a {@link Page} of {@link MerchantAccount} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MerchantAccountQueryService extends QueryService<MerchantAccount> {

    private final Logger log = LoggerFactory.getLogger(MerchantAccountQueryService.class);

    private final MerchantAccountRepository merchantAccountRepository;

    public MerchantAccountQueryService(MerchantAccountRepository merchantAccountRepository) {
        this.merchantAccountRepository = merchantAccountRepository;
    }

    /**
     * Return a {@link List} of {@link MerchantAccount} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MerchantAccount> findByCriteria(MerchantAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MerchantAccount> specification = createSpecification(criteria);
        return merchantAccountRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MerchantAccount} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MerchantAccount> findByCriteria(MerchantAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MerchantAccount> specification = createSpecification(criteria);
        return merchantAccountRepository.findAll(specification, page);
    }

    /**
     * Function to convert MerchantAccountCriteria to a {@link Specification}
     */
    private Specification<MerchantAccount> createSpecification(MerchantAccountCriteria criteria) {
        Specification<MerchantAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MerchantAccount_.id));
            }
            if (criteria.getMid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMid(), MerchantAccount_.mid));
            }
            if (criteria.getMidDescriptor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMidDescriptor(), MerchantAccount_.midDescriptor));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MerchantAccount_.active));
            }
            if (criteria.getMerchantId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMerchantId(), MerchantAccount_.merchant, Merchant_.id));
            }
            if (criteria.getClientCrmId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClientCrmId(), MerchantAccount_.clientCrm, ClientCrm_.id));
            }
        }
        return specification;
    }
}
