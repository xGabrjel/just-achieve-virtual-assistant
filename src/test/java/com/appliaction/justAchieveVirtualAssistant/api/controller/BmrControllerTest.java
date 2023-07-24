package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.BmrCalculatorService;
import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BmrController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class BmrControllerTest {

    private final MockMvc mockMvc;
    @MockBean
    private final BmrCalculatorService bmrCalculatorService;

    @Test
    void bmrHomePageWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/bmr"))
                .andExpect(status().isOk())
                .andExpect(view().name("bmr"));
    }

    @Test
    @WithMockUser
    void returnBmrViewWithBmrAttributeWorksCorrectly() throws Exception {
        // given
        String username = "testUser";
        ActivityLevel activityLevel = ActivityLevel.MODERATELY_ACTIVE;
        BigDecimal bmrValue = BigDecimal.valueOf(2000);

        Principal principal = () -> username;

        when(bmrCalculatorService.calculateActivityIncludedBMR(username, activityLevel))
                .thenReturn(bmrValue);

        // when, then
        mockMvc.perform(get("/bmr/{activityLevel}", activityLevel)
                .param("activityLevel", activityLevel.toString())
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("bmr"))
                .andExpect(model().attributeExists("bmr"))
                .andExpect(model().attribute("bmr", bmrValue));
    }

    @Test
    @WithMockUser
    void returnBmrViewWithNoBmrAttributeWhenCalculationFailsWorksCorrectly() throws Exception {
        // given
        String username = "testUser";
        ActivityLevel activityLevel = ActivityLevel.MODERATELY_ACTIVE;

        Principal principal = () -> username;

        when(bmrCalculatorService.calculateActivityIncludedBMR(username, activityLevel))
                .thenReturn(null);

        // when, then
        mockMvc.perform(get("/bmr/{activityLevel}", activityLevel)
                .param("activityLevel", activityLevel.toString())
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("bmr"))
                .andExpect(model().attributeDoesNotExist("bmr"));
    }
}