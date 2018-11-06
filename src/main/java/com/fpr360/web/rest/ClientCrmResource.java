package com.fpr360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fpr360.domain.ClientCrm;
import com.fpr360.service.ClientCrmService;
import com.fpr360.web.rest.errors.BadRequestAlertException;
import com.fpr360.web.rest.util.HeaderUtil;
import com.fpr360.web.rest.util.PaginationUtil;
import com.fpr360.service.dto.ClientCrmCriteria;
import com.fpr360.service.ClientCrmQueryService;
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
 * REST controller for managing ClientCrm.
 */
@RestController
@RequestMapping("/api")
public class ClientCrmResource {

    private final Logger log = LoggerFactory.getLogger(ClientCrmResource.class);

    private static final String ENTITY_NAME = "clientCrm";

    private final ClientCrmService clientCrmService;

    private final ClientCrmQueryService clientCrmQueryService;

    public ClientCrmResource(ClientCrmService clientCrmService, ClientCrmQueryService clientCrmQueryService) {
        this.clientCrmService = clientCrmService;
        this.clientCrmQueryService = clientCrmQueryService;
    }

    /**
     * POST  /client-crms : Create a new clientCrm.
     *
     * @param clientCrm the clientCrm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientCrm, or with status 400 (Bad Request) if the clientCrm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-crms")
    @Timed
    public ResponseEntity<ClientCrm> createClientCrm(@Valid @RequestBody ClientCrm clientCrm) throws URISyntaxException {
        log.debug("REST request to save ClientCrm : {}", clientCrm);
        if (clientCrm.getId() != null) {
            throw new BadRequestAlertException("A new clientCrm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientCrm result = clientCrmService.save(clientCrm);
        return ResponseEntity.created(new URI("/api/client-crms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-crms : Updates an existing clientCrm.
     *
     * @param clientCrm the clientCrm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientCrm,
     * or with status 400 (Bad Request) if the clientCrm is not valid,
     * or with status 500 (Internal Server Error) if the clientCrm couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-crms")
    @Timed
    public ResponseEntity<ClientCrm> updateClientCrm(@Valid @RequestBody ClientCrm clientCrm) throws URISyntaxException {
        log.debug("REST request to update ClientCrm : {}", clientCrm);
        if (clientCrm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientCrm result = clientCrmService.save(clientCrm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientCrm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-crms : get all the clientCrms.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of clientCrms in body
     */
    @GetMapping("/client-crms")
    @Timed
    public ResponseEntity<List<ClientCrm>> getAllClientCrms(ClientCrmCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClientCrms by criteria: {}", criteria);
        Page<ClientCrm> page = clientCrmQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-crms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-crms/:id : get the "id" clientCrm.
     *
     * @param id the id of the clientCrm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientCrm, or with status 404 (Not Found)
     */
    @GetMapping("/client-crms/{id}")
    @Timed
    public ResponseEntity<ClientCrm> getClientCrm(@PathVariable Long id) {
        log.debug("REST request to get ClientCrm : {}", id);
        Optional<ClientCrm> clientCrm = clientCrmService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientCrm);
    }

    /**
     * DELETE  /client-crms/:id : delete the "id" clientCrm.
     *
     * @param id the id of the clientCrm to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-crms/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientCrm(@PathVariable Long id) {
        log.debug("REST request to delete ClientCrm : {}", id);
        clientCrmService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
