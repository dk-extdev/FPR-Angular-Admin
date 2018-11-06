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

import com.fpr360.domain.PartnerClient;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.PartnerClientRepository;
import com.fpr360.service.dto.PartnerClientCriteria;

/**
 * Service for executing complex queries for PartnerClient entities in the database.
 * The main input is a {@link PartnerClientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PartnerClient} or a {@link Page} of {@link PartnerClient} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartnerClientQueryService extends QueryService<PartnerClient> {

    private final Logger log = LoggerFactory.getLogger(PartnerClientQueryService.class);

    private final PartnerClientRepository partnerClientRepository;

    public PartnerClientQueryService(PartnerClientRepository partnerClientRepository) {
        this.partnerClientRepository = partnerClientRepository;
    }

    /**
     * Return a {@link List} of {@link PartnerClient} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PartnerClient> findByCriteria(PartnerClientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PartnerClient> specification = createSpecification(criteria);
        return partnerClientRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PartnerClient} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartnerClient> findByCriteria(PartnerClientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartnerClient> specification = createSpecification(criteria);
        return partnerClientRepository.findAll(specification, page);
    }

    /**
     * Function to convert PartnerClientCriteria to a {@link Specification}
     */
    private Specification<PartnerClient> createSpecification(PartnerClientCriteria criteria) {
        Specification<PartnerClient> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PartnerClient_.id));
            }
            if (criteria.getPartnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPartnerId(), PartnerClient_.partner, Partner_.id));
            }
            if (criteria.getMerchantAccountId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMerchantAccountId(), PartnerClient_.merchantAccount, MerchantAccount_.id));
            }
        }
        return specification;
    }
}
