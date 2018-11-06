package com.fpr360.service;

import com.fpr360.domain.PartnerClient;
import com.fpr360.repository.PartnerClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PartnerClient.
 */
@Service
@Transactional
public class PartnerClientService {

    private final Logger log = LoggerFactory.getLogger(PartnerClientService.class);

    private final PartnerClientRepository partnerClientRepository;

    public PartnerClientService(PartnerClientRepository partnerClientRepository) {
        this.partnerClientRepository = partnerClientRepository;
    }

    /**
     * Save a partnerClient.
     *
     * @param partnerClient the entity to save
     * @return the persisted entity
     */
    public PartnerClient save(PartnerClient partnerClient) {
        log.debug("Request to save PartnerClient : {}", partnerClient);
        return partnerClientRepository.save(partnerClient);
    }

    /**
     * Get all the partnerClients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PartnerClient> findAll(Pageable pageable) {
        log.debug("Request to get all PartnerClients");
        return partnerClientRepository.findAll(pageable);
    }


    /**
     * Get one partnerClient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PartnerClient> findOne(Long id) {
        log.debug("Request to get PartnerClient : {}", id);
        return partnerClientRepository.findById(id);
    }

    /**
     * Delete the partnerClient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PartnerClient : {}", id);
        partnerClientRepository.deleteById(id);
    }
}
