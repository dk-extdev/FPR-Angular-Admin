package com.fpr360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fpr360.domain.GuruClient;
import com.fpr360.security.AuthoritiesConstants;
import com.fpr360.service.GuruClientService;
import com.fpr360.web.rest.errors.BadRequestAlertException;
import com.fpr360.web.rest.util.HeaderUtil;
import com.fpr360.web.rest.util.PaginationUtil;
import com.fpr360.service.dto.GuruClientCriteria;
import com.fpr360.service.GuruClientQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GuruClient.
 */
@RestController
@RequestMapping("/api")
public class GuruClientResource {

    private static final String DEFAULT_API_ROLES = "'" + AuthoritiesConstants.ADMIN + "'" +
            ", '" + AuthoritiesConstants.OPS_MANAGER + "'" +
            ", '" + AuthoritiesConstants.CLIENT_SERVICE_MANAGER + "'";

    private final Logger log = LoggerFactory.getLogger(GuruClientResource.class);

    private static final String ENTITY_NAME = "guruClient";

    private final GuruClientService guruClientService;

    private final GuruClientQueryService guruClientQueryService;

    public GuruClientResource(GuruClientService guruClientService, GuruClientQueryService guruClientQueryService) {
        this.guruClientService = guruClientService;
        this.guruClientQueryService = guruClientQueryService;
    }

    /**
     * POST  /guru-clients : Create a new guruClient.
     *
     * @param guruClient the guruClient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new guruClient, or with status 400 (Bad Request) if the guruClient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/guru-clients")
    @Timed
    @PreAuthorize("hasAnyRole(" + DEFAULT_API_ROLES + ")")
    public ResponseEntity<GuruClient> createGuruClient(@Valid @RequestBody GuruClient guruClient) throws URISyntaxException {
        log.debug("REST request to save GuruClient : {}", guruClient);
        if (guruClient.getId() != null) {
            throw new BadRequestAlertException("A new guruClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuruClient result = guruClientService.save(guruClient);
        return ResponseEntity.created(new URI("/api/guru-clients/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * POST  /guru-clients/bulk-insert : Create a new guruClient in bulk (more than one data) mode.
     *
     * @param guruClients list of guruClient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new guruClient in array (list), or with
     *         status 400 (Bad Request) if the guruClient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/guru-clients/bulk-insert")
    @Timed
    @PreAuthorize("hasAnyRole(" + DEFAULT_API_ROLES + ")")
    public ResponseEntity<List<GuruClient>> createGuruClientBulk(@Valid @RequestBody List<GuruClient> guruClients) throws URISyntaxException {
        log.debug("REST request to save GuruClient in bulk-insert mode: {}", guruClients);

        guruClients.stream().forEach(guruClient -> {
            if (guruClient.getId() != null) {
                throw new BadRequestAlertException("'/guru-clients/bulk-insert': A new guruClient cannot already have an ID", ENTITY_NAME, "idexists");
            }
        });
        List<GuruClient> result = guruClientService.saveAll(guruClients);

        return ResponseEntity.created(new URI("/api/guru-clients/bulk-insert"))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.size() + " entity created"))
                .body(result);
    }

    /**
     * PUT  /guru-clients : Updates an existing guruClient.
     *
     * @param guruClient the guruClient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated guruClient,
     * or with status 400 (Bad Request) if the guruClient is not valid,
     * or with status 500 (Internal Server Error) if the guruClient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/guru-clients")
    @Timed
    @PreAuthorize("hasAnyRole(" + DEFAULT_API_ROLES + ")")
    public ResponseEntity<GuruClient> updateGuruClient(@Valid @RequestBody GuruClient guruClient) throws URISyntaxException {
        log.debug("REST request to update GuruClient : {}", guruClient);
        if (guruClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GuruClient result = guruClientService.save(guruClient);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, guruClient.getId().toString()))
                .body(result);
    }

    /**
     * GET  /guru-clients : get all the guruClients.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of guruClients in body
     */
    @GetMapping("/guru-clients")
    @Timed
    @PreAuthorize("hasAnyRole(" + DEFAULT_API_ROLES + ")")
    public ResponseEntity<List<GuruClient>> getAllGuruClients(GuruClientCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GuruClients by criteria: {}", criteria);
        Page<GuruClient> page = guruClientQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/guru-clients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /guru-clients/:id : get the "id" guruClient.
     *
     * @param id the id of the guruClient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the guruClient, or with status 404 (Not Found)
     */
    @GetMapping("/guru-clients/{id}")
    @Timed
    @PreAuthorize("hasAnyRole(" + DEFAULT_API_ROLES + ")")
    public ResponseEntity<GuruClient> getGuruClient(@PathVariable Long id) {
        log.debug("REST request to get GuruClient : {}", id);
        Optional<GuruClient> guruClient = guruClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guruClient);
    }

    /**
     * DELETE  /guru-clients/:id : delete the "id" guruClient.
     *
     * @param id the id of the guruClient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/guru-clients/{id}")
    @Timed
    @PreAuthorize("hasAnyRole(" + DEFAULT_API_ROLES + ")")
    public ResponseEntity<Void> deleteGuruClient(@PathVariable Long id) {
        log.debug("REST request to delete GuruClient : {}", id);
        guruClientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
