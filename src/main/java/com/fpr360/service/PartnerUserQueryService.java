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

import com.fpr360.domain.PartnerUser;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.PartnerUserRepository;
import com.fpr360.service.dto.PartnerUserCriteria;

/**
 * Service for executing complex queries for PartnerUser entities in the database.
 * The main input is a {@link PartnerUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PartnerUser} or a {@link Page} of {@link PartnerUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartnerUserQueryService extends QueryService<PartnerUser> {

    private final Logger log = LoggerFactory.getLogger(PartnerUserQueryService.class);

    private final PartnerUserRepository partnerUserRepository;

    public PartnerUserQueryService(PartnerUserRepository partnerUserRepository) {
        this.partnerUserRepository = partnerUserRepository;
    }

    /**
     * Return a {@link List} of {@link PartnerUser} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PartnerUser> findByCriteria(PartnerUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PartnerUser> specification = createSpecification(criteria);
        return partnerUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PartnerUser} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartnerUser> findByCriteria(PartnerUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartnerUser> specification = createSpecification(criteria);
        return partnerUserRepository.findAll(specification, page);
    }

    /**
     * Function to convert PartnerUserCriteria to a {@link Specification}
     */
    private Specification<PartnerUser> createSpecification(PartnerUserCriteria criteria) {
        Specification<PartnerUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PartnerUser_.id));
            }
            if (criteria.getPartnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPartnerId(), PartnerUser_.partner, Partner_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), PartnerUser_.user, User_.id));
            }
        }
        return specification;
    }
}
