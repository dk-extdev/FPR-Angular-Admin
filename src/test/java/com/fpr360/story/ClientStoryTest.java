package com.fpr360.story;

import com.fpr360.Fpr360App;
import com.fpr360.domain.Client;
import com.fpr360.domain.Industry;
import com.fpr360.repository.ClientRepository;
import com.fpr360.repository.IndustryRepository;
import com.fpr360.security.AuthoritiesConstants;
import com.fpr360.service.ClientService;
import com.fpr360.web.rest.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class ClientStoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc restClientMockMvc;
    private Industry managedIndustry; // Brand new, fresh, managed industry entity as many2one reference.

    private Client buildDefaultClient() {
        return new Client()
                .name("amazon.com")
                .description("Amazon, the largest marketplace, is a chargebackgurus.com client.")
                .address("410 Terry Ave. North")
                .city("Seattle")
                .state("WA")
                .postalCode("98109")
                .country("United States")
                .phoneNumber("98109-5210")
                .email("contact@amazon.com")
                .website("https://www.amazon.com")
                .active(true)
                .currency("USD");
    }

    // Make sure that many2one references is exist, but un-managed by any persistence context and represent real world
    // usage (UI most of the time only send an entity id to many2one relation, not entire object graph).
    private Industry getDetachedIndustry() {
        Industry toPublish = new Industry();
        toPublish.setId(managedIndustry.getId());
        return toPublish;
    }

    @Before
    public void setup() {
        // FIXME MockMvcBuilders.webAppContextSetup maybe would take a long time to run with more controllers added.
        // But this is needed so that role based test with @WithMockUser(authorities={}) works. In the future,
        // MockMvcBuilders.standaloneSetup maybe should be used instead.
        this.restClientMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @PostConstruct
    public void postConstruct() {
        managedIndustry = industryRepository.save(new Industry()
                .name("E-Commerce")
                .description("An e-commerce industry"));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.CLIENT_SERVICE_MANAGER)
    public void asClientServiceMgr_whenAddNewClient_thenShouldBeCreated() throws Exception {
        Client toSave = buildDefaultClient();
        toSave.setIndustry(getDetachedIndustry());

        long beforeActivity = clientRepository.count();
        long industryBeforeActivity = industryRepository.count();

        restClientMockMvc.perform(post("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(toSave)))
                .andExpect(status().isCreated());

        long afterActivity = clientRepository.count();
        long industryAfterActivity = this.industryRepository.count();
        assertThat(beforeActivity).isEqualTo(afterActivity - 1);
        assertThat(industryBeforeActivity).isEqualTo(industryAfterActivity); // Make sure industry not persisted
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ANONYMOUS)
    public void asAnonymous_whenAddNewClient_thenShouldBeForbidden() throws Exception {
        Client toSave = buildDefaultClient();
        toSave.setIndustry(getDetachedIndustry());

        restClientMockMvc.perform(post("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(toSave)))
                .andExpect(status().isForbidden());
    }

    // Note that "Activate / Deactivate a Client Company" is also considered as an "update" action.
    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.CLIENT_SERVICE_MANAGER)
    public void asClientServiceMgr_whenUpdateNewClient_thenShouldBeOk() throws Exception {
        Client toSave = buildDefaultClient();
        toSave.setIndustry(getDetachedIndustry());
        Client toUpdate = clientService.save(toSave);

        long beforeActivity = clientRepository.count();
        long industryBeforeActivity = industryRepository.count();

        toUpdate.setName("bestbuy.com");
        toUpdate.setDescription("I acquired you, amazon..");
        toUpdate.setActive(false);

        restClientMockMvc.perform(put("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(toUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("bestbuy.com"))
                .andExpect(jsonPath("$.description").value("I acquired you, amazon.."))
                .andExpect(jsonPath("$.active").value(false));

        long afterActivity = clientRepository.count();
        long industryAfterActivity = this.industryRepository.count();
        assertThat(beforeActivity).isEqualTo(afterActivity);
        assertThat(industryBeforeActivity).isEqualTo(industryAfterActivity); // Make sure industry not persisted
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.CLIENT_SERVICE_MANAGER)
    public void asClientServiceManager_whenListAllClient_thenOkAndReturnAllClients() throws Exception {
        clientService.save(buildDefaultClient().industry(getDetachedIndustry()));
        clientService.save(buildDefaultClient().industry(getDetachedIndustry()));
        clientService.save(buildDefaultClient().industry(getDetachedIndustry()));
        clientService.save(buildDefaultClient().industry(getDetachedIndustry()));

        restClientMockMvc.perform(get("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ANONYMOUS)
    public void asAnonymous_whenListAllClient_thenShouldBeForbidden() throws Exception {
        clientService.save(buildDefaultClient().industry(getDetachedIndustry()));
        clientService.save(buildDefaultClient().industry(getDetachedIndustry()));

        restClientMockMvc.perform(get("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }
}
