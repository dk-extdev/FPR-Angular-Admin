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

import com.fpr360.domain.Merchant;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.MerchantRepository;
import com.fpr360.service.dto.MerchantCriteria;

/**
 * Service for executing complex queries for Merchant entities in the database.
 * The main input is a {@link MerchantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Merchant} or a {@link Page} of {@link Merchant} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MerchantQueryService extends OrganizationQueryService<Merchant, MerchantRepository, MerchantCriteria> {

    private final Logger log = LoggerFactory.getLogger(MerchantQueryService.class);

    public MerchantQueryService(MerchantRepository merchantRepository) {
        super(merchantRepository);
    }

    /**
     * Function to convert MerchantCriteria to a {@link Specification}
     */
    @Override
    protected Specification<Merchant> createSpecification(MerchantCriteria criteria) {
        Specification<Merchant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Merchant_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Merchant_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Merchant_.description));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Merchant_.address));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Merchant_.city));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), Merchant_.state));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), Merchant_.postalCode));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Merchant_.country));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Merchant_.phoneNumber));
            }
            if (criteria.getPhoneExt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneExt(), Merchant_.phoneExt));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Merchant_.email));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), Merchant_.website));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrency(), Merchant_.currency));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Merchant_.active));
            }
            if (criteria.getIndustryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getIndustryId(), Merchant_.industry, Industry_.id));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClientId(), Merchant_.client, Client_.id));
            }
        }
        return specification;
    }
}
