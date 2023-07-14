package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.BmiCalculatorService;
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

@WebMvcTest(BmiController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class BmiControllerTest {

    private final MockMvc mockMvc;
    @MockBean
    private BmiCalculatorService bmiCalculatorService;

    @Test
    @WithMockUser
    void returnBmiViewWithBmiAttributeWorksCorrectly() throws Exception {
        // given
        String username = "testUser";
        String bmiValue = "22.5";

        Principal principal = () -> username;

        when(bmiCalculatorService.calculateAndInterpretBMI(username))
                .thenReturn(bmiValue);

        // when, then
        mockMvc.perform(get("/bmi")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("bmi"))
                .andExpect(model().attributeExists("bmi"))
                .andExpect(model().attribute("bmi", bmiValue));
    }

    @Test
    @WithMockUser
    void returnBmiViewWithNoBmiAttributeWhenCalculationFailsWorksCorrectly() throws Exception {
        // given
        String username = "testUser";

        Principal principal = () -> username;

        when(bmiCalculatorService.calculateAndInterpretBMI(username))
                .thenReturn(null);

        // when, then
        mockMvc.perform(get("/bmi")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("bmi"))
                .andExpect(model().attributeDoesNotExist("bmi"));
    }
}