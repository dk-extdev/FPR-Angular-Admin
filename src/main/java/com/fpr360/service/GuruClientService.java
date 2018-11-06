package com.fpr360.service;

import com.fpr360.domain.GuruClient;
import com.fpr360.domain.enumeration.GuruClientLevel;
import com.fpr360.repository.GuruClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing GuruClient.
 */
@Service
@Transactional
public class GuruClientService {

    private final Logger log = LoggerFactory.getLogger(GuruClientService.class);

    private final GuruClientRepository guruClientRepository;

    public GuruClientService(GuruClientRepository guruClientRepository) {
        this.guruClientRepository = guruClientRepository;
    }

    /**
     * Save a guruClient.
     *
     * @param guruClient the entity to save
     * @return the persisted entity
     */
    public GuruClient save(GuruClient guruClient) {
        log.debug("Request to save GuruClient : {}", guruClient);
        return guruClientRepository.save(guruClient);
    }

    /**
     * Save list of guruClient.
     *
     * @param guruClients the entity to save
     * @return the persisted entity
     */
    public List<GuruClient> saveAll(List<GuruClient> guruClients) {
        log.debug("Request to save GuruClient : {}", guruClients);
        guruClients.stream().forEach(guruClient -> {
            guruClient.setLevel(GuruClientLevel.PRIMARY);
        });
        return guruClientRepository.saveAll(guruClients);
    }

    /**
     * Get all the guruClients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GuruClient> findAll(Pageable pageable) {
        log.debug("Request to get all GuruClients");
        return guruClientRepository.findAll(pageable);
    }


    /**
     * Get one guruClient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GuruClient> findOne(Long id) {
        log.debug("Request to get GuruClient : {}", id);
        return guruClientRepository.findById(id);
    }

    /**
     * Delete the guruClient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GuruClient : {}", id);
        guruClientRepository.deleteById(id);
    }
}
