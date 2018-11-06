package com.fpr360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fpr360.domain.PartnerClient;
import com.fpr360.service.PartnerClientService;
import com.fpr360.web.rest.errors.BadRequestAlertException;
import com.fpr360.web.rest.util.HeaderUtil;
import com.fpr360.web.rest.util.PaginationUtil;
import com.fpr360.service.dto.PartnerClientCriteria;
import com.fpr360.service.PartnerClientQueryService;
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
 * REST controller for managing PartnerClient.
 */
@RestController
@RequestMapping("/api")
public class PartnerClientResource {

    private final Logger log = LoggerFactory.getLogger(PartnerClientResource.class);

    private static final String ENTITY_NAME = "partnerClient";

    private final PartnerClientService partnerClientService;

    private final PartnerClientQueryService partnerClientQueryService;

    public PartnerClientResource(PartnerClientService partnerClientService, PartnerClientQueryService partnerClientQueryService) {
        this.partnerClientService = partnerClientService;
        this.partnerClientQueryService = partnerClientQueryService;
    }

    /**
     * POST  /partner-clients : Create a new partnerClient.
     *
     * @param partnerClient the partnerClient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partnerClient, or with status 400 (Bad Request) if the partnerClient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/partner-clients")
    @Timed
    public ResponseEntity<PartnerClient> createPartnerClient(@Valid @RequestBody PartnerClient partnerClient) throws URISyntaxException {
        log.debug("REST request to save PartnerClient : {}", partnerClient);
        if (partnerClient.getId() != null) {
            throw new BadRequestAlertException("A new partnerClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartnerClient result = partnerClientService.save(partnerClient);
        return ResponseEntity.created(new URI("/api/partner-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partner-clients : Updates an existing partnerClient.
     *
     * @param partnerClient the partnerClient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partnerClient,
     * or with status 400 (Bad Request) if the partnerClient is not valid,
     * or with status 500 (Internal Server Error) if the partnerClient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/partner-clients")
    @Timed
    public ResponseEntity<PartnerClient> updatePartnerClient(@Valid @RequestBody PartnerClient partnerClient) throws URISyntaxException {
        log.debug("REST request to update PartnerClient : {}", partnerClient);
        if (partnerClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartnerClient result = partnerClientService.save(partnerClient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partnerClient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partner-clients : get all the partnerClients.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of partnerClients in body
     */
    @GetMapping("/partner-clients")
    @Timed
    public ResponseEntity<List<PartnerClient>> getAllPartnerClients(PartnerClientCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PartnerClients by criteria: {}", criteria);
        Page<PartnerClient> page = partnerClientQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/partner-clients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /partner-clients/:id : get the "id" partnerClient.
     *
     * @param id the id of the partnerClient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partnerClient, or with status 404 (Not Found)
     */
    @GetMapping("/partner-clients/{id}")
    @Timed
    public ResponseEntity<PartnerClient> getPartnerClient(@PathVariable Long id) {
        log.debug("REST request to get PartnerClient : {}", id);
        Optional<PartnerClient> partnerClient = partnerClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnerClient);
    }

    /**
     * DELETE  /partner-clients/:id : delete the "id" partnerClient.
     *
     * @param id the id of the partnerClient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/partner-clients/{id}")
    @Timed
    public ResponseEntity<Void> deletePartnerClient(@PathVariable Long id) {
        log.debug("REST request to delete PartnerClient : {}", id);
        partnerClientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
