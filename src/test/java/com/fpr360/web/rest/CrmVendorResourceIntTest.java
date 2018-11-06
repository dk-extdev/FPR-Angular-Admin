package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.CrmVendor;
import com.fpr360.repository.CrmVendorRepository;
import com.fpr360.service.CrmVendorService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.dto.CrmVendorCriteria;
import com.fpr360.service.CrmVendorQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.fpr360.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fpr360.domain.enumeration.CrmApiType;
/**
 * Test class for the CrmVendorResource REST controller.
 *
 * @see CrmVendorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class CrmVendorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final CrmApiType DEFAULT_API_TYPE = CrmApiType.BASIC;
    private static final CrmApiType UPDATED_API_TYPE = CrmApiType.API_KEY;

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    @Autowired
    private CrmVendorRepository crmVendorRepository;
    
    @Autowired
    private CrmVendorService crmVendorService;

    @Autowired
    private CrmVendorQueryService crmVendorQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCrmVendorMockMvc;

    private CrmVendor crmVendor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CrmVendorResource crmVendorResource = new CrmVendorResource(crmVendorService, crmVendorQueryService);
        this.restCrmVendorMockMvc = MockMvcBuilders.standaloneSetup(crmVendorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmVendor createEntity(EntityManager em) {
        CrmVendor crmVendor = new CrmVendor()
            .name(DEFAULT_NAME)
            .apiType(DEFAULT_API_TYPE)
            .website(DEFAULT_WEBSITE);
        return crmVendor;
    }

    @Before
    public void initTest() {
        crmVendor = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmVendor() throws Exception {
        int databaseSizeBeforeCreate = crmVendorRepository.findAll().size();

        // Create the CrmVendor
        restCrmVendorMockMvc.perform(post("/api/crm-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmVendor)))
            .andExpect(status().isCreated());

        // Validate the CrmVendor in the database
        List<CrmVendor> crmVendorList = crmVendorRepository.findAll();
        assertThat(crmVendorList).hasSize(databaseSizeBeforeCreate + 1);
        CrmVendor testCrmVendor = crmVendorList.get(crmVendorList.size() - 1);
        assertThat(testCrmVendor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrmVendor.getApiType()).isEqualTo(DEFAULT_API_TYPE);
        assertThat(testCrmVendor.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
    }

    @Test
    @Transactional
    public void createCrmVendorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmVendorRepository.findAll().size();

        // Create the CrmVendor with an existing ID
        crmVendor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmVendorMockMvc.perform(post("/api/crm-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmVendor)))
            .andExpect(status().isBadRequest());

        // Validate the CrmVendor in the database
        List<CrmVendor> crmVendorList = crmVendorRepository.findAll();
        assertThat(crmVendorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmVendorRepository.findAll().size();
        // set the field null
        crmVendor.setName(null);

        // Create the CrmVendor, which fails.

        restCrmVendorMockMvc.perform(post("/api/crm-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmVendor)))
            .andExpect(status().isBadRequest());

        List<CrmVendor> crmVendorList = crmVendorRepository.findAll();
        assertThat(crmVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApiTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmVendorRepository.findAll().size();
        // set the field null
        crmVendor.setApiType(null);

        // Create the CrmVendor, which fails.

        restCrmVendorMockMvc.perform(post("/api/crm-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmVendor)))
            .andExpect(status().isBadRequest());

        List<CrmVendor> crmVendorList = crmVendorRepository.findAll();
        assertThat(crmVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWebsiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmVendorRepository.findAll().size();
        // set the field null
        crmVendor.setWebsite(null);

        // Create the CrmVendor, which fails.

        restCrmVendorMockMvc.perform(post("/api/crm-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmVendor)))
            .andExpect(status().isBadRequest());

        List<CrmVendor> crmVendorList = crmVendorRepository.findAll();
        assertThat(crmVendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCrmVendors() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get all the crmVendorList
        restCrmVendorMockMvc.perform(get("/api/crm-vendors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmVendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].apiType").value(hasItem(DEFAULT_API_TYPE.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())));
    }
    
    @Test
    @Transactional
    public void getCrmVendor() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get the crmVendor
        restCrmVendorMockMvc.perform(get("/api/crm-vendors/{id}", crmVendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmVendor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.apiType").value(DEFAULT_API_TYPE.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()));
    }

    @Test
    @Transactional
    public void getAllCrmVendorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get all the crmVendorList where name equals to DEFAULT_NAME
        defaultCrmVendorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the crmVendorList where name equals to UPDATED_NAME
        defaultCrmVendorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCrmVendorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get all the crmVendorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCrmVendorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the crmVendorList where name equals to UPDATED_NAME
        defaultCrmVendorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCrmVendorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get all the crmVendorList where name is not null
        defaultCrmVendorShouldBeFound("name.specified=true");

        // Get all the crmVendorList where name is null
        defaultCrmVendorShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCrmVendorsByApiTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get all the crmVendorList where apiType equals to DEFAULT_API_TYPE
        defaultCrmVendorShouldBeFound("apiType.equals=" + DEFAULT_API_TYPE);

        // Get all the crmVendorList where apiType equals to UPDATED_API_TYPE
        defaultCrmVendorShouldNotBeFound("apiType.equals=" + UPDATED_API_TYPE);
    }

    @Test
    @Transactional
    public void getAllCrmVendorsByApiTypeIsInShouldWork() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get all the crmVendorList where apiType in DEFAULT_API_TYPE or UPDATED_API_TYPE
        defaultCrmVendorShouldBeFound("apiType.in=" + DEFAULT_API_TYPE + "," + UPDATED_API_TYPE);

        // Get all the crmVendorList where apiType equals to UPDATED_API_TYPE
        defaultCrmVendorShouldNotBeFound("apiType.in=" + UPDATED_API_TYPE);
    }

    @Test
    @Transactional
    public void getAllCrmVendorsByApiTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get all the crmVendorList where apiType is not null
        defaultCrmVendorShouldBeFound("apiType.specified=true");

        // Get all the crmVendorList where apiType is null
        defaultCrmVendorShouldNotBeFound("apiType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCrmVendorsByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get all the crmVendorList where website equals to DEFAULT_WEBSITE
        defaultCrmVendorShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the crmVendorList where website equals to UPDATED_WEBSITE
        defaultCrmVendorShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllCrmVendorsByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get all the crmVendorList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultCrmVendorShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the crmVendorList where website equals to UPDATED_WEBSITE
        defaultCrmVendorShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllCrmVendorsByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmVendorRepository.saveAndFlush(crmVendor);

        // Get all the crmVendorList where website is not null
        defaultCrmVendorShouldBeFound("website.specified=true");

        // Get all the crmVendorList where website is null
        defaultCrmVendorShouldNotBeFound("website.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCrmVendorShouldBeFound(String filter) throws Exception {
        restCrmVendorMockMvc.perform(get("/api/crm-vendors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmVendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].apiType").value(hasItem(DEFAULT_API_TYPE.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCrmVendorShouldNotBeFound(String filter) throws Exception {
        restCrmVendorMockMvc.perform(get("/api/crm-vendors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCrmVendor() throws Exception {
        // Get the crmVendor
        restCrmVendorMockMvc.perform(get("/api/crm-vendors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmVendor() throws Exception {
        // Initialize the database
        crmVendorService.save(crmVendor);

        int databaseSizeBeforeUpdate = crmVendorRepository.findAll().size();

        // Update the crmVendor
        CrmVendor updatedCrmVendor = crmVendorRepository.findById(crmVendor.getId()).get();
        // Disconnect from session so that the updates on updatedCrmVendor are not directly saved in db
        em.detach(updatedCrmVendor);
        updatedCrmVendor
            .name(UPDATED_NAME)
            .apiType(UPDATED_API_TYPE)
            .website(UPDATED_WEBSITE);

        restCrmVendorMockMvc.perform(put("/api/crm-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCrmVendor)))
            .andExpect(status().isOk());

        // Validate the CrmVendor in the database
        List<CrmVendor> crmVendorList = crmVendorRepository.findAll();
        assertThat(crmVendorList).hasSize(databaseSizeBeforeUpdate);
        CrmVendor testCrmVendor = crmVendorList.get(crmVendorList.size() - 1);
        assertThat(testCrmVendor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrmVendor.getApiType()).isEqualTo(UPDATED_API_TYPE);
        assertThat(testCrmVendor.getWebsite()).isEqualTo(UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmVendor() throws Exception {
        int databaseSizeBeforeUpdate = crmVendorRepository.findAll().size();

        // Create the CrmVendor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrmVendorMockMvc.perform(put("/api/crm-vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmVendor)))
            .andExpect(status().isBadRequest());

        // Validate the CrmVendor in the database
        List<CrmVendor> crmVendorList = crmVendorRepository.findAll();
        assertThat(crmVendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCrmVendor() throws Exception {
        // Initialize the database
        crmVendorService.save(crmVendor);

        int databaseSizeBeforeDelete = crmVendorRepository.findAll().size();

        // Get the crmVendor
        restCrmVendorMockMvc.perform(delete("/api/crm-vendors/{id}", crmVendor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmVendor> crmVendorList = crmVendorRepository.findAll();
        assertThat(crmVendorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmVendor.class);
        CrmVendor crmVendor1 = new CrmVendor();
        crmVendor1.setId(1L);
        CrmVendor crmVendor2 = new CrmVendor();
        crmVendor2.setId(crmVendor1.getId());
        assertThat(crmVendor1).isEqualTo(crmVendor2);
        crmVendor2.setId(2L);
        assertThat(crmVendor1).isNotEqualTo(crmVendor2);
        crmVendor1.setId(null);
        assertThat(crmVendor1).isNotEqualTo(crmVendor2);
    }
}
