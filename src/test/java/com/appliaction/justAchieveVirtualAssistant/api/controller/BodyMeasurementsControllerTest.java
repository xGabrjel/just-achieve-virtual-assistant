package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.BodyMeasurementsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.BodyMeasurementsMapper;
import com.appliaction.justAchieveVirtualAssistant.business.BodyMeasurementsService;
import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(BodyMeasurementsController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class BodyMeasurementsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BodyMeasurementsService bodyMeasurementsService;

    @MockBean
    private BodyMeasurementsMapper bodyMeasurementsMapper;

    @MockBean
    private UserProfileService userProfileService;

    @Test
    @WithMockUser
    void bodyMeasurementsHomePageWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/measurements"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("measurements"));
    }
    @Test
    @WithMockUser
    void getMeasurementsReturnsMeasurementsViewWorksCorrectly() throws Exception {
        // given
        String username = "testUser";
        String date = "2022-01-01";
        LocalDate parseResult = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

        BodyMeasurementsDTO bodyMeasurementsDTO = new BodyMeasurementsDTO();
        BodyMeasurements bodyMeasurements = DomainFixtures.someBodyMeasurements();

        Principal principal = () -> username;

        when(userProfileService.findByUsername(username)).thenReturn(new UserProfile());
        when(bodyMeasurementsService.findByDateAndProfileId(parseResult, new UserProfile())).thenReturn(bodyMeasurements);
        when(bodyMeasurementsMapper.map(bodyMeasurements)).thenReturn(bodyMeasurementsDTO);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/measurements/get")
                .param("date", date)
                .principal(principal))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("measurements"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("measurements"))
                .andExpect(MockMvcResultMatchers.model().attribute("measurements", bodyMeasurementsDTO));
    }

    @Test
    @WithMockUser
    void postMeasurementsRedirectsToMeasurementsWithSuccessParamWhenValidWorksCorrectly() throws Exception {
        // given
        String username = "testUser";
        BodyMeasurementsDTO bodyMeasurementsDTO = new BodyMeasurementsDTO();
        UserProfile userProfile = DomainFixtures.someUserProfile();

        Principal principal = () -> username;

        when(userProfileService.findByUsername(username)).thenReturn(userProfile);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/measurements/add")
                .flashAttr("bodyMeasurementsDTO", bodyMeasurementsDTO)
                .principal(principal))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/measurements?success"));
    }

    @Test
    @WithMockUser
    void postMeasurementsReturnsMeasurementsViewWithErrorsWhenInvalidWorksCorrectly() throws Exception {
        // given
        String username = "testUser";
        BodyMeasurementsDTO bodyMeasurementsDTO = new BodyMeasurementsDTO();
        bodyMeasurementsDTO.setCurrentWeight(BigDecimal.valueOf(0));
        UserProfile userProfile = DomainFixtures.someUserProfile();

        Principal principal = () -> username;

        when(userProfileService.findByUsername(username)).thenReturn(userProfile);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/measurements/add")
                .flashAttr("bodyMeasurementsDTO", bodyMeasurementsDTO)
                .principal(principal))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("default-error"));
    }
}