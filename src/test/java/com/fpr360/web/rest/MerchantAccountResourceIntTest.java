package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.MerchantAccount;
import com.fpr360.domain.Merchant;
import com.fpr360.domain.ClientCrm;
import com.fpr360.repository.MerchantAccountRepository;
import com.fpr360.service.MerchantAccountService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.dto.MerchantAccountCriteria;
import com.fpr360.service.MerchantAccountQueryService;

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
 * Test class for the MerchantAccountResource REST controller.
 *
 * @see MerchantAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class MerchantAccountResourceIntTest {

    private static final String DEFAULT_MID = "AAAAAAAAAA";
    private static final String UPDATED_MID = "BBBBBBBBBB";

    private static final String DEFAULT_MID_DESCRIPTOR = "AAAAAAAAAA";
    private static final String UPDATED_MID_DESCRIPTOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MerchantAccountRepository merchantAccountRepository;
    
    @Autowired
    private MerchantAccountService merchantAccountService;

    @Autowired
    private MerchantAccountQueryService merchantAccountQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMerchantAccountMockMvc;

    private MerchantAccount merchantAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MerchantAccountResource merchantAccountResource = new MerchantAccountResource(merchantAccountService, merchantAccountQueryService);
        this.restMerchantAccountMockMvc = MockMvcBuilders.standaloneSetup(merchantAccountResource)
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
    public static MerchantAccount createEntity(EntityManager em) {
        MerchantAccount merchantAccount = new MerchantAccount()
            .mid(DEFAULT_MID)
            .midDescriptor(DEFAULT_MID_DESCRIPTOR)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        Merchant merchant = MerchantResourceIntTest.createEntity(em);
        em.persist(merchant);
        em.flush();
        merchantAccount.setMerchant(merchant);
        return merchantAccount;
    }

    @Before
    public void initTest() {
        merchantAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerchantAccount() throws Exception {
        int databaseSizeBeforeCreate = merchantAccountRepository.findAll().size();

        // Create the MerchantAccount
        restMerchantAccountMockMvc.perform(post("/api/merchant-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantAccount)))
            .andExpect(status().isCreated());

        // Validate the MerchantAccount in the database
        List<MerchantAccount> merchantAccountList = merchantAccountRepository.findAll();
        assertThat(merchantAccountList).hasSize(databaseSizeBeforeCreate + 1);
        MerchantAccount testMerchantAccount = merchantAccountList.get(merchantAccountList.size() - 1);
        assertThat(testMerchantAccount.getMid()).isEqualTo(DEFAULT_MID);
        assertThat(testMerchantAccount.getMidDescriptor()).isEqualTo(DEFAULT_MID_DESCRIPTOR);
        assertThat(testMerchantAccount.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMerchantAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merchantAccountRepository.findAll().size();

        // Create the MerchantAccount with an existing ID
        merchantAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantAccountMockMvc.perform(post("/api/merchant-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantAccount)))
            .andExpect(status().isBadRequest());

        // Validate the MerchantAccount in the database
        List<MerchantAccount> merchantAccountList = merchantAccountRepository.findAll();
        assertThat(merchantAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMidIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantAccountRepository.findAll().size();
        // set the field null
        merchantAccount.setMid(null);

        // Create the MerchantAccount, which fails.

        restMerchantAccountMockMvc.perform(post("/api/merchant-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantAccount)))
            .andExpect(status().isBadRequest());

        List<MerchantAccount> merchantAccountList = merchantAccountRepository.findAll();
        assertThat(merchantAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantAccountRepository.findAll().size();
        // set the field null
        merchantAccount.setActive(null);

        // Create the MerchantAccount, which fails.

        restMerchantAccountMockMvc.perform(post("/api/merchant-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantAccount)))
            .andExpect(status().isBadRequest());

        List<MerchantAccount> merchantAccountList = merchantAccountRepository.findAll();
        assertThat(merchantAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMerchantAccounts() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get all the merchantAccountList
        restMerchantAccountMockMvc.perform(get("/api/merchant-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].mid").value(hasItem(DEFAULT_MID.toString())))
            .andExpect(jsonPath("$.[*].midDescriptor").value(hasItem(DEFAULT_MID_DESCRIPTOR.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMerchantAccount() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get the merchantAccount
        restMerchantAccountMockMvc.perform(get("/api/merchant-accounts/{id}", merchantAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merchantAccount.getId().intValue()))
            .andExpect(jsonPath("$.mid").value(DEFAULT_MID.toString()))
            .andExpect(jsonPath("$.midDescriptor").value(DEFAULT_MID_DESCRIPTOR.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllMerchantAccountsByMidIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get all the merchantAccountList where mid equals to DEFAULT_MID
        defaultMerchantAccountShouldBeFound("mid.equals=" + DEFAULT_MID);

        // Get all the merchantAccountList where mid equals to UPDATED_MID
        defaultMerchantAccountShouldNotBeFound("mid.equals=" + UPDATED_MID);
    }

    @Test
    @Transactional
    public void getAllMerchantAccountsByMidIsInShouldWork() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get all the merchantAccountList where mid in DEFAULT_MID or UPDATED_MID
        defaultMerchantAccountShouldBeFound("mid.in=" + DEFAULT_MID + "," + UPDATED_MID);

        // Get all the merchantAccountList where mid equals to UPDATED_MID
        defaultMerchantAccountShouldNotBeFound("mid.in=" + UPDATED_MID);
    }

    @Test
    @Transactional
    public void getAllMerchantAccountsByMidIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get all the merchantAccountList where mid is not null
        defaultMerchantAccountShouldBeFound("mid.specified=true");

        // Get all the merchantAccountList where mid is null
        defaultMerchantAccountShouldNotBeFound("mid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantAccountsByMidDescriptorIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get all the merchantAccountList where midDescriptor equals to DEFAULT_MID_DESCRIPTOR
        defaultMerchantAccountShouldBeFound("midDescriptor.equals=" + DEFAULT_MID_DESCRIPTOR);

        // Get all the merchantAccountList where midDescriptor equals to UPDATED_MID_DESCRIPTOR
        defaultMerchantAccountShouldNotBeFound("midDescriptor.equals=" + UPDATED_MID_DESCRIPTOR);
    }

    @Test
    @Transactional
    public void getAllMerchantAccountsByMidDescriptorIsInShouldWork() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get all the merchantAccountList where midDescriptor in DEFAULT_MID_DESCRIPTOR or UPDATED_MID_DESCRIPTOR
        defaultMerchantAccountShouldBeFound("midDescriptor.in=" + DEFAULT_MID_DESCRIPTOR + "," + UPDATED_MID_DESCRIPTOR);

        // Get all the merchantAccountList where midDescriptor equals to UPDATED_MID_DESCRIPTOR
        defaultMerchantAccountShouldNotBeFound("midDescriptor.in=" + UPDATED_MID_DESCRIPTOR);
    }

    @Test
    @Transactional
    public void getAllMerchantAccountsByMidDescriptorIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get all the merchantAccountList where midDescriptor is not null
        defaultMerchantAccountShouldBeFound("midDescriptor.specified=true");

        // Get all the merchantAccountList where midDescriptor is null
        defaultMerchantAccountShouldNotBeFound("midDescriptor.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantAccountsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get all the merchantAccountList where active equals to DEFAULT_ACTIVE
        defaultMerchantAccountShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the merchantAccountList where active equals to UPDATED_ACTIVE
        defaultMerchantAccountShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMerchantAccountsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get all the merchantAccountList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMerchantAccountShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the merchantAccountList where active equals to UPDATED_ACTIVE
        defaultMerchantAccountShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMerchantAccountsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantAccountRepository.saveAndFlush(merchantAccount);

        // Get all the merchantAccountList where active is not null
        defaultMerchantAccountShouldBeFound("active.specified=true");

        // Get all the merchantAccountList where active is null
        defaultMerchantAccountShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantAccountsByMerchantIsEqualToSomething() throws Exception {
        // Initialize the database
        Merchant merchant = MerchantResourceIntTest.createEntity(em);
        em.persist(merchant);
        em.flush();
        merchantAccount.setMerchant(merchant);
        merchantAccountRepository.saveAndFlush(merchantAccount);
        Long merchantId = merchant.getId();

        // Get all the merchantAccountList where merchant equals to merchantId
        defaultMerchantAccountShouldBeFound("merchantId.equals=" + merchantId);

        // Get all the merchantAccountList where merchant equals to merchantId + 1
        defaultMerchantAccountShouldNotBeFound("merchantId.equals=" + (merchantId + 1));
    }


    @Test
    @Transactional
    public void getAllMerchantAccountsByClientCrmIsEqualToSomething() throws Exception {
        // Initialize the database
        ClientCrm clientCrm = ClientCrmResourceIntTest.createEntity(em);
        em.persist(clientCrm);
        em.flush();
        merchantAccount.setClientCrm(clientCrm);
        merchantAccountRepository.saveAndFlush(merchantAccount);
        Long clientCrmId = clientCrm.getId();

        // Get all the merchantAccountList where clientCrm equals to clientCrmId
        defaultMerchantAccountShouldBeFound("clientCrmId.equals=" + clientCrmId);

        // Get all the merchantAccountList where clientCrm equals to clientCrmId + 1
        defaultMerchantAccountShouldNotBeFound("clientCrmId.equals=" + (clientCrmId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMerchantAccountShouldBeFound(String filter) throws Exception {
        restMerchantAccountMockMvc.perform(get("/api/merchant-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].mid").value(hasItem(DEFAULT_MID.toString())))
            .andExpect(jsonPath("$.[*].midDescriptor").value(hasItem(DEFAULT_MID_DESCRIPTOR.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMerchantAccountShouldNotBeFound(String filter) throws Exception {
        restMerchantAccountMockMvc.perform(get("/api/merchant-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMerchantAccount() throws Exception {
        // Get the merchantAccount
        restMerchantAccountMockMvc.perform(get("/api/merchant-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerchantAccount() throws Exception {
        // Initialize the database
        merchantAccountService.save(merchantAccount);

        int databaseSizeBeforeUpdate = merchantAccountRepository.findAll().size();

        // Update the merchantAccount
        MerchantAccount updatedMerchantAccount = merchantAccountRepository.findById(merchantAccount.getId()).get();
        // Disconnect from session so that the updates on updatedMerchantAccount are not directly saved in db
        em.detach(updatedMerchantAccount);
        updatedMerchantAccount
            .mid(UPDATED_MID)
            .midDescriptor(UPDATED_MID_DESCRIPTOR)
            .active(UPDATED_ACTIVE);

        restMerchantAccountMockMvc.perform(put("/api/merchant-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMerchantAccount)))
            .andExpect(status().isOk());

        // Validate the MerchantAccount in the database
        List<MerchantAccount> merchantAccountList = merchantAccountRepository.findAll();
        assertThat(merchantAccountList).hasSize(databaseSizeBeforeUpdate);
        MerchantAccount testMerchantAccount = merchantAccountList.get(merchantAccountList.size() - 1);
        assertThat(testMerchantAccount.getMid()).isEqualTo(UPDATED_MID);
        assertThat(testMerchantAccount.getMidDescriptor()).isEqualTo(UPDATED_MID_DESCRIPTOR);
        assertThat(testMerchantAccount.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMerchantAccount() throws Exception {
        int databaseSizeBeforeUpdate = merchantAccountRepository.findAll().size();

        // Create the MerchantAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMerchantAccountMockMvc.perform(put("/api/merchant-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantAccount)))
            .andExpect(status().isBadRequest());

        // Validate the MerchantAccount in the database
        List<MerchantAccount> merchantAccountList = merchantAccountRepository.findAll();
        assertThat(merchantAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMerchantAccount() throws Exception {
        // Initialize the database
        merchantAccountService.save(merchantAccount);

        int databaseSizeBeforeDelete = merchantAccountRepository.findAll().size();

        // Get the merchantAccount
        restMerchantAccountMockMvc.perform(delete("/api/merchant-accounts/{id}", merchantAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MerchantAccount> merchantAccountList = merchantAccountRepository.findAll();
        assertThat(merchantAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerchantAccount.class);
        MerchantAccount merchantAccount1 = new MerchantAccount();
        merchantAccount1.setId(1L);
        MerchantAccount merchantAccount2 = new MerchantAccount();
        merchantAccount2.setId(merchantAccount1.getId());
        assertThat(merchantAccount1).isEqualTo(merchantAccount2);
        merchantAccount2.setId(2L);
        assertThat(merchantAccount1).isNotEqualTo(merchantAccount2);
        merchantAccount1.setId(null);
        assertThat(merchantAccount1).isNotEqualTo(merchantAccount2);
    }
}
