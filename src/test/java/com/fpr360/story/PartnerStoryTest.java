package com.fpr360.story;

import com.fpr360.Fpr360App;
import com.fpr360.domain.Client;
import com.fpr360.domain.Industry;
import com.fpr360.domain.Partner;
import com.fpr360.domain.enumeration.PartnerType;
import com.fpr360.repository.IndustryRepository;
import com.fpr360.repository.PartnerRepository;
import com.fpr360.security.AuthoritiesConstants;
import com.fpr360.service.PartnerService;
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
import javax.mail.Part;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class PartnerStoryTest {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc restClientMockMvc;
    private Industry managedIndustry; // Brand new, fresh, managed industry entity as many2one reference.

    private Partner buildDefaultPartner() {
        return new Partner()
                .name("stripe")
                .description("stripe payment gateway")
                .address("510 Townsend St")
                .city("San Fransisco")
                .state("CA")
                .postalCode("94103")
                .country("United States")
                .phoneNumber("99999")
                .email("contact@stripe.com")
                .website("https://www.stripe.com")
                .active(true)
                .currency("USD")
                .type(PartnerType.REFERRAL);
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
    public void asClientServiceMgr_whenAddNewPartner_thenShouldBeCreated() throws Exception {
        Partner toSave = buildDefaultPartner();
        toSave.setIndustry(getDetachedIndustry());

        long beforeActivity = partnerRepository.count();
        long industryBeforeActivity = industryRepository.count();

        restClientMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(toSave)))
                .andExpect(status().isCreated());

        long afterActivity = partnerRepository.count();
        long industryAfterActivity = this.industryRepository.count();
        assertThat(beforeActivity).isEqualTo(afterActivity - 1);
        assertThat(industryBeforeActivity).isEqualTo(industryAfterActivity); // Make sure industry not persisted
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.CLIENT)
    public void asClient_whenAddNewPartner_thenShouldBeForbidden() throws Exception {
        Partner toSave = buildDefaultPartner();
        toSave.setIndustry(getDetachedIndustry());

        restClientMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(toSave)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void adAdmin_whenUpdateNewClient_thenShouldBeOk() throws Exception {
        Partner toSave = buildDefaultPartner();
        toSave.setIndustry(getDetachedIndustry());
        Partner toUpdate = partnerService.save(toSave);

        long beforeActivity = partnerRepository.count();
        long industryBeforeActivity = industryRepository.count();

        toUpdate.setName("brain tree payments");
        toUpdate.setDescription("I owned by paypal.");
        toUpdate.setWebsite("https://www.braintreepayments.com");
        toUpdate.setActive(false);

        restClientMockMvc.perform(put("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(toUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("brain tree payments"))
                .andExpect(jsonPath("$.description").value("I owned by paypal."))
                .andExpect(jsonPath("$.website").value("https://www.braintreepayments.com"))
                .andExpect(jsonPath("$.active").value(false));

        long afterActivity = partnerRepository.count();
        long industryAfterActivity = this.industryRepository.count();
        assertThat(beforeActivity).isEqualTo(afterActivity);
        assertThat(industryBeforeActivity).isEqualTo(industryAfterActivity); // Make sure industry not persisted
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.OPS_MANAGER)
    public void opsManager_whenListAllPartners_thenOkAndReturnAllPartners() throws Exception {
        partnerService.save(buildDefaultPartner().industry(getDetachedIndustry()));
        partnerService.save(buildDefaultPartner().industry(getDetachedIndustry()));
        partnerService.save(buildDefaultPartner().industry(getDetachedIndustry()));

        restClientMockMvc.perform(get("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.CLIENT)
    public void asClient_whenListAllClient_thenShouldBeForbidden() throws Exception {
        partnerService.save(buildDefaultPartner().industry(getDetachedIndustry()));

        restClientMockMvc.perform(get("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }
}
