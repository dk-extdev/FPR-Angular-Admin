package com.fpr360.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fpr360.domain.ServiceProvider;
import com.fpr360.domain.*; // for static metamodels
import com.fpr360.repository.ServiceProviderRepository;
import com.fpr360.service.dto.ServiceProviderCriteria;


@Service
@Transactional(readOnly = true)
public class ServiceProviderQueryService extends OrganizationQueryService<ServiceProvider, ServiceProviderRepository, ServiceProviderCriteria> {

    public ServiceProviderQueryService(ServiceProviderRepository serviceProviderRepository) {
        super(serviceProviderRepository);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Specification<ServiceProvider> createSpecification(ServiceProviderCriteria criteria) {
        Specification<ServiceProvider> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceProvider_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ServiceProvider_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ServiceProvider_.description));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), ServiceProvider_.address));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), ServiceProvider_.city));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), ServiceProvider_.state));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), ServiceProvider_.postalCode));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), ServiceProvider_.country));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), ServiceProvider_.phoneNumber));
            }
            if (criteria.getPhoneExt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneExt(), ServiceProvider_.phoneExt));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ServiceProvider_.email));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), ServiceProvider_.website));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrency(), ServiceProvider_.currency));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ServiceProvider_.active));
            }
            if (criteria.getIndustryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getIndustryId(), ServiceProvider_.industry, Industry_.id));
            }
        }
        return specification;
    }
}
