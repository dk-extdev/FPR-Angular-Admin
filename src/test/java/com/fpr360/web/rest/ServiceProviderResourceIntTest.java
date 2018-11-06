package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.ServiceProvider;
import com.fpr360.domain.Industry;
import com.fpr360.repository.ServiceProviderRepository;
import com.fpr360.service.ServiceProviderService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.dto.ServiceProviderCriteria;
import com.fpr360.service.ServiceProviderQueryService;

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

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;


import static com.fpr360.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ServiceProviderResource REST controller.
 *
 * @see ServiceProviderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class ServiceProviderResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

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

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    
    @Autowired
    private ServiceProviderService serviceProviderService;

    @Autowired
    private ServiceProviderQueryService serviceProviderQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceProviderMockMvc;

    private ServiceProvider serviceProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceProviderResource serviceProviderResource = new ServiceProviderResource(serviceProviderService, serviceProviderQueryService);
        this.restServiceProviderMockMvc = MockMvcBuilders.standaloneSetup(serviceProviderResource)
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
    public static ServiceProvider createEntity(EntityManager em) {
        ServiceProvider serviceProvider = new ServiceProvider()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .state(DEFAULT_STATE)
            .postalCode(DEFAULT_POSTAL_CODE)
            .country(DEFAULT_COUNTRY)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .phoneExt(DEFAULT_PHONE_EXT)
            .email(DEFAULT_EMAIL)
            .website(DEFAULT_WEBSITE)
            .currency(DEFAULT_CURRENCY)
            .active(DEFAULT_ACTIVE)
            .city(DEFAULT_CITY);
        // Add required entity
        Industry industry = IndustryResourceIntTest.createEntity(em);
        em.persist(industry);
        em.flush();
        serviceProvider.setIndustry(industry);
        return serviceProvider;
    }

    @Before
    public void initTest() {
        serviceProvider = createEntity(em);
    }

    /**
     * We need to clean all data from *.csv when running test, otherwise data will not consistent.
     * Note that add this in @{@link PostConstruct} instead of @{@link Before} to try to execute this only once.
     */
    @PostConstruct
    public void postConstruct() {
        this.serviceProviderRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createServiceProvider() throws Exception {
        int databaseSizeBeforeCreate = serviceProviderRepository.findAll().size();

        // Create the ServiceProvider
        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isCreated());

        // Validate the ServiceProvider in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceProvider testServiceProvider = serviceProviderList.get(serviceProviderList.size() - 1);
        assertThat(testServiceProvider.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceProvider.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServiceProvider.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testServiceProvider.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testServiceProvider.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testServiceProvider.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testServiceProvider.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testServiceProvider.getPhoneExt()).isEqualTo(DEFAULT_PHONE_EXT);
        assertThat(testServiceProvider.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testServiceProvider.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testServiceProvider.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testServiceProvider.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testServiceProvider.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    @Transactional
    public void createServiceProviderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceProviderRepository.findAll().size();

        // Create the ServiceProvider with an existing ID
        serviceProvider.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceProvider in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setName(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setAddress(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setState(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setPostalCode(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setCountry(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setPhoneNumber(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setEmail(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setCurrency(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setCity(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceProviders() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList
        restServiceProviderMockMvc.perform(get("/api/service-providers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceProvider.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].phoneExt").value(hasItem(DEFAULT_PHONE_EXT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceProvider() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get the serviceProvider
        restServiceProviderMockMvc.perform(get("/api/service-providers/{id}", serviceProvider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceProvider.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.phoneExt").value(DEFAULT_PHONE_EXT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where name equals to DEFAULT_NAME
        defaultServiceProviderShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the serviceProviderList where name equals to UPDATED_NAME
        defaultServiceProviderShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where name in DEFAULT_NAME or UPDATED_NAME
        defaultServiceProviderShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the serviceProviderList where name equals to UPDATED_NAME
        defaultServiceProviderShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where name is not null
        defaultServiceProviderShouldBeFound("name.specified=true");

        // Get all the serviceProviderList where name is null
        defaultServiceProviderShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where description equals to DEFAULT_DESCRIPTION
        defaultServiceProviderShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the serviceProviderList where description equals to UPDATED_DESCRIPTION
        defaultServiceProviderShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultServiceProviderShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the serviceProviderList where description equals to UPDATED_DESCRIPTION
        defaultServiceProviderShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where description is not null
        defaultServiceProviderShouldBeFound("description.specified=true");

        // Get all the serviceProviderList where description is null
        defaultServiceProviderShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where address equals to DEFAULT_ADDRESS
        defaultServiceProviderShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the serviceProviderList where address equals to UPDATED_ADDRESS
        defaultServiceProviderShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultServiceProviderShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the serviceProviderList where address equals to UPDATED_ADDRESS
        defaultServiceProviderShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where address is not null
        defaultServiceProviderShouldBeFound("address.specified=true");

        // Get all the serviceProviderList where address is null
        defaultServiceProviderShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where state equals to DEFAULT_STATE
        defaultServiceProviderShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the serviceProviderList where state equals to UPDATED_STATE
        defaultServiceProviderShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByStateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where state in DEFAULT_STATE or UPDATED_STATE
        defaultServiceProviderShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the serviceProviderList where state equals to UPDATED_STATE
        defaultServiceProviderShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where state is not null
        defaultServiceProviderShouldBeFound("state.specified=true");

        // Get all the serviceProviderList where state is null
        defaultServiceProviderShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultServiceProviderShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the serviceProviderList where postalCode equals to UPDATED_POSTAL_CODE
        defaultServiceProviderShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultServiceProviderShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the serviceProviderList where postalCode equals to UPDATED_POSTAL_CODE
        defaultServiceProviderShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where postalCode is not null
        defaultServiceProviderShouldBeFound("postalCode.specified=true");

        // Get all the serviceProviderList where postalCode is null
        defaultServiceProviderShouldNotBeFound("postalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where country equals to DEFAULT_COUNTRY
        defaultServiceProviderShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the serviceProviderList where country equals to UPDATED_COUNTRY
        defaultServiceProviderShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultServiceProviderShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the serviceProviderList where country equals to UPDATED_COUNTRY
        defaultServiceProviderShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where country is not null
        defaultServiceProviderShouldBeFound("country.specified=true");

        // Get all the serviceProviderList where country is null
        defaultServiceProviderShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultServiceProviderShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the serviceProviderList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultServiceProviderShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultServiceProviderShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the serviceProviderList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultServiceProviderShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where phoneNumber is not null
        defaultServiceProviderShouldBeFound("phoneNumber.specified=true");

        // Get all the serviceProviderList where phoneNumber is null
        defaultServiceProviderShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByPhoneExtIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where phoneExt equals to DEFAULT_PHONE_EXT
        defaultServiceProviderShouldBeFound("phoneExt.equals=" + DEFAULT_PHONE_EXT);

        // Get all the serviceProviderList where phoneExt equals to UPDATED_PHONE_EXT
        defaultServiceProviderShouldNotBeFound("phoneExt.equals=" + UPDATED_PHONE_EXT);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByPhoneExtIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where phoneExt in DEFAULT_PHONE_EXT or UPDATED_PHONE_EXT
        defaultServiceProviderShouldBeFound("phoneExt.in=" + DEFAULT_PHONE_EXT + "," + UPDATED_PHONE_EXT);

        // Get all the serviceProviderList where phoneExt equals to UPDATED_PHONE_EXT
        defaultServiceProviderShouldNotBeFound("phoneExt.in=" + UPDATED_PHONE_EXT);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByPhoneExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where phoneExt is not null
        defaultServiceProviderShouldBeFound("phoneExt.specified=true");

        // Get all the serviceProviderList where phoneExt is null
        defaultServiceProviderShouldNotBeFound("phoneExt.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where email equals to DEFAULT_EMAIL
        defaultServiceProviderShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the serviceProviderList where email equals to UPDATED_EMAIL
        defaultServiceProviderShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultServiceProviderShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the serviceProviderList where email equals to UPDATED_EMAIL
        defaultServiceProviderShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where email is not null
        defaultServiceProviderShouldBeFound("email.specified=true");

        // Get all the serviceProviderList where email is null
        defaultServiceProviderShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where website equals to DEFAULT_WEBSITE
        defaultServiceProviderShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the serviceProviderList where website equals to UPDATED_WEBSITE
        defaultServiceProviderShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultServiceProviderShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the serviceProviderList where website equals to UPDATED_WEBSITE
        defaultServiceProviderShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where website is not null
        defaultServiceProviderShouldBeFound("website.specified=true");

        // Get all the serviceProviderList where website is null
        defaultServiceProviderShouldNotBeFound("website.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where currency equals to DEFAULT_CURRENCY
        defaultServiceProviderShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the serviceProviderList where currency equals to UPDATED_CURRENCY
        defaultServiceProviderShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultServiceProviderShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the serviceProviderList where currency equals to UPDATED_CURRENCY
        defaultServiceProviderShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where currency is not null
        defaultServiceProviderShouldBeFound("currency.specified=true");

        // Get all the serviceProviderList where currency is null
        defaultServiceProviderShouldNotBeFound("currency.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where active equals to DEFAULT_ACTIVE
        defaultServiceProviderShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the serviceProviderList where active equals to UPDATED_ACTIVE
        defaultServiceProviderShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultServiceProviderShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the serviceProviderList where active equals to UPDATED_ACTIVE
        defaultServiceProviderShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where active is not null
        defaultServiceProviderShouldBeFound("active.specified=true");

        // Get all the serviceProviderList where active is null
        defaultServiceProviderShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where city equals to DEFAULT_CITY
        defaultServiceProviderShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the serviceProviderList where city equals to UPDATED_CITY
        defaultServiceProviderShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByCityIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where city in DEFAULT_CITY or UPDATED_CITY
        defaultServiceProviderShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the serviceProviderList where city equals to UPDATED_CITY
        defaultServiceProviderShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList where city is not null
        defaultServiceProviderShouldBeFound("city.specified=true");

        // Get all the serviceProviderList where city is null
        defaultServiceProviderShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceProvidersByIndustryIsEqualToSomething() throws Exception {
        // Initialize the database
        Industry industry = IndustryResourceIntTest.createEntity(em);
        em.persist(industry);
        em.flush();
        serviceProvider.setIndustry(industry);
        serviceProviderRepository.saveAndFlush(serviceProvider);
        Long industryId = industry.getId();

        // Get all the serviceProviderList where industry equals to industryId
        defaultServiceProviderShouldBeFound("industryId.equals=" + industryId);

        // Get all the serviceProviderList where industry equals to industryId + 1
        defaultServiceProviderShouldNotBeFound("industryId.equals=" + (industryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultServiceProviderShouldBeFound(String filter) throws Exception {
        restServiceProviderMockMvc.perform(get("/api/service-providers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceProvider.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].phoneExt").value(hasItem(DEFAULT_PHONE_EXT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultServiceProviderShouldNotBeFound(String filter) throws Exception {
        restServiceProviderMockMvc.perform(get("/api/service-providers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingServiceProvider() throws Exception {
        // Get the serviceProvider
        restServiceProviderMockMvc.perform(get("/api/service-providers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceProvider() throws Exception {
        // Initialize the database
        serviceProviderService.save(serviceProvider);

        int databaseSizeBeforeUpdate = serviceProviderRepository.findAll().size();

        // Update the serviceProvider
        ServiceProvider updatedServiceProvider = serviceProviderRepository.findById(serviceProvider.getId()).get();
        // Disconnect from session so that the updates on updatedServiceProvider are not directly saved in db
        em.detach(updatedServiceProvider);
        updatedServiceProvider
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .state(UPDATED_STATE)
            .postalCode(UPDATED_POSTAL_CODE)
            .country(UPDATED_COUNTRY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .phoneExt(UPDATED_PHONE_EXT)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .currency(UPDATED_CURRENCY)
            .active(UPDATED_ACTIVE)
            .city(UPDATED_CITY);

        restServiceProviderMockMvc.perform(put("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceProvider)))
            .andExpect(status().isOk());

        // Validate the ServiceProvider in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeUpdate);
        ServiceProvider testServiceProvider = serviceProviderList.get(serviceProviderList.size() - 1);
        assertThat(testServiceProvider.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceProvider.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServiceProvider.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testServiceProvider.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testServiceProvider.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testServiceProvider.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testServiceProvider.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testServiceProvider.getPhoneExt()).isEqualTo(UPDATED_PHONE_EXT);
        assertThat(testServiceProvider.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testServiceProvider.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testServiceProvider.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testServiceProvider.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testServiceProvider.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceProvider() throws Exception {
        int databaseSizeBeforeUpdate = serviceProviderRepository.findAll().size();

        // Create the ServiceProvider

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceProviderMockMvc.perform(put("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceProvider in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceProvider() throws Exception {
        // Initialize the database
        serviceProviderService.save(serviceProvider);

        int databaseSizeBeforeDelete = serviceProviderRepository.findAll().size();

        // Get the serviceProvider
        restServiceProviderMockMvc.perform(delete("/api/service-providers/{id}", serviceProvider.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceProvider.class);
        ServiceProvider serviceProvider1 = new ServiceProvider();
        serviceProvider1.setId(1L);
        ServiceProvider serviceProvider2 = new ServiceProvider();
        serviceProvider2.setId(serviceProvider1.getId());
        assertThat(serviceProvider1).isEqualTo(serviceProvider2);
        serviceProvider2.setId(2L);
        assertThat(serviceProvider1).isNotEqualTo(serviceProvider2);
        serviceProvider1.setId(null);
        assertThat(serviceProvider1).isNotEqualTo(serviceProvider2);
    }
}
