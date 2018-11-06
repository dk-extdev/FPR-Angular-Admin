package com.fpr360.web.rest;

import com.fpr360.Fpr360App;

import com.fpr360.domain.ClientUser;
import com.fpr360.domain.Client;
import com.fpr360.domain.User;
import com.fpr360.repository.ClientUserRepository;
import com.fpr360.service.ClientUserService;
import com.fpr360.web.rest.errors.ExceptionTranslator;
import com.fpr360.service.dto.ClientUserCriteria;
import com.fpr360.service.ClientUserQueryService;

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
 * Test class for the ClientUserResource REST controller.
 *
 * @see ClientUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class ClientUserResourceIntTest {

    @Autowired
    private ClientUserRepository clientUserRepository;
    
    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientUserQueryService clientUserQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientUserMockMvc;

    private ClientUser clientUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientUserResource clientUserResource = new ClientUserResource(clientUserService, clientUserQueryService);
        this.restClientUserMockMvc = MockMvcBuilders.standaloneSetup(clientUserResource)
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
    public static ClientUser createEntity(EntityManager em) {
        ClientUser clientUser = new ClientUser();
        // Add required entity
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        clientUser.setClient(client);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        clientUser.setUser(user);
        return clientUser;
    }

    @Before
    public void initTest() {
        clientUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientUser() throws Exception {
        int databaseSizeBeforeCreate = clientUserRepository.findAll().size();

        // Create the ClientUser
        restClientUserMockMvc.perform(post("/api/client-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientUser)))
            .andExpect(status().isCreated());

        // Validate the ClientUser in the database
        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeCreate + 1);
        ClientUser testClientUser = clientUserList.get(clientUserList.size() - 1);
    }

    @Test
    @Transactional
    public void createClientUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientUserRepository.findAll().size();

        // Create the ClientUser with an existing ID
        clientUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientUserMockMvc.perform(post("/api/client-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientUser)))
            .andExpect(status().isBadRequest());

        // Validate the ClientUser in the database
        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientUsers() throws Exception {
        // Initialize the database
        clientUserRepository.saveAndFlush(clientUser);

        // Get all the clientUserList
        restClientUserMockMvc.perform(get("/api/client-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientUser.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getClientUser() throws Exception {
        // Initialize the database
        clientUserRepository.saveAndFlush(clientUser);

        // Get the clientUser
        restClientUserMockMvc.perform(get("/api/client-users/{id}", clientUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientUser.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllClientUsersByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        clientUser.setClient(client);
        clientUserRepository.saveAndFlush(clientUser);
        Long clientId = client.getId();

        // Get all the clientUserList where client equals to clientId
        defaultClientUserShouldBeFound("clientId.equals=" + clientId);

        // Get all the clientUserList where client equals to clientId + 1
        defaultClientUserShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }


    @Test
    @Transactional
    public void getAllClientUsersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        clientUser.setUser(user);
        clientUserRepository.saveAndFlush(clientUser);
        Long userId = user.getId();

        // Get all the clientUserList where user equals to userId
        defaultClientUserShouldBeFound("userId.equals=" + userId);

        // Get all the clientUserList where user equals to userId + 1
        defaultClientUserShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClientUserShouldBeFound(String filter) throws Exception {
        restClientUserMockMvc.perform(get("/api/client-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientUser.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClientUserShouldNotBeFound(String filter) throws Exception {
        restClientUserMockMvc.perform(get("/api/client-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingClientUser() throws Exception {
        // Get the clientUser
        restClientUserMockMvc.perform(get("/api/client-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientUser() throws Exception {
        // Initialize the database
        clientUserService.save(clientUser);

        int databaseSizeBeforeUpdate = clientUserRepository.findAll().size();

        // Update the clientUser
        ClientUser updatedClientUser = clientUserRepository.findById(clientUser.getId()).get();
        // Disconnect from session so that the updates on updatedClientUser are not directly saved in db
        em.detach(updatedClientUser);

        restClientUserMockMvc.perform(put("/api/client-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientUser)))
            .andExpect(status().isOk());

        // Validate the ClientUser in the database
        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeUpdate);
        ClientUser testClientUser = clientUserList.get(clientUserList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingClientUser() throws Exception {
        int databaseSizeBeforeUpdate = clientUserRepository.findAll().size();

        // Create the ClientUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientUserMockMvc.perform(put("/api/client-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientUser)))
            .andExpect(status().isBadRequest());

        // Validate the ClientUser in the database
        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientUser() throws Exception {
        // Initialize the database
        clientUserService.save(clientUser);

        int databaseSizeBeforeDelete = clientUserRepository.findAll().size();

        // Get the clientUser
        restClientUserMockMvc.perform(delete("/api/client-users/{id}", clientUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientUser.class);
        ClientUser clientUser1 = new ClientUser();
        clientUser1.setId(1L);
        ClientUser clientUser2 = new ClientUser();
        clientUser2.setId(clientUser1.getId());
        assertThat(clientUser1).isEqualTo(clientUser2);
        clientUser2.setId(2L);
        assertThat(clientUser1).isNotEqualTo(clientUser2);
        clientUser1.setId(null);
        assertThat(clientUser1).isNotEqualTo(clientUser2);
    }
}
