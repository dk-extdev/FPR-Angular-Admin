package com.fpr360.story;

import com.fpr360.Fpr360App;
import com.fpr360.domain.User;
import com.fpr360.domain.UserType;
import com.fpr360.security.AuthoritiesConstants;
import com.fpr360.web.rest.TestUtil;
import com.fpr360.web.rest.vm.ManagedUserVM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fpr360App.class)
public class UserAsPartnerStoryTest extends  AbstractUserStoryTest {

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void asAdmin_whenAddNewPartnerAsUser_thenShouldCreated() throws Exception {
        int beforeActivity = userRepository.findAll().size();
        ManagedUserVM managedUserVM = buildSampleData(UserType.PARTNER, AuthoritiesConstants.PARTNER);

        restUserMockMvc.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
                .andExpect(status().isCreated());

        int afterActivity = userRepository.findAll().size();

        assertThat(beforeActivity).isEqualTo(afterActivity - 1);
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void asAdmin_whenDeactivatedPartner_thenShouldOk() throws Exception {
        ManagedUserVM managedUserVM = buildSampleData(UserType.PARTNER, AuthoritiesConstants.PARTNER);
        User user = this.userService.createUser(managedUserVM);
        assertThat(user.getActivated()).isEqualTo(true);

        int beforeActivity = userRepository.findAll().size();

        managedUserVM = createManagedUserVMFrom(user, AuthoritiesConstants.PARTNER);
        managedUserVM.setActivated(false);

        restUserMockMvc.perform(put("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activated").value(false));

        int afterActivity = userRepository.findAll().size();

        assertThat(beforeActivity).isEqualTo(afterActivity);
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void asAdmin_whenListAllPartner_thenReturnPartnersOnly() throws Exception {
        ManagedUserVM user1 = buildSampleDataWithLogin("user1", UserType.CLIENT, AuthoritiesConstants.CLIENT);
        ManagedUserVM user2 = buildSampleDataWithLogin("user2", UserType.PARTNER, AuthoritiesConstants.PARTNER);
        ManagedUserVM user3 = buildSampleDataWithLogin("user3", UserType.GURU, AuthoritiesConstants.CASE_RESOLUTION_ANALYST);

        this.userService.createUser(user1);
        this.userService.createUser(user2);
        this.userService.createUser(user3);

        restUserMockMvc.perform(get("/api/users?type=PARTNER")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
