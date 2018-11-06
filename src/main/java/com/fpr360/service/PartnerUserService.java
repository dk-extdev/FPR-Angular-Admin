package com.fpr360.service;

import com.fpr360.domain.PartnerUser;
import com.fpr360.repository.PartnerUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PartnerUser.
 */
@Service
@Transactional
public class PartnerUserService {

    private final Logger log = LoggerFactory.getLogger(PartnerUserService.class);

    private final PartnerUserRepository partnerUserRepository;

    public PartnerUserService(PartnerUserRepository partnerUserRepository) {
        this.partnerUserRepository = partnerUserRepository;
    }

    /**
     * Save a partnerUser.
     *
     * @param partnerUser the entity to save
     * @return the persisted entity
     */
    public PartnerUser save(PartnerUser partnerUser) {
        log.debug("Request to save PartnerUser : {}", partnerUser);
        return partnerUserRepository.save(partnerUser);
    }

    /**
     * Get all the partnerUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PartnerUser> findAll(Pageable pageable) {
        log.debug("Request to get all PartnerUsers");
        return partnerUserRepository.findAll(pageable);
    }


    /**
     * Get one partnerUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PartnerUser> findOne(Long id) {
        log.debug("Request to get PartnerUser : {}", id);
        return partnerUserRepository.findById(id);
    }

    /**
     * Delete the partnerUser by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PartnerUser : {}", id);
        partnerUserRepository.deleteById(id);
    }
}
