package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.Partner;
import com.fpr360.domain.Industry;
import com.fpr360.repository.PartnerRepository;
import com.fpr360.service.PartnerService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.PartnerQueryService;

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

import com.fpr360.domain.enumeration.PartnerType;
/**
 * Test class for the PartnerResource REST controller.
 *
 * @see PartnerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class PartnerResourceIntTest {

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

    private static final PartnerType DEFAULT_TYPE = PartnerType.REFERRAL;
    private static final PartnerType UPDATED_TYPE = PartnerType.RESELLER;

    @Autowired
    private PartnerRepository partnerRepository;
    
    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PartnerQueryService partnerQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartnerMockMvc;

    private Partner partner;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerResource partnerResource = new PartnerResource(partnerService, partnerQueryService);
        this.restPartnerMockMvc = MockMvcBuilders.standaloneSetup(partnerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @PostConstruct
    public void postConstruct() {
        partnerRepository.deleteAll();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partner createEntity(EntityManager em) {
        Partner partner = new Partner()
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
            .active(DEFAULT_ACTIVE)
            .type(DEFAULT_TYPE);
        // Add required entity
        Industry industry = IndustryResourceIntTest.createEntity(em);
        em.persist(industry);
        em.flush();
        partner.setIndustry(industry);
        return partner;
    }

    @Before
    public void initTest() {
        partner = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartner() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner
        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isCreated());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeCreate + 1);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPartner.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartner.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPartner.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPartner.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testPartner.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testPartner.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPartner.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPartner.getPhoneExt()).isEqualTo(DEFAULT_PHONE_EXT);
        assertThat(testPartner.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPartner.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testPartner.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testPartner.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testPartner.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createPartnerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner with an existing ID
        partner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setName(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setAddress(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setCity(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setState(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setPostalCode(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setCountry(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setPhoneNumber(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setEmail(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setCurrency(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setType(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartners() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList
        restPartnerMockMvc.perform(get("/api/partners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getPartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partner.getId().intValue()))
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
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllPartnersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where name equals to DEFAULT_NAME
        defaultPartnerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the partnerList where name equals to UPDATED_NAME
        defaultPartnerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPartnersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPartnerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the partnerList where name equals to UPDATED_NAME
        defaultPartnerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPartnersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where name is not null
        defaultPartnerShouldBeFound("name.specified=true");

        // Get all the partnerList where name is null
        defaultPartnerShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where description equals to DEFAULT_DESCRIPTION
        defaultPartnerShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the partnerList where description equals to UPDATED_DESCRIPTION
        defaultPartnerShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPartnersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPartnerShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the partnerList where description equals to UPDATED_DESCRIPTION
        defaultPartnerShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPartnersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where description is not null
        defaultPartnerShouldBeFound("description.specified=true");

        // Get all the partnerList where description is null
        defaultPartnerShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where address equals to DEFAULT_ADDRESS
        defaultPartnerShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the partnerList where address equals to UPDATED_ADDRESS
        defaultPartnerShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPartnersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultPartnerShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the partnerList where address equals to UPDATED_ADDRESS
        defaultPartnerShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPartnersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where address is not null
        defaultPartnerShouldBeFound("address.specified=true");

        // Get all the partnerList where address is null
        defaultPartnerShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where city equals to DEFAULT_CITY
        defaultPartnerShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the partnerList where city equals to UPDATED_CITY
        defaultPartnerShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllPartnersByCityIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where city in DEFAULT_CITY or UPDATED_CITY
        defaultPartnerShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the partnerList where city equals to UPDATED_CITY
        defaultPartnerShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllPartnersByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where city is not null
        defaultPartnerShouldBeFound("city.specified=true");

        // Get all the partnerList where city is null
        defaultPartnerShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where state equals to DEFAULT_STATE
        defaultPartnerShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the partnerList where state equals to UPDATED_STATE
        defaultPartnerShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllPartnersByStateIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where state in DEFAULT_STATE or UPDATED_STATE
        defaultPartnerShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the partnerList where state equals to UPDATED_STATE
        defaultPartnerShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllPartnersByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where state is not null
        defaultPartnerShouldBeFound("state.specified=true");

        // Get all the partnerList where state is null
        defaultPartnerShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultPartnerShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the partnerList where postalCode equals to UPDATED_POSTAL_CODE
        defaultPartnerShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllPartnersByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultPartnerShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the partnerList where postalCode equals to UPDATED_POSTAL_CODE
        defaultPartnerShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllPartnersByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where postalCode is not null
        defaultPartnerShouldBeFound("postalCode.specified=true");

        // Get all the partnerList where postalCode is null
        defaultPartnerShouldNotBeFound("postalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where country equals to DEFAULT_COUNTRY
        defaultPartnerShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the partnerList where country equals to UPDATED_COUNTRY
        defaultPartnerShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllPartnersByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultPartnerShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the partnerList where country equals to UPDATED_COUNTRY
        defaultPartnerShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllPartnersByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where country is not null
        defaultPartnerShouldBeFound("country.specified=true");

        // Get all the partnerList where country is null
        defaultPartnerShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultPartnerShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the partnerList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPartnerShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPartnersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultPartnerShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the partnerList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPartnerShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPartnersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where phoneNumber is not null
        defaultPartnerShouldBeFound("phoneNumber.specified=true");

        // Get all the partnerList where phoneNumber is null
        defaultPartnerShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByPhoneExtIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where phoneExt equals to DEFAULT_PHONE_EXT
        defaultPartnerShouldBeFound("phoneExt.equals=" + DEFAULT_PHONE_EXT);

        // Get all the partnerList where phoneExt equals to UPDATED_PHONE_EXT
        defaultPartnerShouldNotBeFound("phoneExt.equals=" + UPDATED_PHONE_EXT);
    }

    @Test
    @Transactional
    public void getAllPartnersByPhoneExtIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where phoneExt in DEFAULT_PHONE_EXT or UPDATED_PHONE_EXT
        defaultPartnerShouldBeFound("phoneExt.in=" + DEFAULT_PHONE_EXT + "," + UPDATED_PHONE_EXT);

        // Get all the partnerList where phoneExt equals to UPDATED_PHONE_EXT
        defaultPartnerShouldNotBeFound("phoneExt.in=" + UPDATED_PHONE_EXT);
    }

    @Test
    @Transactional
    public void getAllPartnersByPhoneExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where phoneExt is not null
        defaultPartnerShouldBeFound("phoneExt.specified=true");

        // Get all the partnerList where phoneExt is null
        defaultPartnerShouldNotBeFound("phoneExt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where email equals to DEFAULT_EMAIL
        defaultPartnerShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the partnerList where email equals to UPDATED_EMAIL
        defaultPartnerShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPartnersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultPartnerShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the partnerList where email equals to UPDATED_EMAIL
        defaultPartnerShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPartnersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where email is not null
        defaultPartnerShouldBeFound("email.specified=true");

        // Get all the partnerList where email is null
        defaultPartnerShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where website equals to DEFAULT_WEBSITE
        defaultPartnerShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the partnerList where website equals to UPDATED_WEBSITE
        defaultPartnerShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllPartnersByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultPartnerShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the partnerList where website equals to UPDATED_WEBSITE
        defaultPartnerShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllPartnersByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where website is not null
        defaultPartnerShouldBeFound("website.specified=true");

        // Get all the partnerList where website is null
        defaultPartnerShouldNotBeFound("website.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where currency equals to DEFAULT_CURRENCY
        defaultPartnerShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the partnerList where currency equals to UPDATED_CURRENCY
        defaultPartnerShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllPartnersByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultPartnerShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the partnerList where currency equals to UPDATED_CURRENCY
        defaultPartnerShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllPartnersByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where currency is not null
        defaultPartnerShouldBeFound("currency.specified=true");

        // Get all the partnerList where currency is null
        defaultPartnerShouldNotBeFound("currency.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where active equals to DEFAULT_ACTIVE
        defaultPartnerShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the partnerList where active equals to UPDATED_ACTIVE
        defaultPartnerShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPartnersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultPartnerShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the partnerList where active equals to UPDATED_ACTIVE
        defaultPartnerShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPartnersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where active is not null
        defaultPartnerShouldBeFound("active.specified=true");

        // Get all the partnerList where active is null
        defaultPartnerShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where type equals to DEFAULT_TYPE
        defaultPartnerShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the partnerList where type equals to UPDATED_TYPE
        defaultPartnerShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPartnersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPartnerShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the partnerList where type equals to UPDATED_TYPE
        defaultPartnerShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPartnersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList where type is not null
        defaultPartnerShouldBeFound("type.specified=true");

        // Get all the partnerList where type is null
        defaultPartnerShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartnersByIndustryIsEqualToSomething() throws Exception {
        // Initialize the database
        Industry industry = IndustryResourceIntTest.createEntity(em);
        em.persist(industry);
        em.flush();
        partner.setIndustry(industry);
        partnerRepository.saveAndFlush(partner);
        Long industryId = industry.getId();

        // Get all the partnerList where industry equals to industryId
        defaultPartnerShouldBeFound("industryId.equals=" + industryId);

        // Get all the partnerList where industry equals to industryId + 1
        defaultPartnerShouldNotBeFound("industryId.equals=" + (industryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPartnerShouldBeFound(String filter) throws Exception {
        restPartnerMockMvc.perform(get("/api/partners?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPartnerShouldNotBeFound(String filter) throws Exception {
        restPartnerMockMvc.perform(get("/api/partners?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPartner() throws Exception {
        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartner() throws Exception {
        // Initialize the database
        partnerService.save(partner);

        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Update the partner
        Partner updatedPartner = partnerRepository.findById(partner.getId()).get();
        // Disconnect from session so that the updates on updatedPartner are not directly saved in db
        em.detach(updatedPartner);
        updatedPartner
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
            .active(UPDATED_ACTIVE)
            .type(UPDATED_TYPE);

        restPartnerMockMvc.perform(put("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartner)))
            .andExpect(status().isOk());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPartner.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartner.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPartner.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPartner.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testPartner.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testPartner.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPartner.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPartner.getPhoneExt()).isEqualTo(UPDATED_PHONE_EXT);
        assertThat(testPartner.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPartner.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testPartner.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPartner.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPartner.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartner() throws Exception {
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Create the Partner

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerMockMvc.perform(put("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartner() throws Exception {
        // Initialize the database
        partnerService.save(partner);

        int databaseSizeBeforeDelete = partnerRepository.findAll().size();

        // Get the partner
        restPartnerMockMvc.perform(delete("/api/partners/{id}", partner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partner.class);
        Partner partner1 = new Partner();
        partner1.setId(1L);
        Partner partner2 = new Partner();
        partner2.setId(partner1.getId());
        assertThat(partner1).isEqualTo(partner2);
        partner2.setId(2L);
        assertThat(partner1).isNotEqualTo(partner2);
        partner1.setId(null);
        assertThat(partner1).isNotEqualTo(partner2);
    }
}
