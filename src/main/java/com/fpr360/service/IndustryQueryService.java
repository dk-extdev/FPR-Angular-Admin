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

import com.fpr360.domain.Industry;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.IndustryRepository;
import com.fpr360.service.dto.IndustryCriteria;

/**
 * Service for executing complex queries for Industry entities in the database.
 * The main input is a {@link IndustryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Industry} or a {@link Page} of {@link Industry} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndustryQueryService extends QueryService<Industry> {

    private final Logger log = LoggerFactory.getLogger(IndustryQueryService.class);

    private final IndustryRepository industryRepository;

    public IndustryQueryService(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    /**
     * Return a {@link List} of {@link Industry} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Industry> findByCriteria(IndustryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Industry> specification = createSpecification(criteria);
        return industryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Industry} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Industry> findByCriteria(IndustryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Industry> specification = createSpecification(criteria);
        return industryRepository.findAll(specification, page);
    }

    /**
     * Function to convert IndustryCriteria to a {@link Specification}
     */
    private Specification<Industry> createSpecification(IndustryCriteria criteria) {
        Specification<Industry> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Industry_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Industry_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Industry_.description));
            }
        }
        return specification;
    }
}
