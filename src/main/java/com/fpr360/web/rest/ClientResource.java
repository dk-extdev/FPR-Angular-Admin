package com.fpr360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fpr360.domain.Client;
import com.fpr360.security.AuthoritiesConstants;
import com.fpr360.service.ClientService;
import com.fpr360.web.rest.errors.BadRequestAlertException;
import com.fpr360.web.rest.util.HeaderUtil;
import com.fpr360.web.rest.util.PaginationUtil;
import com.fpr360.service.dto.ClientCriteria;
import com.fpr360.service.ClientQueryService;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Client.
 */
@RestController
@RequestMapping("/api")
public class ClientResource {

    private static final String DEFAULT_CLIENT_API_ROLES = "'" + AuthoritiesConstants.ADMIN + "'" +
            ", '" + AuthoritiesConstants.OPS_MANAGER + "'" +
            ", '" + AuthoritiesConstants.CLIENT_SERVICE_MANAGER + "'";
    private static final String LIST_CLIENTS_API_ROLES = DEFAULT_CLIENT_API_ROLES +
            ", '" + AuthoritiesConstants.PARTNER + "'";
    private static final String DETAIL_CLIENT_API_ROLES = LIST_CLIENTS_API_ROLES +
            ", '" + AuthoritiesConstants.CLIENT + "'"; // Client can see them self

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "client";

    private final ClientService clientService;

    private final ClientQueryService clientQueryService;

    public ClientResource(ClientService clientService, ClientQueryService clientQueryService) {
        this.clientService = clientService;
        this.clientQueryService = clientQueryService;
    }

    /**
     * POST  /clients : Create a new client.
     *
     * @param client the client to create
     * @return the ResponseEntity with status 201 (Created) and with body the new client, or with status 400 (Bad Request) if the client has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clients")
    @Timed
    @PreAuthorize("hasAnyRole(" + DEFAULT_CLIENT_API_ROLES + ")")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to save Client : {}", client);
        if (client.getId() != null) {
            throw new BadRequestAlertException("A new client cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Client result = clientService.save(client);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clients : Updates an existing client.
     *
     * @param client the client to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated client,
     * or with status 400 (Bad Request) if the client is not valid,
     * or with status 500 (Internal Server Error) if the client couldn't be updated
     */
    @PutMapping("/clients")
    @Timed
    @PreAuthorize("hasAnyRole(" + DEFAULT_CLIENT_API_ROLES + ")")
    public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) {
        log.debug("REST request to update Client : {}", client);
        if (client.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Client result = clientService.save(client);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, client.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clients : get all the clients.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of clients in body
     */
    @GetMapping("/clients")
    @Timed
    @PreAuthorize("hasAnyRole(" + LIST_CLIENTS_API_ROLES + ")")
    public ResponseEntity<List<Client>> getAllClients(ClientCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Clients by criteria: {}", criteria);
        Page<Client> page = clientQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clients/:id : get the "id" client.
     *
     * @param id the id of the client to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client, or with status 404 (Not Found)
     */
    @GetMapping("/clients/{id}")
    @Timed
    @PreAuthorize("hasAnyRole(" + DETAIL_CLIENT_API_ROLES + ")")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        Optional<Client> client = clientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(client);
    }

    /**
     * DELETE  /clients/:id : delete the "id" client.
     *
     * @param id the id of the client to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clients/{id}")
    @Timed
    @PreAuthorize("hasAnyRole(" + DEFAULT_CLIENT_API_ROLES + ")")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
