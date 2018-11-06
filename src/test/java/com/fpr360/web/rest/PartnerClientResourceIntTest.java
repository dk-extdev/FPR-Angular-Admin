package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.PartnerClient;
import com.fpr360.domain.Partner;
import com.fpr360.domain.MerchantAccount;
import com.fpr360.repository.PartnerClientRepository;
import com.fpr360.service.PartnerClientService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.dto.PartnerClientCriteria;
import com.fpr360.service.PartnerClientQueryService;

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

/**
 * Test class for the PartnerClientResource REST controller.
 *
 * @see PartnerClientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class PartnerClientResourceIntTest {

    @Autowired
    private PartnerClientRepository partnerClientRepository;
    
    @Autowired
    private PartnerClientService partnerClientService;

    @Autowired
    private PartnerClientQueryService partnerClientQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartnerClientMockMvc;

    private PartnerClient partnerClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerClientResource partnerClientResource = new PartnerClientResource(partnerClientService, partnerClientQueryService);
        this.restPartnerClientMockMvc = MockMvcBuilders.standaloneSetup(partnerClientResource)
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
    public static PartnerClient createEntity(EntityManager em) {
        PartnerClient partnerClient = new PartnerClient();
        // Add required entity
        Partner partner = PartnerResourceIntTest.createEntity(em);
        em.persist(partner);
        em.flush();
        partnerClient.setPartner(partner);
        // Add required entity
        MerchantAccount merchantAccount = MerchantAccountResourceIntTest.createEntity(em);
        em.persist(merchantAccount);
        em.flush();
        partnerClient.setMerchantAccount(merchantAccount);
        return partnerClient;
    }

    @Before
    public void initTest() {
        partnerClient = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartnerClient() throws Exception {
        int databaseSizeBeforeCreate = partnerClientRepository.findAll().size();

        // Create the PartnerClient
        restPartnerClientMockMvc.perform(post("/api/partner-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerClient)))
            .andExpect(status().isCreated());

        // Validate the PartnerClient in the database
        List<PartnerClient> partnerClientList = partnerClientRepository.findAll();
        assertThat(partnerClientList).hasSize(databaseSizeBeforeCreate + 1);
        PartnerClient testPartnerClient = partnerClientList.get(partnerClientList.size() - 1);
    }

    @Test
    @Transactional
    public void createPartnerClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerClientRepository.findAll().size();

        // Create the PartnerClient with an existing ID
        partnerClient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerClientMockMvc.perform(post("/api/partner-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerClient)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerClient in the database
        List<PartnerClient> partnerClientList = partnerClientRepository.findAll();
        assertThat(partnerClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPartnerClients() throws Exception {
        // Initialize the database
        partnerClientRepository.saveAndFlush(partnerClient);

        // Get all the partnerClientList
        restPartnerClientMockMvc.perform(get("/api/partner-clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerClient.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPartnerClient() throws Exception {
        // Initialize the database
        partnerClientRepository.saveAndFlush(partnerClient);

        // Get the partnerClient
        restPartnerClientMockMvc.perform(get("/api/partner-clients/{id}", partnerClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partnerClient.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllPartnerClientsByPartnerIsEqualToSomething() throws Exception {
        // Initialize the database
        Partner partner = PartnerResourceIntTest.createEntity(em);
        em.persist(partner);
        em.flush();
        partnerClient.setPartner(partner);
        partnerClientRepository.saveAndFlush(partnerClient);
        Long partnerId = partner.getId();

        // Get all the partnerClientList where partner equals to partnerId
        defaultPartnerClientShouldBeFound("partnerId.equals=" + partnerId);

        // Get all the partnerClientList where partner equals to partnerId + 1
        defaultPartnerClientShouldNotBeFound("partnerId.equals=" + (partnerId + 1));
    }


    @Test
    @Transactional
    public void getAllPartnerClientsByMerchantAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        MerchantAccount merchantAccount = MerchantAccountResourceIntTest.createEntity(em);
        em.persist(merchantAccount);
        em.flush();
        partnerClient.setMerchantAccount(merchantAccount);
        partnerClientRepository.saveAndFlush(partnerClient);
        Long merchantAccountId = merchantAccount.getId();

        // Get all the partnerClientList where merchantAccount equals to merchantAccountId
        defaultPartnerClientShouldBeFound("merchantAccountId.equals=" + merchantAccountId);

        // Get all the partnerClientList where merchantAccount equals to merchantAccountId + 1
        defaultPartnerClientShouldNotBeFound("merchantAccountId.equals=" + (merchantAccountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPartnerClientShouldBeFound(String filter) throws Exception {
        restPartnerClientMockMvc.perform(get("/api/partner-clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerClient.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPartnerClientShouldNotBeFound(String filter) throws Exception {
        restPartnerClientMockMvc.perform(get("/api/partner-clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPartnerClient() throws Exception {
        // Get the partnerClient
        restPartnerClientMockMvc.perform(get("/api/partner-clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartnerClient() throws Exception {
        // Initialize the database
        partnerClientService.save(partnerClient);

        int databaseSizeBeforeUpdate = partnerClientRepository.findAll().size();

        // Update the partnerClient
        PartnerClient updatedPartnerClient = partnerClientRepository.findById(partnerClient.getId()).get();
        // Disconnect from session so that the updates on updatedPartnerClient are not directly saved in db
        em.detach(updatedPartnerClient);

        restPartnerClientMockMvc.perform(put("/api/partner-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartnerClient)))
            .andExpect(status().isOk());

        // Validate the PartnerClient in the database
        List<PartnerClient> partnerClientList = partnerClientRepository.findAll();
        assertThat(partnerClientList).hasSize(databaseSizeBeforeUpdate);
        PartnerClient testPartnerClient = partnerClientList.get(partnerClientList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPartnerClient() throws Exception {
        int databaseSizeBeforeUpdate = partnerClientRepository.findAll().size();

        // Create the PartnerClient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerClientMockMvc.perform(put("/api/partner-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerClient)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerClient in the database
        List<PartnerClient> partnerClientList = partnerClientRepository.findAll();
        assertThat(partnerClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartnerClient() throws Exception {
        // Initialize the database
        partnerClientService.save(partnerClient);

        int databaseSizeBeforeDelete = partnerClientRepository.findAll().size();

        // Get the partnerClient
        restPartnerClientMockMvc.perform(delete("/api/partner-clients/{id}", partnerClient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PartnerClient> partnerClientList = partnerClientRepository.findAll();
        assertThat(partnerClientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerClient.class);
        PartnerClient partnerClient1 = new PartnerClient();
        partnerClient1.setId(1L);
        PartnerClient partnerClient2 = new PartnerClient();
        partnerClient2.setId(partnerClient1.getId());
        assertThat(partnerClient1).isEqualTo(partnerClient2);
        partnerClient2.setId(2L);
        assertThat(partnerClient1).isNotEqualTo(partnerClient2);
        partnerClient1.setId(null);
        assertThat(partnerClient1).isNotEqualTo(partnerClient2);
    }
}
