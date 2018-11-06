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

import com.fpr360.domain.ClientUser;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.ClientUserRepository;
import com.fpr360.service.dto.ClientUserCriteria;

/**
 * Service for executing complex queries for ClientUser entities in the database.
 * The main input is a {@link ClientUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientUser} or a {@link Page} of {@link ClientUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientUserQueryService extends QueryService<ClientUser> {

    private final Logger log = LoggerFactory.getLogger(ClientUserQueryService.class);

    private final ClientUserRepository clientUserRepository;

    public ClientUserQueryService(ClientUserRepository clientUserRepository) {
        this.clientUserRepository = clientUserRepository;
    }

    /**
     * Return a {@link List} of {@link ClientUser} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientUser> findByCriteria(ClientUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClientUser> specification = createSpecification(criteria);
        return clientUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ClientUser} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientUser> findByCriteria(ClientUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClientUser> specification = createSpecification(criteria);
        return clientUserRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClientUserCriteria to a {@link Specification}
     */
    private Specification<ClientUser> createSpecification(ClientUserCriteria criteria) {
        Specification<ClientUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClientUser_.id));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClientId(), ClientUser_.client, Client_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), ClientUser_.user, User_.id));
            }
        }
        return specification;
    }
}
