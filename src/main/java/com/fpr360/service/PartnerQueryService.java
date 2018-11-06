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

import com.fpr360.domain.Partner;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.PartnerRepository;
import com.fpr360.service.dto.PartnerCriteria;

/**
 * Service for executing complex queries for Partner entities in the database.
 * The main input is a {@link PartnerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Partner} or a {@link Page} of {@link Partner} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartnerQueryService extends OrganizationQueryService<Partner, PartnerRepository, PartnerCriteria> {

    public PartnerQueryService(PartnerRepository partnerRepository) {
        super(partnerRepository);
    }

    /**
     * Function to convert PartnerCriteria to a {@link Specification}
     */
    @Override
    public Specification<Partner> createSpecification(PartnerCriteria criteria) {
        Specification<Partner> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Partner_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Partner_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Partner_.description));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Partner_.address));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Partner_.city));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), Partner_.state));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), Partner_.postalCode));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Partner_.country));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Partner_.phoneNumber));
            }
            if (criteria.getPhoneExt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneExt(), Partner_.phoneExt));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Partner_.email));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), Partner_.website));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrency(), Partner_.currency));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Partner_.active));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Partner_.type));
            }
            if (criteria.getIndustryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getIndustryId(), Partner_.industry, Industry_.id));
            }
        }
        return specification;
    }
}
