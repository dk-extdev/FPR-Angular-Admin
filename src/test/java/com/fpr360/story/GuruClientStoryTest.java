package com.fpr360.story;

import com.fpr360.Fpr360App;
import com.fpr360.domain.*;
import com.fpr360.domain.enumeration.GuruClientLevel;
import com.fpr360.repository.ClientRepository;
import com.fpr360.repository.GuruClientRepository;
import com.fpr360.repository.IndustryRepository;
import com.fpr360.security.AuthoritiesConstants;
import com.fpr360.service.UserService;
import com.fpr360.web.rest.TestUtil;
import com.fpr360.web.rest.vm.ManagedUserVM;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class GuruClientStoryTest {

    @Autowired
    private UserService userService;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GuruClientRepository guruClientRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc restGuruClientMockMvc;

    private ManagedUserVM buildDefaultGuru() {
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setLogin("guru");
        managedUserVM.setPassword("password");
        managedUserVM.setFirstName("first");
        managedUserVM.setLastName("name");
        managedUserVM.setEmail("guru@localhost");
        managedUserVM.setImageUrl("http://placehold.it/50x50");
        managedUserVM.setLangKey("en");
        managedUserVM.setUserType(UserType.GURU);
        managedUserVM.setAuthorities(Collections.singleton(AuthoritiesConstants.CASE_RESOLUTION_ANALYST));
        return managedUserVM;
    }

    private Client buildDefaultClient() {
        return new Client()
                .name("amazon.com")
                .description("Amazon, the largest marketplace.")
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

    private Industry getDefaultIndustry() {
        return industryRepository.save(new Industry()
                .name("E-Commerce")
                .description("An e-commerce industry"));
    }

    @Before
    public void setup() {
        restGuruClientMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.OPS_MANAGER)
    public void asOpsManager_whenAssignPrimary_withExistingClient_andExistingGuru_thenShouldCreated() throws Exception {
        ManagedUserVM vm = buildDefaultGuru();
        User guru = this.userService.createUser(vm);

        Client client = clientRepository.save(
                buildDefaultClient().industry(getDefaultIndustry())
        );

        GuruClient guruClient = new GuruClient()
                .level(GuruClientLevel.PRIMARY)
                .guru(guru)
                .client(client);

        restGuruClientMockMvc.perform(post("/api/guru-clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(guruClient)))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.OPS_MANAGER)
    public void asOpsManager_whenAssignMultiplePrimarySecondaryClient_thenShouldOk() throws Exception {
        ManagedUserVM vm = buildDefaultGuru();
        User guru = this.userService.createUser(vm);

        Client client1 = clientRepository.save(buildDefaultClient().industry(getDefaultIndustry()));
        Client client2 = clientRepository.save(buildDefaultClient().industry(getDefaultIndustry()));

        long beforeActivity = guruClientRepository.count();

        List<GuruClient> request = Arrays.asList(
                new GuruClient().client(client1).guru(guru),
                new GuruClient().client(client2).guru(guru)
        );

        restGuruClientMockMvc.perform(post("/api/guru-clients/bulk-insert")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(2)));

        long afterActivity = guruClientRepository.count();

        assertThat(afterActivity).isEqualTo(beforeActivity + 2);
    }
}
