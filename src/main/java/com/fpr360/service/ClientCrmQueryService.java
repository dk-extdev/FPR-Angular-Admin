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

import com.fpr360.domain.ClientCrm;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.ClientCrmRepository;
import com.fpr360.service.dto.ClientCrmCriteria;

/**
 * Service for executing complex queries for ClientCrm entities in the database.
 * The main input is a {@link ClientCrmCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientCrm} or a {@link Page} of {@link ClientCrm} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientCrmQueryService extends QueryService<ClientCrm> {

    private final Logger log = LoggerFactory.getLogger(ClientCrmQueryService.class);

    private final ClientCrmRepository clientCrmRepository;

    public ClientCrmQueryService(ClientCrmRepository clientCrmRepository) {
        this.clientCrmRepository = clientCrmRepository;
    }

    /**
     * Return a {@link List} of {@link ClientCrm} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientCrm> findByCriteria(ClientCrmCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClientCrm> specification = createSpecification(criteria);
        return clientCrmRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ClientCrm} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientCrm> findByCriteria(ClientCrmCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClientCrm> specification = createSpecification(criteria);
        return clientCrmRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClientCrmCriteria to a {@link Specification}
     */
    private Specification<ClientCrm> createSpecification(ClientCrmCriteria criteria) {
        Specification<ClientCrm> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClientCrm_.id));
            }
            if (criteria.getEndpoint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndpoint(), ClientCrm_.endpoint));
            }
            if (criteria.getWebUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebUsername(), ClientCrm_.webUsername));
            }
            if (criteria.getWebPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebPassword(), ClientCrm_.webPassword));
            }
            if (criteria.getApiKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApiKey(), ClientCrm_.apiKey));
            }
            if (criteria.getApiSecret() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApiSecret(), ClientCrm_.apiSecret));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClientId(), ClientCrm_.client, Client_.id));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getVendorId(), ClientCrm_.vendor, CrmVendor_.id));
            }
        }
        return specification;
    }
}
