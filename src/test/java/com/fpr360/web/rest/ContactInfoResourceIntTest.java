package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.ContactInfo;
import com.fpr360.domain.User;
import com.fpr360.repository.ContactInfoRepository;
import com.fpr360.service.ContactInfoService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.dto.ContactInfoCriteria;
import com.fpr360.service.ContactInfoQueryService;

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
 * Test class for the ContactInfoResource REST controller.
 *
 * @see ContactInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class ContactInfoResourceIntTest {

    public static final String DEFAULT_PRIMARY_PHONE = "AAAAAAAAAA";
    public static final String UPDATED_PRIMARY_PHONE = "BBBBBBBBBB";

    public static final String DEFAULT_SECONDARY_PHONE = "AAAAAAAAAA";
    public static final String UPDATED_SECONDARY_PHONE = "BBBBBBBBBB";

    public static final String DEFAULT_WORK_PHONE = "AAAAAAAAAA";
    public static final String UPDATED_WORK_PHONE = "BBBBBBBBBB";

    public static final String DEFAULT_WORK_PHONE_EXT = "123";
    public static final String UPDATED_WORK_PHONE_EXT = "456";

    public static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    public static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    public static final String DEFAULT_SKYPE_ID = "AAAAAAAAAA";
    public static final String UPDATED_SKYPE_ID = "BBBBBBBBBB";

    @Autowired
    private ContactInfoRepository contactInfoRepository;
    
    @Autowired
    private ContactInfoService contactInfoService;

    @Autowired
    private ContactInfoQueryService contactInfoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactInfoMockMvc;

    private ContactInfo contactInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactInfoResource contactInfoResource = new ContactInfoResource(contactInfoService, contactInfoQueryService);
        this.restContactInfoMockMvc = MockMvcBuilders.standaloneSetup(contactInfoResource)
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
    public static ContactInfo createEntity(EntityManager em) {
        return createEntity(em, true);
    }

    /**
     * Do the same thing as {@link #createEntity(EntityManager)}, but discard creation of {@link User} entity.
     */
    public static ContactInfo createEntity(EntityManager em, boolean addUserEntity) {
        ContactInfo contactInfo = new ContactInfo()
                .primaryPhone(DEFAULT_PRIMARY_PHONE)
                .secondaryPhone(DEFAULT_SECONDARY_PHONE)
                .workPhone(DEFAULT_WORK_PHONE)
                .workPhoneExt(DEFAULT_WORK_PHONE_EXT)
                .mobilePhone(DEFAULT_MOBILE_PHONE)
                .skypeId(DEFAULT_SKYPE_ID);

        if (addUserEntity) {
            User user = UserResourceIntTest.createEntity(em);
            em.persist(user);
            em.flush();
            contactInfo.setUser(user);
        }

        return contactInfo;
    }

    @Before
    public void initTest() {
        contactInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactInfo() throws Exception {
        int databaseSizeBeforeCreate = contactInfoRepository.findAll().size();

        // Create the ContactInfo
        restContactInfoMockMvc.perform(post("/api/contact-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactInfo)))
            .andExpect(status().isCreated());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getPrimaryPhone()).isEqualTo(DEFAULT_PRIMARY_PHONE);
        assertThat(testContactInfo.getSecondaryPhone()).isEqualTo(DEFAULT_SECONDARY_PHONE);
        assertThat(testContactInfo.getWorkPhone()).isEqualTo(DEFAULT_WORK_PHONE);
        assertThat(testContactInfo.getWorkPhoneExt()).isEqualTo(DEFAULT_WORK_PHONE_EXT);
        assertThat(testContactInfo.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testContactInfo.getSkypeId()).isEqualTo(DEFAULT_SKYPE_ID);
    }

    @Test
    @Transactional
    public void createContactInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactInfoRepository.findAll().size();

        // Create the ContactInfo with an existing ID
        contactInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactInfoMockMvc.perform(post("/api/contact-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContactInfos() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList
        restContactInfoMockMvc.perform(get("/api/contact-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].primaryPhone").value(hasItem(DEFAULT_PRIMARY_PHONE.toString())))
            .andExpect(jsonPath("$.[*].secondaryPhone").value(hasItem(DEFAULT_SECONDARY_PHONE.toString())))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE.toString())))
            .andExpect(jsonPath("$.[*].workPhoneExt").value(hasItem(DEFAULT_WORK_PHONE_EXT.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].skypeId").value(hasItem(DEFAULT_SKYPE_ID.toString())));
    }
    
    @Test
    @Transactional
    public void getContactInfo() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get the contactInfo
        restContactInfoMockMvc.perform(get("/api/contact-infos/{id}", contactInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactInfo.getId().intValue()))
            .andExpect(jsonPath("$.primaryPhone").value(DEFAULT_PRIMARY_PHONE.toString()))
            .andExpect(jsonPath("$.secondaryPhone").value(DEFAULT_SECONDARY_PHONE.toString()))
            .andExpect(jsonPath("$.workPhone").value(DEFAULT_WORK_PHONE.toString()))
            .andExpect(jsonPath("$.workPhoneExt").value(DEFAULT_WORK_PHONE_EXT.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.skypeId").value(DEFAULT_SKYPE_ID.toString()));
    }

    @Test
    @Transactional
    public void getAllContactInfosByPrimaryPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where primaryPhone equals to DEFAULT_PRIMARY_PHONE
        defaultContactInfoShouldBeFound("primaryPhone.equals=" + DEFAULT_PRIMARY_PHONE);

        // Get all the contactInfoList where primaryPhone equals to UPDATED_PRIMARY_PHONE
        defaultContactInfoShouldNotBeFound("primaryPhone.equals=" + UPDATED_PRIMARY_PHONE);
    }

    @Test
    @Transactional
    public void getAllContactInfosByPrimaryPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where primaryPhone in DEFAULT_PRIMARY_PHONE or UPDATED_PRIMARY_PHONE
        defaultContactInfoShouldBeFound("primaryPhone.in=" + DEFAULT_PRIMARY_PHONE + "," + UPDATED_PRIMARY_PHONE);

        // Get all the contactInfoList where primaryPhone equals to UPDATED_PRIMARY_PHONE
        defaultContactInfoShouldNotBeFound("primaryPhone.in=" + UPDATED_PRIMARY_PHONE);
    }

    @Test
    @Transactional
    public void getAllContactInfosByPrimaryPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where primaryPhone is not null
        defaultContactInfoShouldBeFound("primaryPhone.specified=true");

        // Get all the contactInfoList where primaryPhone is null
        defaultContactInfoShouldNotBeFound("primaryPhone.specified=false");
    }

    @Test
    @Transactional
    public void getAllContactInfosBySecondaryPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where secondaryPhone equals to DEFAULT_SECONDARY_PHONE
        defaultContactInfoShouldBeFound("secondaryPhone.equals=" + DEFAULT_SECONDARY_PHONE);

        // Get all the contactInfoList where secondaryPhone equals to UPDATED_SECONDARY_PHONE
        defaultContactInfoShouldNotBeFound("secondaryPhone.equals=" + UPDATED_SECONDARY_PHONE);
    }

    @Test
    @Transactional
    public void getAllContactInfosBySecondaryPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where secondaryPhone in DEFAULT_SECONDARY_PHONE or UPDATED_SECONDARY_PHONE
        defaultContactInfoShouldBeFound("secondaryPhone.in=" + DEFAULT_SECONDARY_PHONE + "," + UPDATED_SECONDARY_PHONE);

        // Get all the contactInfoList where secondaryPhone equals to UPDATED_SECONDARY_PHONE
        defaultContactInfoShouldNotBeFound("secondaryPhone.in=" + UPDATED_SECONDARY_PHONE);
    }

    @Test
    @Transactional
    public void getAllContactInfosBySecondaryPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where secondaryPhone is not null
        defaultContactInfoShouldBeFound("secondaryPhone.specified=true");

        // Get all the contactInfoList where secondaryPhone is null
        defaultContactInfoShouldNotBeFound("secondaryPhone.specified=false");
    }

    @Test
    @Transactional
    public void getAllContactInfosByWorkPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where workPhone equals to DEFAULT_WORK_PHONE
        defaultContactInfoShouldBeFound("workPhone.equals=" + DEFAULT_WORK_PHONE);

        // Get all the contactInfoList where workPhone equals to UPDATED_WORK_PHONE
        defaultContactInfoShouldNotBeFound("workPhone.equals=" + UPDATED_WORK_PHONE);
    }

    @Test
    @Transactional
    public void getAllContactInfosByWorkPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where workPhone in DEFAULT_WORK_PHONE or UPDATED_WORK_PHONE
        defaultContactInfoShouldBeFound("workPhone.in=" + DEFAULT_WORK_PHONE + "," + UPDATED_WORK_PHONE);

        // Get all the contactInfoList where workPhone equals to UPDATED_WORK_PHONE
        defaultContactInfoShouldNotBeFound("workPhone.in=" + UPDATED_WORK_PHONE);
    }

    @Test
    @Transactional
    public void getAllContactInfosByWorkPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where workPhone is not null
        defaultContactInfoShouldBeFound("workPhone.specified=true");

        // Get all the contactInfoList where workPhone is null
        defaultContactInfoShouldNotBeFound("workPhone.specified=false");
    }

    @Test
    @Transactional
    public void getAllContactInfosByWorkPhoneExtIsEqualToSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where workPhoneExt equals to DEFAULT_WORK_PHONE_EXT
        defaultContactInfoShouldBeFound("workPhoneExt.equals=" + DEFAULT_WORK_PHONE_EXT);

        // Get all the contactInfoList where workPhoneExt equals to UPDATED_WORK_PHONE_EXT
        defaultContactInfoShouldNotBeFound("workPhoneExt.equals=" + UPDATED_WORK_PHONE_EXT);
    }

    @Test
    @Transactional
    public void getAllContactInfosByWorkPhoneExtIsInShouldWork() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where workPhoneExt in DEFAULT_WORK_PHONE_EXT or UPDATED_WORK_PHONE_EXT
        defaultContactInfoShouldBeFound("workPhoneExt.in=" + DEFAULT_WORK_PHONE_EXT + "," + UPDATED_WORK_PHONE_EXT);

        // Get all the contactInfoList where workPhoneExt equals to UPDATED_WORK_PHONE_EXT
        defaultContactInfoShouldNotBeFound("workPhoneExt.in=" + UPDATED_WORK_PHONE_EXT);
    }

    @Test
    @Transactional
    public void getAllContactInfosByWorkPhoneExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where workPhoneExt is not null
        defaultContactInfoShouldBeFound("workPhoneExt.specified=true");

        // Get all the contactInfoList where workPhoneExt is null
        defaultContactInfoShouldNotBeFound("workPhoneExt.specified=false");
    }

    @Test
    @Transactional
    public void getAllContactInfosByMobilePhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where mobilePhone equals to DEFAULT_MOBILE_PHONE
        defaultContactInfoShouldBeFound("mobilePhone.equals=" + DEFAULT_MOBILE_PHONE);

        // Get all the contactInfoList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultContactInfoShouldNotBeFound("mobilePhone.equals=" + UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    public void getAllContactInfosByMobilePhoneIsInShouldWork() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where mobilePhone in DEFAULT_MOBILE_PHONE or UPDATED_MOBILE_PHONE
        defaultContactInfoShouldBeFound("mobilePhone.in=" + DEFAULT_MOBILE_PHONE + "," + UPDATED_MOBILE_PHONE);

        // Get all the contactInfoList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultContactInfoShouldNotBeFound("mobilePhone.in=" + UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    public void getAllContactInfosByMobilePhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where mobilePhone is not null
        defaultContactInfoShouldBeFound("mobilePhone.specified=true");

        // Get all the contactInfoList where mobilePhone is null
        defaultContactInfoShouldNotBeFound("mobilePhone.specified=false");
    }

    @Test
    @Transactional
    public void getAllContactInfosBySkypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where skypeId equals to DEFAULT_SKYPE_ID
        defaultContactInfoShouldBeFound("skypeId.equals=" + DEFAULT_SKYPE_ID);

        // Get all the contactInfoList where skypeId equals to UPDATED_SKYPE_ID
        defaultContactInfoShouldNotBeFound("skypeId.equals=" + UPDATED_SKYPE_ID);
    }

    @Test
    @Transactional
    public void getAllContactInfosBySkypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where skypeId in DEFAULT_SKYPE_ID or UPDATED_SKYPE_ID
        defaultContactInfoShouldBeFound("skypeId.in=" + DEFAULT_SKYPE_ID + "," + UPDATED_SKYPE_ID);

        // Get all the contactInfoList where skypeId equals to UPDATED_SKYPE_ID
        defaultContactInfoShouldNotBeFound("skypeId.in=" + UPDATED_SKYPE_ID);
    }

    @Test
    @Transactional
    public void getAllContactInfosBySkypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where skypeId is not null
        defaultContactInfoShouldBeFound("skypeId.specified=true");

        // Get all the contactInfoList where skypeId is null
        defaultContactInfoShouldNotBeFound("skypeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllContactInfosByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        contactInfo.setUser(user);
        contactInfoRepository.saveAndFlush(contactInfo);
        Long userId = user.getId();

        // Get all the contactInfoList where user equals to userId
        defaultContactInfoShouldBeFound("userId.equals=" + userId);

        // Get all the contactInfoList where user equals to userId + 1
        defaultContactInfoShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContactInfoShouldBeFound(String filter) throws Exception {
        restContactInfoMockMvc.perform(get("/api/contact-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].primaryPhone").value(hasItem(DEFAULT_PRIMARY_PHONE.toString())))
            .andExpect(jsonPath("$.[*].secondaryPhone").value(hasItem(DEFAULT_SECONDARY_PHONE.toString())))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE.toString())))
            .andExpect(jsonPath("$.[*].workPhoneExt").value(hasItem(DEFAULT_WORK_PHONE_EXT.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].skypeId").value(hasItem(DEFAULT_SKYPE_ID.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContactInfoShouldNotBeFound(String filter) throws Exception {
        restContactInfoMockMvc.perform(get("/api/contact-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingContactInfo() throws Exception {
        // Get the contactInfo
        restContactInfoMockMvc.perform(get("/api/contact-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactInfo() throws Exception {
        // Initialize the database
        contactInfoService.save(contactInfo);

        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Update the contactInfo
        ContactInfo updatedContactInfo = contactInfoRepository.findById(contactInfo.getId()).get();
        // Disconnect from session so that the updates on updatedContactInfo are not directly saved in db
        em.detach(updatedContactInfo);
        updatedContactInfo
            .primaryPhone(UPDATED_PRIMARY_PHONE)
            .secondaryPhone(UPDATED_SECONDARY_PHONE)
            .workPhone(UPDATED_WORK_PHONE)
            .workPhoneExt(UPDATED_WORK_PHONE_EXT)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .skypeId(UPDATED_SKYPE_ID);

        restContactInfoMockMvc.perform(put("/api/contact-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactInfo)))
            .andExpect(status().isOk());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getPrimaryPhone()).isEqualTo(UPDATED_PRIMARY_PHONE);
        assertThat(testContactInfo.getSecondaryPhone()).isEqualTo(UPDATED_SECONDARY_PHONE);
        assertThat(testContactInfo.getWorkPhone()).isEqualTo(UPDATED_WORK_PHONE);
        assertThat(testContactInfo.getWorkPhoneExt()).isEqualTo(UPDATED_WORK_PHONE_EXT);
        assertThat(testContactInfo.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testContactInfo.getSkypeId()).isEqualTo(UPDATED_SKYPE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Create the ContactInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactInfoMockMvc.perform(put("/api/contact-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactInfo() throws Exception {
        // Initialize the database
        contactInfoService.save(contactInfo);

        int databaseSizeBeforeDelete = contactInfoRepository.findAll().size();

        // Get the contactInfo
        restContactInfoMockMvc.perform(delete("/api/contact-infos/{id}", contactInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactInfo.class);
        ContactInfo contactInfo1 = new ContactInfo();
        contactInfo1.setId(1L);
        ContactInfo contactInfo2 = new ContactInfo();
        contactInfo2.setId(contactInfo1.getId());
        assertThat(contactInfo1).isEqualTo(contactInfo2);
        contactInfo2.setId(2L);
        assertThat(contactInfo1).isNotEqualTo(contactInfo2);
        contactInfo1.setId(null);
        assertThat(contactInfo1).isNotEqualTo(contactInfo2);
    }
}
