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

import com.fpr360.domain.GuruClient;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.GuruClientRepository;
import com.fpr360.service.dto.GuruClientCriteria;

/**
 * Service for executing complex queries for GuruClient entities in the database.
 * The main input is a {@link GuruClientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GuruClient} or a {@link Page} of {@link GuruClient} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GuruClientQueryService extends QueryService<GuruClient> {

    private final Logger log = LoggerFactory.getLogger(GuruClientQueryService.class);

    private final GuruClientRepository guruClientRepository;

    public GuruClientQueryService(GuruClientRepository guruClientRepository) {
        this.guruClientRepository = guruClientRepository;
    }

    /**
     * Return a {@link List} of {@link GuruClient} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GuruClient> findByCriteria(GuruClientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GuruClient> specification = createSpecification(criteria);
        return guruClientRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GuruClient} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GuruClient> findByCriteria(GuruClientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GuruClient> specification = createSpecification(criteria);
        return guruClientRepository.findAll(specification, page);
    }

    /**
     * Function to convert GuruClientCriteria to a {@link Specification}
     */
    private Specification<GuruClient> createSpecification(GuruClientCriteria criteria) {
        Specification<GuruClient> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), GuruClient_.id));
            }
            if (criteria.getLevel() != null) {
                specification = specification.and(buildSpecification(criteria.getLevel(), GuruClient_.level));
            }
            if (criteria.getGuruId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getGuruId(), GuruClient_.guru, User_.id));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClientId(), GuruClient_.client, Client_.id));
            }
        }
        return specification;
    }
}
