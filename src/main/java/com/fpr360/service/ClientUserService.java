package com.fpr360.service;

import com.fpr360.domain.ClientUser;
import com.fpr360.repository.ClientUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ClientUser.
 */
@Service
@Transactional
public class ClientUserService {

    private final Logger log = LoggerFactory.getLogger(ClientUserService.class);

    private final ClientUserRepository clientUserRepository;

    public ClientUserService(ClientUserRepository clientUserRepository) {
        this.clientUserRepository = clientUserRepository;
    }

    /**
     * Save a clientUser.
     *
     * @param clientUser the entity to save
     * @return the persisted entity
     */
    public ClientUser save(ClientUser clientUser) {
        log.debug("Request to save ClientUser : {}", clientUser);
        return clientUserRepository.save(clientUser);
    }

    /**
     * Get all the clientUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClientUser> findAll(Pageable pageable) {
        log.debug("Request to get all ClientUsers");
        return clientUserRepository.findAll(pageable);
    }


    /**
     * Get one clientUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ClientUser> findOne(Long id) {
        log.debug("Request to get ClientUser : {}", id);
        return clientUserRepository.findById(id);
    }

    /**
     * Delete the clientUser by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientUser : {}", id);
        clientUserRepository.deleteById(id);
    }
}
