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

import com.fpr360.domain.ContactInfo;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.ContactInfoRepository;
import com.fpr360.service.dto.ContactInfoCriteria;

/**
 * Service for executing complex queries for ContactInfo entities in the database.
 * The main input is a {@link ContactInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContactInfo} or a {@link Page} of {@link ContactInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactInfoQueryService extends QueryService<ContactInfo> {

    private final Logger log = LoggerFactory.getLogger(ContactInfoQueryService.class);

    private final ContactInfoRepository contactInfoRepository;

    public ContactInfoQueryService(ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    }

    /**
     * Return a {@link List} of {@link ContactInfo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContactInfo> findByCriteria(ContactInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContactInfo> specification = createSpecification(criteria);
        return contactInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ContactInfo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactInfo> findByCriteria(ContactInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContactInfo> specification = createSpecification(criteria);
        return contactInfoRepository.findAll(specification, page);
    }

    /**
     * Function to convert ContactInfoCriteria to a {@link Specification}
     */
    private Specification<ContactInfo> createSpecification(ContactInfoCriteria criteria) {
        Specification<ContactInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ContactInfo_.id));
            }
            if (criteria.getPrimaryPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrimaryPhone(), ContactInfo_.primaryPhone));
            }
            if (criteria.getSecondaryPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecondaryPhone(), ContactInfo_.secondaryPhone));
            }
            if (criteria.getWorkPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkPhone(), ContactInfo_.workPhone));
            }
            if (criteria.getWorkPhoneExt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkPhoneExt(), ContactInfo_.workPhoneExt));
            }
            if (criteria.getMobilePhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobilePhone(), ContactInfo_.mobilePhone));
            }
            if (criteria.getSkypeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSkypeId(), ContactInfo_.skypeId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), ContactInfo_.user, User_.id));
            }
        }
        return specification;
    }
}
