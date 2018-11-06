package com.fpr360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fpr360.domain.CrmVendor;
import com.fpr360.service.CrmVendorService;
import com.fpr360.web.rest.errors.BadRequestAlertException;
import com.fpr360.web.rest.util.HeaderUtil;
import com.fpr360.web.rest.util.PaginationUtil;
import com.fpr360.service.dto.CrmVendorCriteria;
import com.fpr360.service.CrmVendorQueryService;
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
 * REST controller for managing CrmVendor.
 */
@RestController
@RequestMapping("/api")
public class CrmVendorResource {

    private final Logger log = LoggerFactory.getLogger(CrmVendorResource.class);

    private static final String ENTITY_NAME = "crmVendor";

    private final CrmVendorService crmVendorService;

    private final CrmVendorQueryService crmVendorQueryService;

    public CrmVendorResource(CrmVendorService crmVendorService, CrmVendorQueryService crmVendorQueryService) {
        this.crmVendorService = crmVendorService;
        this.crmVendorQueryService = crmVendorQueryService;
    }

    /**
     * POST  /crm-vendors : Create a new crmVendor.
     *
     * @param crmVendor the crmVendor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new crmVendor, or with status 400 (Bad Request) if the crmVendor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crm-vendors")
    @Timed
    public ResponseEntity<CrmVendor> createCrmVendor(@Valid @RequestBody CrmVendor crmVendor) throws URISyntaxException {
        log.debug("REST request to save CrmVendor : {}", crmVendor);
        if (crmVendor.getId() != null) {
            throw new BadRequestAlertException("A new crmVendor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CrmVendor result = crmVendorService.save(crmVendor);
        return ResponseEntity.created(new URI("/api/crm-vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crm-vendors : Updates an existing crmVendor.
     *
     * @param crmVendor the crmVendor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated crmVendor,
     * or with status 400 (Bad Request) if the crmVendor is not valid,
     * or with status 500 (Internal Server Error) if the crmVendor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crm-vendors")
    @Timed
    public ResponseEntity<CrmVendor> updateCrmVendor(@Valid @RequestBody CrmVendor crmVendor) throws URISyntaxException {
        log.debug("REST request to update CrmVendor : {}", crmVendor);
        if (crmVendor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CrmVendor result = crmVendorService.save(crmVendor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, crmVendor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crm-vendors : get all the crmVendors.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of crmVendors in body
     */
    @GetMapping("/crm-vendors")
    @Timed
    public ResponseEntity<List<CrmVendor>> getAllCrmVendors(CrmVendorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CrmVendors by criteria: {}", criteria);
        Page<CrmVendor> page = crmVendorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/crm-vendors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /crm-vendors/:id : get the "id" crmVendor.
     *
     * @param id the id of the crmVendor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the crmVendor, or with status 404 (Not Found)
     */
    @GetMapping("/crm-vendors/{id}")
    @Timed
    public ResponseEntity<CrmVendor> getCrmVendor(@PathVariable Long id) {
        log.debug("REST request to get CrmVendor : {}", id);
        Optional<CrmVendor> crmVendor = crmVendorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(crmVendor);
    }

    /**
     * DELETE  /crm-vendors/:id : delete the "id" crmVendor.
     *
     * @param id the id of the crmVendor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crm-vendors/{id}")
    @Timed
    public ResponseEntity<Void> deleteCrmVendor(@PathVariable Long id) {
        log.debug("REST request to delete CrmVendor : {}", id);
        crmVendorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
