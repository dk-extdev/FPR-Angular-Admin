package com.fpr360.service;

import com.fpr360.domain.Industry;
import com.fpr360.repository.IndustryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Industry.
 */
@Service
@Transactional
public class IndustryService {

    private final Logger log = LoggerFactory.getLogger(IndustryService.class);

    private final IndustryRepository industryRepository;

    public IndustryService(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    /**
     * Save a industry.
     *
     * @param industry the entity to save
     * @return the persisted entity
     */
    public Industry save(Industry industry) {
        log.debug("Request to save Industry : {}", industry);
        return industryRepository.save(industry);
    }

    /**
     * Get all the industries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Industry> findAll(Pageable pageable) {
        log.debug("Request to get all Industries");
        return industryRepository.findAll(pageable);
    }


    /**
     * Get one industry by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Industry> findOne(Long id) {
        log.debug("Request to get Industry : {}", id);
        return industryRepository.findById(id);
    }

    /**
     * Delete the industry by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Industry : {}", id);
        industryRepository.deleteById(id);
    }
}
