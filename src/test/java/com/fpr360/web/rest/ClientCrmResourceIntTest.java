package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.ClientCrm;
import com.fpr360.domain.Client;
import com.fpr360.domain.CrmVendor;
import com.fpr360.repository.ClientCrmRepository;
import com.fpr360.service.ClientCrmService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.dto.ClientCrmCriteria;
import com.fpr360.service.ClientCrmQueryService;

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
 * Test class for the ClientCrmResource REST controller.
 *
 * @see ClientCrmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class ClientCrmResourceIntTest {

    private static final String DEFAULT_ENDPOINT = "AAAAAAAAAA";
    private static final String UPDATED_ENDPOINT = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_WEB_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_WEB_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_API_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_API_SECRET = "BBBBBBBBBB";

    @Autowired
    private ClientCrmRepository clientCrmRepository;
    
    @Autowired
    private ClientCrmService clientCrmService;

    @Autowired
    private ClientCrmQueryService clientCrmQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientCrmMockMvc;

    private ClientCrm clientCrm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientCrmResource clientCrmResource = new ClientCrmResource(clientCrmService, clientCrmQueryService);
        this.restClientCrmMockMvc = MockMvcBuilders.standaloneSetup(clientCrmResource)
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
    public static ClientCrm createEntity(EntityManager em) {
        ClientCrm clientCrm = new ClientCrm()
            .endpoint(DEFAULT_ENDPOINT)
            .webUsername(DEFAULT_WEB_USERNAME)
            .webPassword(DEFAULT_WEB_PASSWORD)
            .apiKey(DEFAULT_API_KEY)
            .apiSecret(DEFAULT_API_SECRET);
        // Add required entity
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        clientCrm.setClient(client);
        // Add required entity
        CrmVendor crmVendor = CrmVendorResourceIntTest.createEntity(em);
        em.persist(crmVendor);
        em.flush();
        clientCrm.setVendor(crmVendor);
        return clientCrm;
    }

    @Before
    public void initTest() {
        clientCrm = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientCrm() throws Exception {
        int databaseSizeBeforeCreate = clientCrmRepository.findAll().size();

        // Create the ClientCrm
        restClientCrmMockMvc.perform(post("/api/client-crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCrm)))
            .andExpect(status().isCreated());

        // Validate the ClientCrm in the database
        List<ClientCrm> clientCrmList = clientCrmRepository.findAll();
        assertThat(clientCrmList).hasSize(databaseSizeBeforeCreate + 1);
        ClientCrm testClientCrm = clientCrmList.get(clientCrmList.size() - 1);
        assertThat(testClientCrm.getEndpoint()).isEqualTo(DEFAULT_ENDPOINT);
        assertThat(testClientCrm.getWebUsername()).isEqualTo(DEFAULT_WEB_USERNAME);
        assertThat(testClientCrm.getWebPassword()).isEqualTo(DEFAULT_WEB_PASSWORD);
        assertThat(testClientCrm.getApiKey()).isEqualTo(DEFAULT_API_KEY);
        assertThat(testClientCrm.getApiSecret()).isEqualTo(DEFAULT_API_SECRET);
    }

    @Test
    @Transactional
    public void createClientCrmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientCrmRepository.findAll().size();

        // Create the ClientCrm with an existing ID
        clientCrm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientCrmMockMvc.perform(post("/api/client-crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCrm)))
            .andExpect(status().isBadRequest());

        // Validate the ClientCrm in the database
        List<ClientCrm> clientCrmList = clientCrmRepository.findAll();
        assertThat(clientCrmList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEndpointIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientCrmRepository.findAll().size();
        // set the field null
        clientCrm.setEndpoint(null);

        // Create the ClientCrm, which fails.

        restClientCrmMockMvc.perform(post("/api/client-crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCrm)))
            .andExpect(status().isBadRequest());

        List<ClientCrm> clientCrmList = clientCrmRepository.findAll();
        assertThat(clientCrmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWebUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientCrmRepository.findAll().size();
        // set the field null
        clientCrm.setWebUsername(null);

        // Create the ClientCrm, which fails.

        restClientCrmMockMvc.perform(post("/api/client-crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCrm)))
            .andExpect(status().isBadRequest());

        List<ClientCrm> clientCrmList = clientCrmRepository.findAll();
        assertThat(clientCrmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWebPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientCrmRepository.findAll().size();
        // set the field null
        clientCrm.setWebPassword(null);

        // Create the ClientCrm, which fails.

        restClientCrmMockMvc.perform(post("/api/client-crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCrm)))
            .andExpect(status().isBadRequest());

        List<ClientCrm> clientCrmList = clientCrmRepository.findAll();
        assertThat(clientCrmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientCrmRepository.findAll().size();
        // set the field null
        clientCrm.setApiKey(null);

        // Create the ClientCrm, which fails.

        restClientCrmMockMvc.perform(post("/api/client-crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCrm)))
            .andExpect(status().isBadRequest());

        List<ClientCrm> clientCrmList = clientCrmRepository.findAll();
        assertThat(clientCrmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientCrms() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList
        restClientCrmMockMvc.perform(get("/api/client-crms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientCrm.getId().intValue())))
            .andExpect(jsonPath("$.[*].endpoint").value(hasItem(DEFAULT_ENDPOINT.toString())))
            .andExpect(jsonPath("$.[*].webUsername").value(hasItem(DEFAULT_WEB_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].webPassword").value(hasItem(DEFAULT_WEB_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].apiKey").value(hasItem(DEFAULT_API_KEY.toString())))
            .andExpect(jsonPath("$.[*].apiSecret").value(hasItem(DEFAULT_API_SECRET.toString())));
    }
    
    @Test
    @Transactional
    public void getClientCrm() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get the clientCrm
        restClientCrmMockMvc.perform(get("/api/client-crms/{id}", clientCrm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientCrm.getId().intValue()))
            .andExpect(jsonPath("$.endpoint").value(DEFAULT_ENDPOINT.toString()))
            .andExpect(jsonPath("$.webUsername").value(DEFAULT_WEB_USERNAME.toString()))
            .andExpect(jsonPath("$.webPassword").value(DEFAULT_WEB_PASSWORD.toString()))
            .andExpect(jsonPath("$.apiKey").value(DEFAULT_API_KEY.toString()))
            .andExpect(jsonPath("$.apiSecret").value(DEFAULT_API_SECRET.toString()));
    }

    @Test
    @Transactional
    public void getAllClientCrmsByEndpointIsEqualToSomething() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where endpoint equals to DEFAULT_ENDPOINT
        defaultClientCrmShouldBeFound("endpoint.equals=" + DEFAULT_ENDPOINT);

        // Get all the clientCrmList where endpoint equals to UPDATED_ENDPOINT
        defaultClientCrmShouldNotBeFound("endpoint.equals=" + UPDATED_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllClientCrmsByEndpointIsInShouldWork() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where endpoint in DEFAULT_ENDPOINT or UPDATED_ENDPOINT
        defaultClientCrmShouldBeFound("endpoint.in=" + DEFAULT_ENDPOINT + "," + UPDATED_ENDPOINT);

        // Get all the clientCrmList where endpoint equals to UPDATED_ENDPOINT
        defaultClientCrmShouldNotBeFound("endpoint.in=" + UPDATED_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllClientCrmsByEndpointIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where endpoint is not null
        defaultClientCrmShouldBeFound("endpoint.specified=true");

        // Get all the clientCrmList where endpoint is null
        defaultClientCrmShouldNotBeFound("endpoint.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientCrmsByWebUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where webUsername equals to DEFAULT_WEB_USERNAME
        defaultClientCrmShouldBeFound("webUsername.equals=" + DEFAULT_WEB_USERNAME);

        // Get all the clientCrmList where webUsername equals to UPDATED_WEB_USERNAME
        defaultClientCrmShouldNotBeFound("webUsername.equals=" + UPDATED_WEB_USERNAME);
    }

    @Test
    @Transactional
    public void getAllClientCrmsByWebUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where webUsername in DEFAULT_WEB_USERNAME or UPDATED_WEB_USERNAME
        defaultClientCrmShouldBeFound("webUsername.in=" + DEFAULT_WEB_USERNAME + "," + UPDATED_WEB_USERNAME);

        // Get all the clientCrmList where webUsername equals to UPDATED_WEB_USERNAME
        defaultClientCrmShouldNotBeFound("webUsername.in=" + UPDATED_WEB_USERNAME);
    }

    @Test
    @Transactional
    public void getAllClientCrmsByWebUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where webUsername is not null
        defaultClientCrmShouldBeFound("webUsername.specified=true");

        // Get all the clientCrmList where webUsername is null
        defaultClientCrmShouldNotBeFound("webUsername.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientCrmsByWebPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where webPassword equals to DEFAULT_WEB_PASSWORD
        defaultClientCrmShouldBeFound("webPassword.equals=" + DEFAULT_WEB_PASSWORD);

        // Get all the clientCrmList where webPassword equals to UPDATED_WEB_PASSWORD
        defaultClientCrmShouldNotBeFound("webPassword.equals=" + UPDATED_WEB_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllClientCrmsByWebPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where webPassword in DEFAULT_WEB_PASSWORD or UPDATED_WEB_PASSWORD
        defaultClientCrmShouldBeFound("webPassword.in=" + DEFAULT_WEB_PASSWORD + "," + UPDATED_WEB_PASSWORD);

        // Get all the clientCrmList where webPassword equals to UPDATED_WEB_PASSWORD
        defaultClientCrmShouldNotBeFound("webPassword.in=" + UPDATED_WEB_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllClientCrmsByWebPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where webPassword is not null
        defaultClientCrmShouldBeFound("webPassword.specified=true");

        // Get all the clientCrmList where webPassword is null
        defaultClientCrmShouldNotBeFound("webPassword.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientCrmsByApiKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where apiKey equals to DEFAULT_API_KEY
        defaultClientCrmShouldBeFound("apiKey.equals=" + DEFAULT_API_KEY);

        // Get all the clientCrmList where apiKey equals to UPDATED_API_KEY
        defaultClientCrmShouldNotBeFound("apiKey.equals=" + UPDATED_API_KEY);
    }

    @Test
    @Transactional
    public void getAllClientCrmsByApiKeyIsInShouldWork() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where apiKey in DEFAULT_API_KEY or UPDATED_API_KEY
        defaultClientCrmShouldBeFound("apiKey.in=" + DEFAULT_API_KEY + "," + UPDATED_API_KEY);

        // Get all the clientCrmList where apiKey equals to UPDATED_API_KEY
        defaultClientCrmShouldNotBeFound("apiKey.in=" + UPDATED_API_KEY);
    }

    @Test
    @Transactional
    public void getAllClientCrmsByApiKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where apiKey is not null
        defaultClientCrmShouldBeFound("apiKey.specified=true");

        // Get all the clientCrmList where apiKey is null
        defaultClientCrmShouldNotBeFound("apiKey.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientCrmsByApiSecretIsEqualToSomething() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where apiSecret equals to DEFAULT_API_SECRET
        defaultClientCrmShouldBeFound("apiSecret.equals=" + DEFAULT_API_SECRET);

        // Get all the clientCrmList where apiSecret equals to UPDATED_API_SECRET
        defaultClientCrmShouldNotBeFound("apiSecret.equals=" + UPDATED_API_SECRET);
    }

    @Test
    @Transactional
    public void getAllClientCrmsByApiSecretIsInShouldWork() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where apiSecret in DEFAULT_API_SECRET or UPDATED_API_SECRET
        defaultClientCrmShouldBeFound("apiSecret.in=" + DEFAULT_API_SECRET + "," + UPDATED_API_SECRET);

        // Get all the clientCrmList where apiSecret equals to UPDATED_API_SECRET
        defaultClientCrmShouldNotBeFound("apiSecret.in=" + UPDATED_API_SECRET);
    }

    @Test
    @Transactional
    public void getAllClientCrmsByApiSecretIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientCrmRepository.saveAndFlush(clientCrm);

        // Get all the clientCrmList where apiSecret is not null
        defaultClientCrmShouldBeFound("apiSecret.specified=true");

        // Get all the clientCrmList where apiSecret is null
        defaultClientCrmShouldNotBeFound("apiSecret.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientCrmsByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        clientCrm.setClient(client);
        clientCrmRepository.saveAndFlush(clientCrm);
        Long clientId = client.getId();

        // Get all the clientCrmList where client equals to clientId
        defaultClientCrmShouldBeFound("clientId.equals=" + clientId);

        // Get all the clientCrmList where client equals to clientId + 1
        defaultClientCrmShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }


    @Test
    @Transactional
    public void getAllClientCrmsByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        CrmVendor vendor = CrmVendorResourceIntTest.createEntity(em);
        em.persist(vendor);
        em.flush();
        clientCrm.setVendor(vendor);
        clientCrmRepository.saveAndFlush(clientCrm);
        Long vendorId = vendor.getId();

        // Get all the clientCrmList where vendor equals to vendorId
        defaultClientCrmShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the clientCrmList where vendor equals to vendorId + 1
        defaultClientCrmShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClientCrmShouldBeFound(String filter) throws Exception {
        restClientCrmMockMvc.perform(get("/api/client-crms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientCrm.getId().intValue())))
            .andExpect(jsonPath("$.[*].endpoint").value(hasItem(DEFAULT_ENDPOINT.toString())))
            .andExpect(jsonPath("$.[*].webUsername").value(hasItem(DEFAULT_WEB_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].webPassword").value(hasItem(DEFAULT_WEB_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].apiKey").value(hasItem(DEFAULT_API_KEY.toString())))
            .andExpect(jsonPath("$.[*].apiSecret").value(hasItem(DEFAULT_API_SECRET.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClientCrmShouldNotBeFound(String filter) throws Exception {
        restClientCrmMockMvc.perform(get("/api/client-crms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingClientCrm() throws Exception {
        // Get the clientCrm
        restClientCrmMockMvc.perform(get("/api/client-crms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientCrm() throws Exception {
        // Initialize the database
        clientCrmService.save(clientCrm);

        int databaseSizeBeforeUpdate = clientCrmRepository.findAll().size();

        // Update the clientCrm
        ClientCrm updatedClientCrm = clientCrmRepository.findById(clientCrm.getId()).get();
        // Disconnect from session so that the updates on updatedClientCrm are not directly saved in db
        em.detach(updatedClientCrm);
        updatedClientCrm
            .endpoint(UPDATED_ENDPOINT)
            .webUsername(UPDATED_WEB_USERNAME)
            .webPassword(UPDATED_WEB_PASSWORD)
            .apiKey(UPDATED_API_KEY)
            .apiSecret(UPDATED_API_SECRET);

        restClientCrmMockMvc.perform(put("/api/client-crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientCrm)))
            .andExpect(status().isOk());

        // Validate the ClientCrm in the database
        List<ClientCrm> clientCrmList = clientCrmRepository.findAll();
        assertThat(clientCrmList).hasSize(databaseSizeBeforeUpdate);
        ClientCrm testClientCrm = clientCrmList.get(clientCrmList.size() - 1);
        assertThat(testClientCrm.getEndpoint()).isEqualTo(UPDATED_ENDPOINT);
        assertThat(testClientCrm.getWebUsername()).isEqualTo(UPDATED_WEB_USERNAME);
        assertThat(testClientCrm.getWebPassword()).isEqualTo(UPDATED_WEB_PASSWORD);
        assertThat(testClientCrm.getApiKey()).isEqualTo(UPDATED_API_KEY);
        assertThat(testClientCrm.getApiSecret()).isEqualTo(UPDATED_API_SECRET);
    }

    @Test
    @Transactional
    public void updateNonExistingClientCrm() throws Exception {
        int databaseSizeBeforeUpdate = clientCrmRepository.findAll().size();

        // Create the ClientCrm

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientCrmMockMvc.perform(put("/api/client-crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCrm)))
            .andExpect(status().isBadRequest());

        // Validate the ClientCrm in the database
        List<ClientCrm> clientCrmList = clientCrmRepository.findAll();
        assertThat(clientCrmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientCrm() throws Exception {
        // Initialize the database
        clientCrmService.save(clientCrm);

        int databaseSizeBeforeDelete = clientCrmRepository.findAll().size();

        // Get the clientCrm
        restClientCrmMockMvc.perform(delete("/api/client-crms/{id}", clientCrm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientCrm> clientCrmList = clientCrmRepository.findAll();
        assertThat(clientCrmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientCrm.class);
        ClientCrm clientCrm1 = new ClientCrm();
        clientCrm1.setId(1L);
        ClientCrm clientCrm2 = new ClientCrm();
        clientCrm2.setId(clientCrm1.getId());
        assertThat(clientCrm1).isEqualTo(clientCrm2);
        clientCrm2.setId(2L);
        assertThat(clientCrm1).isNotEqualTo(clientCrm2);
        clientCrm1.setId(null);
        assertThat(clientCrm1).isNotEqualTo(clientCrm2);
    }
}
