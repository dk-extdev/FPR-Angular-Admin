package com.fpr360.story;

import com.fpr360.domain.User;
import com.fpr360.domain.UserType;
import com.fpr360.repository.UserRepository;
import com.fpr360.security.AuthoritiesConstants;
import com.fpr360.service.UserService;
import com.fpr360.web.rest.vm.ManagedUserVM;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Collections;

public abstract class AbstractUserStoryTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc restUserMockMvc;

    @Before
    public void setup() {
        cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).clear();
        cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).clear();

        // FIXME MockMvcBuilders.webAppContextSetup maybe would take a long time to run with more controllers added.
        // But this is needed so that role based test with @WithMockUser(authorities={}) works. In the future,
        // MockMvcBuilders.standaloneSetup maybe should be used instead.
        this.restUserMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @PostConstruct
    public void removeAllCsvData() {
        this.userRepository.deleteAll();
    }

    ManagedUserVM buildSampleData(UserType userType, String authority) {
        return buildSampleData(null, userType, authority);
    }

    ManagedUserVM buildSampleData(Long id, UserType userType, String authority) {
        return buildSampleData(null, userType.toString(), userType, authority);
    }

    ManagedUserVM buildSampleDataWithLogin(String login, UserType userType, String authority) {
        return buildSampleData(null, login, userType, authority);
    }

    ManagedUserVM buildSampleData(Long id, String login, UserType userType, String authority) {
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(id);
        managedUserVM.setLogin(login);
        managedUserVM.setPassword(userType.toString().toLowerCase());
        managedUserVM.setFirstName(userType.toString());
        managedUserVM.setLastName("class");
        managedUserVM.setEmail(login + "@localhost");
        managedUserVM.setImageUrl("http://placehold.it/50x50");
        managedUserVM.setLangKey("en");
        managedUserVM.setUserType(userType);
        managedUserVM.setAuthorities(Collections.singleton(authority));
        return managedUserVM;
    }

    /* authorities attribute needed because sometimes user object is not managed by persistence context, or not bring
       the authorities object graph at all */
    ManagedUserVM createManagedUserVMFrom(User user, String...authorities) {
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(user.getId());
        managedUserVM.setLogin(user.getLogin());
        managedUserVM.setPassword(user.getPassword());
        managedUserVM.setFirstName(user.getFirstName());
        managedUserVM.setLastName(user.getLastName());
        managedUserVM.setEmail(user.getEmail());
        managedUserVM.setActivated(user.getActivated());
        managedUserVM.setImageUrl(user.getImageUrl());
        managedUserVM.setLangKey(user.getLangKey());
        managedUserVM.setCreatedBy(user.getCreatedBy());
        managedUserVM.setCreatedDate(user.getCreatedDate());
        managedUserVM.setLastModifiedBy(user.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(user.getLastModifiedDate());
        managedUserVM.setUserType(user.getUserType());
        if (authorities == null) {
            managedUserVM.setAuthorities(Sets.newHashSet(AuthoritiesConstants.DATA_ENTRY_OPERATOR));
        } else {
            managedUserVM.setAuthorities(Sets.newHashSet(authorities));
        }

        return managedUserVM;
    }
}
