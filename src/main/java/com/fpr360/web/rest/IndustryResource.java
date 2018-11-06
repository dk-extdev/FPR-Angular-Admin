package com.fpr360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fpr360.domain.Industry;
import com.fpr360.service.IndustryService;
import com.fpr360.web.rest.errors.BadRequestAlertException;
import com.fpr360.web.rest.util.HeaderUtil;
import com.fpr360.web.rest.util.PaginationUtil;
import com.fpr360.service.dto.IndustryCriteria;
import com.fpr360.service.IndustryQueryService;
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
 * REST controller for managing Industry.
 */
@RestController
@RequestMapping("/api")
public class IndustryResource {

    private final Logger log = LoggerFactory.getLogger(IndustryResource.class);

    private static final String ENTITY_NAME = "industry";

    private final IndustryService industryService;

    private final IndustryQueryService industryQueryService;

    public IndustryResource(IndustryService industryService, IndustryQueryService industryQueryService) {
        this.industryService = industryService;
        this.industryQueryService = industryQueryService;
    }

    /**
     * POST  /industries : Create a new industry.
     *
     * @param industry the industry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industry, or with status 400 (Bad Request) if the industry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/industries")
    @Timed
    public ResponseEntity<Industry> createIndustry(@Valid @RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to save Industry : {}", industry);
        if (industry.getId() != null) {
            throw new BadRequestAlertException("A new industry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Industry result = industryService.save(industry);
        return ResponseEntity.created(new URI("/api/industries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /industries : Updates an existing industry.
     *
     * @param industry the industry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industry,
     * or with status 400 (Bad Request) if the industry is not valid,
     * or with status 500 (Internal Server Error) if the industry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/industries")
    @Timed
    public ResponseEntity<Industry> updateIndustry(@Valid @RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to update Industry : {}", industry);
        if (industry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Industry result = industryService.save(industry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, industry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industries : get all the industries.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of industries in body
     */
    @GetMapping("/industries")
    @Timed
    public ResponseEntity<List<Industry>> getAllIndustries(IndustryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Industries by criteria: {}", criteria);
        Page<Industry> page = industryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/industries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /industries/:id : get the "id" industry.
     *
     * @param id the id of the industry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industry, or with status 404 (Not Found)
     */
    @GetMapping("/industries/{id}")
    @Timed
    public ResponseEntity<Industry> getIndustry(@PathVariable Long id) {
        log.debug("REST request to get Industry : {}", id);
        Optional<Industry> industry = industryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(industry);
    }

    /**
     * DELETE  /industries/:id : delete the "id" industry.
     *
     * @param id the id of the industry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/industries/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        log.debug("REST request to delete Industry : {}", id);
        industryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
