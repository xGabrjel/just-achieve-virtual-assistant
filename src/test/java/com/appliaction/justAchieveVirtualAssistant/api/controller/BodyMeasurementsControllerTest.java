package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.BodyMeasurementsDTO;
import com.appliaction.justAchieveVirtualAssistant.business.BodyMeasurementsService;
import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.security.Principal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BodyMeasurementsController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class BodyMeasurementsControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private BodyMeasurementsService bodyMeasurementsService;
    @MockBean
    private UserProfileService userProfileService;

    @Test
    @WithMockUser
    void bodyMeasurementsHomePageWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/measurements"))
                .andExpect(status().isOk())
                .andExpect(view().name("measurements"));
    }
    @Test
    @WithMockUser
    void returnMeasurementsViewWorksCorrectly() throws Exception {
        //given
        String username = "testUser";
        String date = "2022-01-01";
        BodyMeasurementsDTO bodyMeasurementsDTO = new BodyMeasurementsDTO();
        Principal principal = () -> username;

        when(bodyMeasurementsService.findFinalBodyMeasurementsDTO(date, username))
                .thenReturn(bodyMeasurementsDTO);

        //when, then
        mockMvc.perform(get("/measurements/available-measurement")
                .param("date", date)
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("measurements"))
                .andExpect(model().attributeExists("measurements"))
                .andExpect(model().attribute("measurements", bodyMeasurementsDTO));
    }

    @Test
    @WithMockUser
    void redirectToMeasurementsWithSuccessParamWhenValidWorksCorrectly() throws Exception {
        //given
        String username = "testUser";
        BodyMeasurementsDTO bodyMeasurementsDTO = new BodyMeasurementsDTO();
        UserProfile userProfile = DomainFixtures.someUserProfile();

        Principal principal = () -> username;

        when(userProfileService.findByUsername(username))
                .thenReturn(userProfile);

        //when, then
        mockMvc.perform(post("/measurements/new-measurement")
                .flashAttr("bodyMeasurementsDTO", bodyMeasurementsDTO)
                .principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/measurements?success"));
    }

    @Test
    @WithMockUser
    void returnMeasurementsViewWithErrorsWhenInvalidWorksCorrectly() throws Exception {
        //given
        String username = "testUser";
        BodyMeasurementsDTO bodyMeasurementsDTO = new BodyMeasurementsDTO();
        bodyMeasurementsDTO.setCurrentWeight(BigDecimal.valueOf(0));
        UserProfile userProfile = DomainFixtures.someUserProfile();

        Principal principal = () -> username;

        when(userProfileService.findByUsername(username))
                .thenReturn(userProfile);

        //when, then
        mockMvc.perform(post("/measurements/new-measurement")
                .flashAttr("bodyMeasurementsDTO", bodyMeasurementsDTO)
                .principal(principal))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("default-error"));
    }
}