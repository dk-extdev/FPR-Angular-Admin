package com.fpr360.service;

import com.fpr360.domain.MerchantAccount;
import com.fpr360.repository.MerchantAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MerchantAccount.
 */
@Service
@Transactional
public class MerchantAccountService {

    private final Logger log = LoggerFactory.getLogger(MerchantAccountService.class);

    private final MerchantAccountRepository merchantAccountRepository;

    public MerchantAccountService(MerchantAccountRepository merchantAccountRepository) {
        this.merchantAccountRepository = merchantAccountRepository;
    }

    /**
     * Save a merchantAccount.
     *
     * @param merchantAccount the entity to save
     * @return the persisted entity
     */
    public MerchantAccount save(MerchantAccount merchantAccount) {
        log.debug("Request to save MerchantAccount : {}", merchantAccount);        return merchantAccountRepository.save(merchantAccount);
    }

    /**
     * Get all the merchantAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MerchantAccount> findAll(Pageable pageable) {
        log.debug("Request to get all MerchantAccounts");
        return merchantAccountRepository.findAll(pageable);
    }


    /**
     * Get one merchantAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MerchantAccount> findOne(Long id) {
        log.debug("Request to get MerchantAccount : {}", id);
        return merchantAccountRepository.findById(id);
    }

    /**
     * Delete the merchantAccount by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MerchantAccount : {}", id);
        merchantAccountRepository.deleteById(id);
    }
}
