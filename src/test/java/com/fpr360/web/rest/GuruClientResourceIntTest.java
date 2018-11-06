package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.GuruClient;
import com.fpr360.domain.User;
import com.fpr360.domain.Client;
import com.fpr360.repository.GuruClientRepository;
import com.fpr360.service.GuruClientService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.dto.GuruClientCriteria;
import com.fpr360.service.GuruClientQueryService;

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
import java.util.Arrays;
import java.util.List;


import static com.fpr360.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fpr360.domain.enumeration.GuruClientLevel;
/**
 * Test class for the GuruClientResource REST controller.
 *
 * @see GuruClientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class GuruClientResourceIntTest {

    private static final GuruClientLevel DEFAULT_LEVEL = GuruClientLevel.PRIMARY;
    private static final GuruClientLevel UPDATED_LEVEL = GuruClientLevel.SECONDARY;

    @Autowired
    private GuruClientRepository guruClientRepository;

    @Autowired
    private GuruClientService guruClientService;

    @Autowired
    private GuruClientQueryService guruClientQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGuruClientMockMvc;

    private GuruClient guruClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GuruClientResource guruClientResource = new GuruClientResource(guruClientService, guruClientQueryService);
        this.restGuruClientMockMvc = MockMvcBuilders.standaloneSetup(guruClientResource)
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
    public static GuruClient createEntity(EntityManager em) {
        GuruClient guruClient = new GuruClient()
                .level(DEFAULT_LEVEL);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        guruClient.setGuru(user);
        // Add required entity
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        guruClient.setClient(client);
        return guruClient;
    }

    @Before
    public void initTest() {
        guruClient = createEntity(em);
    }

    @Test
    @Transactional
    public void createGuruClient() throws Exception {
        int databaseSizeBeforeCreate = guruClientRepository.findAll().size();

        // Create the GuruClient
        restGuruClientMockMvc.perform(post("/api/guru-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(guruClient)))
                .andExpect(status().isCreated());

        // Validate the GuruClient in the database
        List<GuruClient> guruClientList = guruClientRepository.findAll();
        assertThat(guruClientList).hasSize(databaseSizeBeforeCreate + 1);
        GuruClient testGuruClient = guruClientList.get(guruClientList.size() - 1);
        assertThat(testGuruClient.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createGuruClientInBulkInsert() throws Exception {
        int databaseSizeBeforeCreate = guruClientRepository.findAll().size();

        List<GuruClient> requestObject = Arrays.asList(guruClient, createEntity(em), createEntity(em));
        // Create the GuruClient
        restGuruClientMockMvc.perform(post("/api/guru-clients/bulk-insert")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(requestObject)))
                .andExpect(status().isCreated());

        // Validate the GuruClient in the database
        List<GuruClient> guruClientList = guruClientRepository.findAll();
        assertThat(guruClientList).hasSize(databaseSizeBeforeCreate + 3);
    }

    @Test
    @Transactional
    public void createGuruClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = guruClientRepository.findAll().size();

        // Create the GuruClient with an existing ID
        guruClient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuruClientMockMvc.perform(post("/api/guru-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(guruClient)))
                .andExpect(status().isBadRequest());

        // Validate the GuruClient in the database
        List<GuruClient> guruClientList = guruClientRepository.findAll();
        assertThat(guruClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = guruClientRepository.findAll().size();
        // set the field null
        guruClient.setLevel(null);

        // Create the GuruClient, which fails.

        restGuruClientMockMvc.perform(post("/api/guru-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(guruClient)))
                .andExpect(status().isBadRequest());

        List<GuruClient> guruClientList = guruClientRepository.findAll();
        assertThat(guruClientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGuruClients() throws Exception {
        // Initialize the database
        guruClientRepository.saveAndFlush(guruClient);

        // Get all the guruClientList
        restGuruClientMockMvc.perform(get("/api/guru-clients?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(guruClient.getId().intValue())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }

    @Test
    @Transactional
    public void getGuruClient() throws Exception {
        // Initialize the database
        guruClientRepository.saveAndFlush(guruClient);

        // Get the guruClient
        restGuruClientMockMvc.perform(get("/api/guru-clients/{id}", guruClient.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(guruClient.getId().intValue()))
                .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }

    @Test
    @Transactional
    public void getAllGuruClientsByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        guruClientRepository.saveAndFlush(guruClient);

        // Get all the guruClientList where level equals to DEFAULT_LEVEL
        defaultGuruClientShouldBeFound("level.equals=" + DEFAULT_LEVEL);

        // Get all the guruClientList where level equals to UPDATED_LEVEL
        defaultGuruClientShouldNotBeFound("level.equals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllGuruClientsByLevelIsInShouldWork() throws Exception {
        // Initialize the database
        guruClientRepository.saveAndFlush(guruClient);

        // Get all the guruClientList where level in DEFAULT_LEVEL or UPDATED_LEVEL
        defaultGuruClientShouldBeFound("level.in=" + DEFAULT_LEVEL + "," + UPDATED_LEVEL);

        // Get all the guruClientList where level equals to UPDATED_LEVEL
        defaultGuruClientShouldNotBeFound("level.in=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllGuruClientsByLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        guruClientRepository.saveAndFlush(guruClient);

        // Get all the guruClientList where level is not null
        defaultGuruClientShouldBeFound("level.specified=true");

        // Get all the guruClientList where level is null
        defaultGuruClientShouldNotBeFound("level.specified=false");
    }

    @Test
    @Transactional
    public void getAllGuruClientsByGuruIsEqualToSomething() throws Exception {
        // Initialize the database
        User guru = UserResourceIntTest.createEntity(em);
        em.persist(guru);
        em.flush();
        guruClient.setGuru(guru);
        guruClientRepository.saveAndFlush(guruClient);
        Long guruId = guru.getId();

        // Get all the guruClientList where guru equals to guruId
        defaultGuruClientShouldBeFound("guruId.equals=" + guruId);

        // Get all the guruClientList where guru equals to guruId + 1
        defaultGuruClientShouldNotBeFound("guruId.equals=" + (guruId + 1));
    }


    @Test
    @Transactional
    public void getAllGuruClientsByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        guruClient.setClient(client);
        guruClientRepository.saveAndFlush(guruClient);
        Long clientId = client.getId();

        // Get all the guruClientList where client equals to clientId
        defaultGuruClientShouldBeFound("clientId.equals=" + clientId);

        // Get all the guruClientList where client equals to clientId + 1
        defaultGuruClientShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGuruClientShouldBeFound(String filter) throws Exception {
        restGuruClientMockMvc.perform(get("/api/guru-clients?sort=id,desc&" + filter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(guruClient.getId().intValue())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGuruClientShouldNotBeFound(String filter) throws Exception {
        restGuruClientMockMvc.perform(get("/api/guru-clients?sort=id,desc&" + filter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingGuruClient() throws Exception {
        // Get the guruClient
        restGuruClientMockMvc.perform(get("/api/guru-clients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGuruClient() throws Exception {
        // Initialize the database
        guruClientService.save(guruClient);

        int databaseSizeBeforeUpdate = guruClientRepository.findAll().size();

        // Update the guruClient
        GuruClient updatedGuruClient = guruClientRepository.findById(guruClient.getId()).get();
        // Disconnect from session so that the updates on updatedGuruClient are not directly saved in db
        em.detach(updatedGuruClient);
        updatedGuruClient
                .level(UPDATED_LEVEL);

        restGuruClientMockMvc.perform(put("/api/guru-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGuruClient)))
                .andExpect(status().isOk());

        // Validate the GuruClient in the database
        List<GuruClient> guruClientList = guruClientRepository.findAll();
        assertThat(guruClientList).hasSize(databaseSizeBeforeUpdate);
        GuruClient testGuruClient = guruClientList.get(guruClientList.size() - 1);
        assertThat(testGuruClient.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingGuruClient() throws Exception {
        int databaseSizeBeforeUpdate = guruClientRepository.findAll().size();

        // Create the GuruClient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuruClientMockMvc.perform(put("/api/guru-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(guruClient)))
                .andExpect(status().isBadRequest());

        // Validate the GuruClient in the database
        List<GuruClient> guruClientList = guruClientRepository.findAll();
        assertThat(guruClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGuruClient() throws Exception {
        // Initialize the database
        guruClientService.save(guruClient);

        int databaseSizeBeforeDelete = guruClientRepository.findAll().size();

        // Get the guruClient
        restGuruClientMockMvc.perform(delete("/api/guru-clients/{id}", guruClient.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GuruClient> guruClientList = guruClientRepository.findAll();
        assertThat(guruClientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuruClient.class);
        GuruClient guruClient1 = new GuruClient();
        guruClient1.setId(1L);
        GuruClient guruClient2 = new GuruClient();
        guruClient2.setId(guruClient1.getId());
        assertThat(guruClient1).isEqualTo(guruClient2);
        guruClient2.setId(2L);
        assertThat(guruClient1).isNotEqualTo(guruClient2);
        guruClient1.setId(null);
        assertThat(guruClient1).isNotEqualTo(guruClient2);
    }
}
