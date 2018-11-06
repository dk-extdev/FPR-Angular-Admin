package com.fpr360.service;

import com.fpr360.domain.CrmVendor;
import com.fpr360.repository.CrmVendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CrmVendor.
 */
@Service
@Transactional
public class CrmVendorService {

    private final Logger log = LoggerFactory.getLogger(CrmVendorService.class);

    private final CrmVendorRepository crmVendorRepository;

    public CrmVendorService(CrmVendorRepository crmVendorRepository) {
        this.crmVendorRepository = crmVendorRepository;
    }

    /**
     * Save a crmVendor.
     *
     * @param crmVendor the entity to save
     * @return the persisted entity
     */
    public CrmVendor save(CrmVendor crmVendor) {
        log.debug("Request to save CrmVendor : {}", crmVendor);
        return crmVendorRepository.save(crmVendor);
    }

    /**
     * Get all the crmVendors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CrmVendor> findAll(Pageable pageable) {
        log.debug("Request to get all CrmVendors");
        return crmVendorRepository.findAll(pageable);
    }


    /**
     * Get one crmVendor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CrmVendor> findOne(Long id) {
        log.debug("Request to get CrmVendor : {}", id);
        return crmVendorRepository.findById(id);
    }

    /**
     * Delete the crmVendor by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CrmVendor : {}", id);
        crmVendorRepository.deleteById(id);
    }
}
