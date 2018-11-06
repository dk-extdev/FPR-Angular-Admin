package com.fpr360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fpr360.domain.MerchantAccount;
import com.fpr360.service.MerchantAccountService;
import com.fpr360.web.rest.errors.BadRequestAlertException;
import com.fpr360.web.rest.util.HeaderUtil;
import com.fpr360.web.rest.util.PaginationUtil;
import com.fpr360.service.dto.MerchantAccountCriteria;
import com.fpr360.service.MerchantAccountQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MerchantAccount.
 */
@RestController
@RequestMapping("/api")
public class MerchantAccountResource {

    private final Logger log = LoggerFactory.getLogger(MerchantAccountResource.class);

    private static final String ENTITY_NAME = "merchantAccount";

    private final MerchantAccountService merchantAccountService;

    private final MerchantAccountQueryService merchantAccountQueryService;

    public MerchantAccountResource(MerchantAccountService merchantAccountService, MerchantAccountQueryService merchantAccountQueryService) {
        this.merchantAccountService = merchantAccountService;
        this.merchantAccountQueryService = merchantAccountQueryService;
    }

    /**
     * POST  /merchant-accounts : Create a new merchantAccount.
     *
     * @param merchantAccount the merchantAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new merchantAccount, or with status 400 (Bad Request) if the merchantAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/merchant-accounts")
    @Timed
    public ResponseEntity<MerchantAccount> createMerchantAccount(@Valid @RequestBody MerchantAccount merchantAccount) throws URISyntaxException {
        log.debug("REST request to save MerchantAccount : {}", merchantAccount);
        if (merchantAccount.getId() != null) {
            throw new BadRequestAlertException("A new merchantAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MerchantAccount result = merchantAccountService.save(merchantAccount);
        return ResponseEntity.created(new URI("/api/merchant-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /merchant-accounts : Updates an existing merchantAccount.
     *
     * @param merchantAccount the merchantAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated merchantAccount,
     * or with status 400 (Bad Request) if the merchantAccount is not valid,
     * or with status 500 (Internal Server Error) if the merchantAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/merchant-accounts")
    @Timed
    public ResponseEntity<MerchantAccount> updateMerchantAccount(@Valid @RequestBody MerchantAccount merchantAccount) throws URISyntaxException {
        log.debug("REST request to update MerchantAccount : {}", merchantAccount);
        if (merchantAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MerchantAccount result = merchantAccountService.save(merchantAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, merchantAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /merchant-accounts : get all the merchantAccounts.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of merchantAccounts in body
     */
    @GetMapping("/merchant-accounts")
    @Timed
    public ResponseEntity<List<MerchantAccount>> getAllMerchantAccounts(MerchantAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MerchantAccounts by criteria: {}", criteria);
        Page<MerchantAccount> page = merchantAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/merchant-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /merchant-accounts/:id : get the "id" merchantAccount.
     *
     * @param id the id of the merchantAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the merchantAccount, or with status 404 (Not Found)
     */
    @GetMapping("/merchant-accounts/{id}")
    @Timed
    public ResponseEntity<MerchantAccount> getMerchantAccount(@PathVariable Long id) {
        log.debug("REST request to get MerchantAccount : {}", id);
        Optional<MerchantAccount> merchantAccount = merchantAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(merchantAccount);
    }

    /**
     * DELETE  /merchant-accounts/:id : delete the "id" merchantAccount.
     *
     * @param id the id of the merchantAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/merchant-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMerchantAccount(@PathVariable Long id) {
        log.debug("REST request to delete MerchantAccount : {}", id);
        merchantAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
