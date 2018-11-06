package com.fpr360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fpr360.domain.ServiceProvider;
import com.fpr360.service.ServiceProviderService;
import com.fpr360.web.rest.errors.BadRequestAlertException;
import com.fpr360.web.rest.util.HeaderUtil;
import com.fpr360.web.rest.util.PaginationUtil;
import com.fpr360.service.dto.ServiceProviderCriteria;
import com.fpr360.service.ServiceProviderQueryService;
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
 * REST controller for managing ServiceProvider.
 */
@RestController
@RequestMapping("/api")
public class ServiceProviderResource {

    private final Logger log = LoggerFactory.getLogger(ServiceProviderResource.class);

    private static final String ENTITY_NAME = "serviceProvider";

    private final ServiceProviderService serviceProviderService;

    private final ServiceProviderQueryService serviceProviderQueryService;

    public ServiceProviderResource(ServiceProviderService serviceProviderService, ServiceProviderQueryService serviceProviderQueryService) {
        this.serviceProviderService = serviceProviderService;
        this.serviceProviderQueryService = serviceProviderQueryService;
    }

    /**
     * POST  /service-providers : Create a new serviceProvider.
     *
     * @param serviceProvider the serviceProvider to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceProvider, or with status 400 (Bad Request) if the serviceProvider has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-providers")
    @Timed
    public ResponseEntity<ServiceProvider> createServiceProvider(@Valid @RequestBody ServiceProvider serviceProvider) throws URISyntaxException {
        log.debug("REST request to save ServiceProvider : {}", serviceProvider);
        if (serviceProvider.getId() != null) {
            throw new BadRequestAlertException("A new serviceProvider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceProvider result = serviceProviderService.save(serviceProvider);
        return ResponseEntity.created(new URI("/api/service-providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-providers : Updates an existing serviceProvider.
     *
     * @param serviceProvider the serviceProvider to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceProvider,
     * or with status 400 (Bad Request) if the serviceProvider is not valid,
     * or with status 500 (Internal Server Error) if the serviceProvider couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-providers")
    @Timed
    public ResponseEntity<ServiceProvider> updateServiceProvider(@Valid @RequestBody ServiceProvider serviceProvider) throws URISyntaxException {
        log.debug("REST request to update ServiceProvider : {}", serviceProvider);
        if (serviceProvider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceProvider result = serviceProviderService.save(serviceProvider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceProvider.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-providers : get all the serviceProviders.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of serviceProviders in body
     */
    @GetMapping("/service-providers")
    @Timed
    public ResponseEntity<List<ServiceProvider>> getAllServiceProviders(ServiceProviderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceProviders by criteria: {}", criteria);
        Page<ServiceProvider> page = serviceProviderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service-providers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /service-providers/:id : get the "id" serviceProvider.
     *
     * @param id the id of the serviceProvider to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceProvider, or with status 404 (Not Found)
     */
    @GetMapping("/service-providers/{id}")
    @Timed
    public ResponseEntity<ServiceProvider> getServiceProvider(@PathVariable Long id) {
        log.debug("REST request to get ServiceProvider : {}", id);
        Optional<ServiceProvider> serviceProvider = serviceProviderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceProvider);
    }

    /**
     * DELETE  /service-providers/:id : delete the "id" serviceProvider.
     *
     * @param id the id of the serviceProvider to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-providers/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable Long id) {
        log.debug("REST request to delete ServiceProvider : {}", id);
        serviceProviderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
