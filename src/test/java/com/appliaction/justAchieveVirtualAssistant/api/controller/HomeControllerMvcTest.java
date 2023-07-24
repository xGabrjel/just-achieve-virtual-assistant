package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class HomeControllerMvcTest {

    private final MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    @Test
    @WithMockUser
    void homePageWorksCorrectly() throws Exception {
        //given
        String username = "testuser";
        Principal principal = () -> username;
        UserProfile completedProfile = new UserProfile();

        when(userProfileService.findByUsername(username))
                .thenReturn(completedProfile);

        // when, then
        mockMvc.perform(get("/")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("isProfileCompleted", true));
    }

    @Test
    void homePageWorksCorrectlyWithEmptyProfile() throws Exception {
        //given
        String username = "testuser";
        UserProfile emptyProfile = null;

        when(userProfileService.findByUsername(username))
                .thenReturn(emptyProfile);

        // when, then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("isProfileCompleted", false));
    }

    @Test
    void loginPageWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
    @Test
    void invalidAndExpiredTokenErrorWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }
}