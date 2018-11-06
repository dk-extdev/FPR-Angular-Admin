package com.fpr360.service;

import com.fpr360.domain.ClientCrm;
import com.fpr360.repository.ClientCrmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ClientCrm.
 */
@Service
@Transactional
public class ClientCrmService {

    private final Logger log = LoggerFactory.getLogger(ClientCrmService.class);

    private final ClientCrmRepository clientCrmRepository;

    public ClientCrmService(ClientCrmRepository clientCrmRepository) {
        this.clientCrmRepository = clientCrmRepository;
    }

    /**
     * Save a clientCrm.
     *
     * @param clientCrm the entity to save
     * @return the persisted entity
     */
    public ClientCrm save(ClientCrm clientCrm) {
        log.debug("Request to save ClientCrm : {}", clientCrm);
        return clientCrmRepository.save(clientCrm);
    }

    /**
     * Get all the clientCrms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClientCrm> findAll(Pageable pageable) {
        log.debug("Request to get all ClientCrms");
        return clientCrmRepository.findAll(pageable);
    }


    /**
     * Get one clientCrm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ClientCrm> findOne(Long id) {
        log.debug("Request to get ClientCrm : {}", id);
        return clientCrmRepository.findById(id);
    }

    /**
     * Delete the clientCrm by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientCrm : {}", id);
        clientCrmRepository.deleteById(id);
    }
}
