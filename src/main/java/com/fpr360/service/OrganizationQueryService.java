package com.fpr360.service;

import com.fpr360.domain.Organization;
import com.fpr360.service.dto.OrganizationCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link Organization} (and its sub classes) entities in the database.
 * The main input is a {@link OrganizationCriteria} (or its sub classes) which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Organization} (or its sub classes) or a {@link Page} of {@link Organization}
 * which fulfills the criteria.
 */
public abstract class OrganizationQueryService<
        E, // The entity
        R extends JpaSpecificationExecutor<E>, // the query executor, usually the repository class
        EC extends OrganizationCriteria
        >
        extends QueryService<E> {

    private final Logger log = LoggerFactory.getLogger(ServiceProviderQueryService.class);
    protected final R repository;

    public OrganizationQueryService(R repository) {
        this.repository = repository;
    }

    /**
     * Return a {@link List} of {@link Organization} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<E> findByCriteria(EC criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<E> specification = createSpecification(criteria);
        return this.repository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Organization} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<E> findByCriteria(EC criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<E> specification = createSpecification(criteria);
        return this.repository.findAll(specification, page);
    }

    /**
     * Convert {@link OrganizationCriteria} (and its subclasses) to a {@link Specification}.
     */
    protected abstract Specification<E> createSpecification(EC criteria);
}
