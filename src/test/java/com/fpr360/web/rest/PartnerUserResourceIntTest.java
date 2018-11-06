package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.PartnerUser;
import com.fpr360.domain.Partner;
import com.fpr360.domain.User;
import com.fpr360.repository.PartnerUserRepository;
import com.fpr360.service.PartnerUserService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.dto.PartnerUserCriteria;
import com.fpr360.service.PartnerUserQueryService;

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
 * Test class for the PartnerUserResource REST controller.
 *
 * @see PartnerUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class PartnerUserResourceIntTest {

    @Autowired
    private PartnerUserRepository partnerUserRepository;
    
    @Autowired
    private PartnerUserService partnerUserService;

    @Autowired
    private PartnerUserQueryService partnerUserQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartnerUserMockMvc;

    private PartnerUser partnerUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerUserResource partnerUserResource = new PartnerUserResource(partnerUserService, partnerUserQueryService);
        this.restPartnerUserMockMvc = MockMvcBuilders.standaloneSetup(partnerUserResource)
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
    public static PartnerUser createEntity(EntityManager em) {
        PartnerUser partnerUser = new PartnerUser();
        // Add required entity
        Partner partner = PartnerResourceIntTest.createEntity(em);
        em.persist(partner);
        em.flush();
        partnerUser.setPartner(partner);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        partnerUser.setUser(user);
        return partnerUser;
    }

    @Before
    public void initTest() {
        partnerUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartnerUser() throws Exception {
        int databaseSizeBeforeCreate = partnerUserRepository.findAll().size();

        // Create the PartnerUser
        restPartnerUserMockMvc.perform(post("/api/partner-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerUser)))
            .andExpect(status().isCreated());

        // Validate the PartnerUser in the database
        List<PartnerUser> partnerUserList = partnerUserRepository.findAll();
        assertThat(partnerUserList).hasSize(databaseSizeBeforeCreate + 1);
        PartnerUser testPartnerUser = partnerUserList.get(partnerUserList.size() - 1);
    }

    @Test
    @Transactional
    public void createPartnerUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerUserRepository.findAll().size();

        // Create the PartnerUser with an existing ID
        partnerUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerUserMockMvc.perform(post("/api/partner-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerUser)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerUser in the database
        List<PartnerUser> partnerUserList = partnerUserRepository.findAll();
        assertThat(partnerUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPartnerUsers() throws Exception {
        // Initialize the database
        partnerUserRepository.saveAndFlush(partnerUser);

        // Get all the partnerUserList
        restPartnerUserMockMvc.perform(get("/api/partner-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerUser.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPartnerUser() throws Exception {
        // Initialize the database
        partnerUserRepository.saveAndFlush(partnerUser);

        // Get the partnerUser
        restPartnerUserMockMvc.perform(get("/api/partner-users/{id}", partnerUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partnerUser.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllPartnerUsersByPartnerIsEqualToSomething() throws Exception {
        // Initialize the database
        Partner partner = PartnerResourceIntTest.createEntity(em);
        em.persist(partner);
        em.flush();
        partnerUser.setPartner(partner);
        partnerUserRepository.saveAndFlush(partnerUser);
        Long partnerId = partner.getId();

        // Get all the partnerUserList where partner equals to partnerId
        defaultPartnerUserShouldBeFound("partnerId.equals=" + partnerId);

        // Get all the partnerUserList where partner equals to partnerId + 1
        defaultPartnerUserShouldNotBeFound("partnerId.equals=" + (partnerId + 1));
    }


    @Test
    @Transactional
    public void getAllPartnerUsersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        partnerUser.setUser(user);
        partnerUserRepository.saveAndFlush(partnerUser);
        Long userId = user.getId();

        // Get all the partnerUserList where user equals to userId
        defaultPartnerUserShouldBeFound("userId.equals=" + userId);

        // Get all the partnerUserList where user equals to userId + 1
        defaultPartnerUserShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPartnerUserShouldBeFound(String filter) throws Exception {
        restPartnerUserMockMvc.perform(get("/api/partner-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerUser.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPartnerUserShouldNotBeFound(String filter) throws Exception {
        restPartnerUserMockMvc.perform(get("/api/partner-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPartnerUser() throws Exception {
        // Get the partnerUser
        restPartnerUserMockMvc.perform(get("/api/partner-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartnerUser() throws Exception {
        // Initialize the database
        partnerUserService.save(partnerUser);

        int databaseSizeBeforeUpdate = partnerUserRepository.findAll().size();

        // Update the partnerUser
        PartnerUser updatedPartnerUser = partnerUserRepository.findById(partnerUser.getId()).get();
        // Disconnect from session so that the updates on updatedPartnerUser are not directly saved in db
        em.detach(updatedPartnerUser);

        restPartnerUserMockMvc.perform(put("/api/partner-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartnerUser)))
            .andExpect(status().isOk());

        // Validate the PartnerUser in the database
        List<PartnerUser> partnerUserList = partnerUserRepository.findAll();
        assertThat(partnerUserList).hasSize(databaseSizeBeforeUpdate);
        PartnerUser testPartnerUser = partnerUserList.get(partnerUserList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPartnerUser() throws Exception {
        int databaseSizeBeforeUpdate = partnerUserRepository.findAll().size();

        // Create the PartnerUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerUserMockMvc.perform(put("/api/partner-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerUser)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerUser in the database
        List<PartnerUser> partnerUserList = partnerUserRepository.findAll();
        assertThat(partnerUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartnerUser() throws Exception {
        // Initialize the database
        partnerUserService.save(partnerUser);

        int databaseSizeBeforeDelete = partnerUserRepository.findAll().size();

        // Get the partnerUser
        restPartnerUserMockMvc.perform(delete("/api/partner-users/{id}", partnerUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PartnerUser> partnerUserList = partnerUserRepository.findAll();
        assertThat(partnerUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerUser.class);
        PartnerUser partnerUser1 = new PartnerUser();
        partnerUser1.setId(1L);
        PartnerUser partnerUser2 = new PartnerUser();
        partnerUser2.setId(partnerUser1.getId());
        assertThat(partnerUser1).isEqualTo(partnerUser2);
        partnerUser2.setId(2L);
        assertThat(partnerUser1).isNotEqualTo(partnerUser2);
        partnerUser1.setId(null);
        assertThat(partnerUser1).isNotEqualTo(partnerUser2);
    }
}
