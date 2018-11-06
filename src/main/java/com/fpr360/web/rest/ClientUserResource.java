package com.fpr360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fpr360.domain.ClientUser;
import com.fpr360.service.ClientUserService;
import com.fpr360.web.rest.errors.BadRequestAlertException;
import com.fpr360.web.rest.util.HeaderUtil;
import com.fpr360.web.rest.util.PaginationUtil;
import com.fpr360.service.dto.ClientUserCriteria;
import com.fpr360.service.ClientUserQueryService;
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
 * REST controller for managing ClientUser.
 */
@RestController
@RequestMapping("/api")
public class ClientUserResource {

    private final Logger log = LoggerFactory.getLogger(ClientUserResource.class);

    private static final String ENTITY_NAME = "clientUser";

    private final ClientUserService clientUserService;

    private final ClientUserQueryService clientUserQueryService;

    public ClientUserResource(ClientUserService clientUserService, ClientUserQueryService clientUserQueryService) {
        this.clientUserService = clientUserService;
        this.clientUserQueryService = clientUserQueryService;
    }

    /**
     * POST  /client-users : Create a new clientUser.
     *
     * @param clientUser the clientUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientUser, or with status 400 (Bad Request) if the clientUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-users")
    @Timed
    public ResponseEntity<ClientUser> createClientUser(@Valid @RequestBody ClientUser clientUser) throws URISyntaxException {
        log.debug("REST request to save ClientUser : {}", clientUser);
        if (clientUser.getId() != null) {
            throw new BadRequestAlertException("A new clientUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientUser result = clientUserService.save(clientUser);
        return ResponseEntity.created(new URI("/api/client-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-users : Updates an existing clientUser.
     *
     * @param clientUser the clientUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientUser,
     * or with status 400 (Bad Request) if the clientUser is not valid,
     * or with status 500 (Internal Server Error) if the clientUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-users")
    @Timed
    public ResponseEntity<ClientUser> updateClientUser(@Valid @RequestBody ClientUser clientUser) throws URISyntaxException {
        log.debug("REST request to update ClientUser : {}", clientUser);
        if (clientUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientUser result = clientUserService.save(clientUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-users : get all the clientUsers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of clientUsers in body
     */
    @GetMapping("/client-users")
    @Timed
    public ResponseEntity<List<ClientUser>> getAllClientUsers(ClientUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClientUsers by criteria: {}", criteria);
        Page<ClientUser> page = clientUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-users/:id : get the "id" clientUser.
     *
     * @param id the id of the clientUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientUser, or with status 404 (Not Found)
     */
    @GetMapping("/client-users/{id}")
    @Timed
    public ResponseEntity<ClientUser> getClientUser(@PathVariable Long id) {
        log.debug("REST request to get ClientUser : {}", id);
        Optional<ClientUser> clientUser = clientUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientUser);
    }

    /**
     * DELETE  /client-users/:id : delete the "id" clientUser.
     *
     * @param id the id of the clientUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientUser(@PathVariable Long id) {
        log.debug("REST request to delete ClientUser : {}", id);
        clientUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
