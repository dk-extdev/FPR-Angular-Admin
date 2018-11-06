package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.Merchant;
import com.fpr360.domain.Industry;
import com.fpr360.domain.Client;
import com.fpr360.repository.MerchantRepository;
import com.fpr360.service.MerchantService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.dto.MerchantCriteria;
import com.fpr360.service.MerchantQueryService;

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
 * Test class for the MerchantResource REST controller.
 *
 * @see MerchantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class MerchantResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_EXT = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAA";
    private static final String UPDATED_CURRENCY = "BBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MerchantRepository merchantRepository;
    
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantQueryService merchantQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMerchantMockMvc;

    private Merchant merchant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MerchantResource merchantResource = new MerchantResource(merchantService, merchantQueryService);
        this.restMerchantMockMvc = MockMvcBuilders.standaloneSetup(merchantResource)
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
    public static Merchant createEntity(EntityManager em) {
        Merchant merchant = new Merchant()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .postalCode(DEFAULT_POSTAL_CODE)
            .country(DEFAULT_COUNTRY)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .phoneExt(DEFAULT_PHONE_EXT)
            .email(DEFAULT_EMAIL)
            .website(DEFAULT_WEBSITE)
            .currency(DEFAULT_CURRENCY)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        Industry industry = IndustryResourceIntTest.createEntity(em);
        em.persist(industry);
        em.flush();
        merchant.setIndustry(industry);
        // Add required entity
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        merchant.setClient(client);
        return merchant;
    }

    @Before
    public void initTest() {
        merchant = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerchant() throws Exception {
        int databaseSizeBeforeCreate = merchantRepository.findAll().size();

        // Create the Merchant
        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isCreated());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeCreate + 1);
        Merchant testMerchant = merchantList.get(merchantList.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMerchant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMerchant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMerchant.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testMerchant.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testMerchant.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testMerchant.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMerchant.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testMerchant.getPhoneExt()).isEqualTo(DEFAULT_PHONE_EXT);
        assertThat(testMerchant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMerchant.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testMerchant.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testMerchant.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMerchantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merchantRepository.findAll().size();

        // Create the Merchant with an existing ID
        merchant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setName(null);

        // Create the Merchant, which fails.

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setAddress(null);

        // Create the Merchant, which fails.

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setCity(null);

        // Create the Merchant, which fails.

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setState(null);

        // Create the Merchant, which fails.

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setPostalCode(null);

        // Create the Merchant, which fails.

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setCountry(null);

        // Create the Merchant, which fails.

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setPhoneNumber(null);

        // Create the Merchant, which fails.

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setEmail(null);

        // Create the Merchant, which fails.

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setCurrency(null);

        // Create the Merchant, which fails.

        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMerchants() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList
        restMerchantMockMvc.perform(get("/api/merchants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].phoneExt").value(hasItem(DEFAULT_PHONE_EXT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getMerchant() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get the merchant
        restMerchantMockMvc.perform(get("/api/merchants/{id}", merchant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merchant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.phoneExt").value(DEFAULT_PHONE_EXT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllMerchantsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where name equals to DEFAULT_NAME
        defaultMerchantShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the merchantList where name equals to UPDATED_NAME
        defaultMerchantShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMerchantsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMerchantShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the merchantList where name equals to UPDATED_NAME
        defaultMerchantShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMerchantsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where name is not null
        defaultMerchantShouldBeFound("name.specified=true");

        // Get all the merchantList where name is null
        defaultMerchantShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where description equals to DEFAULT_DESCRIPTION
        defaultMerchantShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the merchantList where description equals to UPDATED_DESCRIPTION
        defaultMerchantShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMerchantsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMerchantShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the merchantList where description equals to UPDATED_DESCRIPTION
        defaultMerchantShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMerchantsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where description is not null
        defaultMerchantShouldBeFound("description.specified=true");

        // Get all the merchantList where description is null
        defaultMerchantShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where address equals to DEFAULT_ADDRESS
        defaultMerchantShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the merchantList where address equals to UPDATED_ADDRESS
        defaultMerchantShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMerchantsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultMerchantShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the merchantList where address equals to UPDATED_ADDRESS
        defaultMerchantShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMerchantsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where address is not null
        defaultMerchantShouldBeFound("address.specified=true");

        // Get all the merchantList where address is null
        defaultMerchantShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where city equals to DEFAULT_CITY
        defaultMerchantShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the merchantList where city equals to UPDATED_CITY
        defaultMerchantShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllMerchantsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where city in DEFAULT_CITY or UPDATED_CITY
        defaultMerchantShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the merchantList where city equals to UPDATED_CITY
        defaultMerchantShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllMerchantsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where city is not null
        defaultMerchantShouldBeFound("city.specified=true");

        // Get all the merchantList where city is null
        defaultMerchantShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where state equals to DEFAULT_STATE
        defaultMerchantShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the merchantList where state equals to UPDATED_STATE
        defaultMerchantShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllMerchantsByStateIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where state in DEFAULT_STATE or UPDATED_STATE
        defaultMerchantShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the merchantList where state equals to UPDATED_STATE
        defaultMerchantShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllMerchantsByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where state is not null
        defaultMerchantShouldBeFound("state.specified=true");

        // Get all the merchantList where state is null
        defaultMerchantShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultMerchantShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the merchantList where postalCode equals to UPDATED_POSTAL_CODE
        defaultMerchantShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllMerchantsByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultMerchantShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the merchantList where postalCode equals to UPDATED_POSTAL_CODE
        defaultMerchantShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllMerchantsByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where postalCode is not null
        defaultMerchantShouldBeFound("postalCode.specified=true");

        // Get all the merchantList where postalCode is null
        defaultMerchantShouldNotBeFound("postalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where country equals to DEFAULT_COUNTRY
        defaultMerchantShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the merchantList where country equals to UPDATED_COUNTRY
        defaultMerchantShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllMerchantsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultMerchantShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the merchantList where country equals to UPDATED_COUNTRY
        defaultMerchantShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllMerchantsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where country is not null
        defaultMerchantShouldBeFound("country.specified=true");

        // Get all the merchantList where country is null
        defaultMerchantShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultMerchantShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the merchantList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultMerchantShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMerchantsByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultMerchantShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the merchantList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultMerchantShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMerchantsByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phoneNumber is not null
        defaultMerchantShouldBeFound("phoneNumber.specified=true");

        // Get all the merchantList where phoneNumber is null
        defaultMerchantShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByPhoneExtIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phoneExt equals to DEFAULT_PHONE_EXT
        defaultMerchantShouldBeFound("phoneExt.equals=" + DEFAULT_PHONE_EXT);

        // Get all the merchantList where phoneExt equals to UPDATED_PHONE_EXT
        defaultMerchantShouldNotBeFound("phoneExt.equals=" + UPDATED_PHONE_EXT);
    }

    @Test
    @Transactional
    public void getAllMerchantsByPhoneExtIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phoneExt in DEFAULT_PHONE_EXT or UPDATED_PHONE_EXT
        defaultMerchantShouldBeFound("phoneExt.in=" + DEFAULT_PHONE_EXT + "," + UPDATED_PHONE_EXT);

        // Get all the merchantList where phoneExt equals to UPDATED_PHONE_EXT
        defaultMerchantShouldNotBeFound("phoneExt.in=" + UPDATED_PHONE_EXT);
    }

    @Test
    @Transactional
    public void getAllMerchantsByPhoneExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phoneExt is not null
        defaultMerchantShouldBeFound("phoneExt.specified=true");

        // Get all the merchantList where phoneExt is null
        defaultMerchantShouldNotBeFound("phoneExt.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where email equals to DEFAULT_EMAIL
        defaultMerchantShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the merchantList where email equals to UPDATED_EMAIL
        defaultMerchantShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllMerchantsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultMerchantShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the merchantList where email equals to UPDATED_EMAIL
        defaultMerchantShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllMerchantsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where email is not null
        defaultMerchantShouldBeFound("email.specified=true");

        // Get all the merchantList where email is null
        defaultMerchantShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where website equals to DEFAULT_WEBSITE
        defaultMerchantShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the merchantList where website equals to UPDATED_WEBSITE
        defaultMerchantShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllMerchantsByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultMerchantShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the merchantList where website equals to UPDATED_WEBSITE
        defaultMerchantShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllMerchantsByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where website is not null
        defaultMerchantShouldBeFound("website.specified=true");

        // Get all the merchantList where website is null
        defaultMerchantShouldNotBeFound("website.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where currency equals to DEFAULT_CURRENCY
        defaultMerchantShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the merchantList where currency equals to UPDATED_CURRENCY
        defaultMerchantShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllMerchantsByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultMerchantShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the merchantList where currency equals to UPDATED_CURRENCY
        defaultMerchantShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllMerchantsByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where currency is not null
        defaultMerchantShouldBeFound("currency.specified=true");

        // Get all the merchantList where currency is null
        defaultMerchantShouldNotBeFound("currency.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where active equals to DEFAULT_ACTIVE
        defaultMerchantShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the merchantList where active equals to UPDATED_ACTIVE
        defaultMerchantShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMerchantsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMerchantShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the merchantList where active equals to UPDATED_ACTIVE
        defaultMerchantShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMerchantsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where active is not null
        defaultMerchantShouldBeFound("active.specified=true");

        // Get all the merchantList where active is null
        defaultMerchantShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMerchantsByIndustryIsEqualToSomething() throws Exception {
        // Initialize the database
        Industry industry = IndustryResourceIntTest.createEntity(em);
        em.persist(industry);
        em.flush();
        merchant.setIndustry(industry);
        merchantRepository.saveAndFlush(merchant);
        Long industryId = industry.getId();

        // Get all the merchantList where industry equals to industryId
        defaultMerchantShouldBeFound("industryId.equals=" + industryId);

        // Get all the merchantList where industry equals to industryId + 1
        defaultMerchantShouldNotBeFound("industryId.equals=" + (industryId + 1));
    }


    @Test
    @Transactional
    public void getAllMerchantsByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        merchant.setClient(client);
        merchantRepository.saveAndFlush(merchant);
        Long clientId = client.getId();

        // Get all the merchantList where client equals to clientId
        defaultMerchantShouldBeFound("clientId.equals=" + clientId);

        // Get all the merchantList where client equals to clientId + 1
        defaultMerchantShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMerchantShouldBeFound(String filter) throws Exception {
        restMerchantMockMvc.perform(get("/api/merchants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].phoneExt").value(hasItem(DEFAULT_PHONE_EXT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMerchantShouldNotBeFound(String filter) throws Exception {
        restMerchantMockMvc.perform(get("/api/merchants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMerchant() throws Exception {
        // Get the merchant
        restMerchantMockMvc.perform(get("/api/merchants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerchant() throws Exception {
        // Initialize the database
        merchantService.save(merchant);

        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();

        // Update the merchant
        Merchant updatedMerchant = merchantRepository.findById(merchant.getId()).get();
        // Disconnect from session so that the updates on updatedMerchant are not directly saved in db
        em.detach(updatedMerchant);
        updatedMerchant
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .postalCode(UPDATED_POSTAL_CODE)
            .country(UPDATED_COUNTRY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .phoneExt(UPDATED_PHONE_EXT)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .currency(UPDATED_CURRENCY)
            .active(UPDATED_ACTIVE);

        restMerchantMockMvc.perform(put("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMerchant)))
            .andExpect(status().isOk());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
        Merchant testMerchant = merchantList.get(merchantList.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMerchant.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMerchant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMerchant.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMerchant.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testMerchant.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testMerchant.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMerchant.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testMerchant.getPhoneExt()).isEqualTo(UPDATED_PHONE_EXT);
        assertThat(testMerchant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMerchant.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testMerchant.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testMerchant.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMerchant() throws Exception {
        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();

        // Create the Merchant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMerchantMockMvc.perform(put("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMerchant() throws Exception {
        // Initialize the database
        merchantService.save(merchant);

        int databaseSizeBeforeDelete = merchantRepository.findAll().size();

        // Get the merchant
        restMerchantMockMvc.perform(delete("/api/merchants/{id}", merchant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Merchant.class);
        Merchant merchant1 = new Merchant();
        merchant1.setId(1L);
        Merchant merchant2 = new Merchant();
        merchant2.setId(merchant1.getId());
        assertThat(merchant1).isEqualTo(merchant2);
        merchant2.setId(2L);
        assertThat(merchant1).isNotEqualTo(merchant2);
        merchant1.setId(null);
        assertThat(merchant1).isNotEqualTo(merchant2);
    }
}
