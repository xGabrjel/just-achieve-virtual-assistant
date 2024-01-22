package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.business.MacronutrientsCalculatorService;
import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(MacronutrientsController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class MacronutrientsControllerTest {

    private final MockMvc mockMvc;
    @MockBean
    private MacronutrientsCalculatorService macronutrientsCalculatorService;

    @Test
    @WithMockUser
    void returnMacronutrientsHomePageViewCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/macronutrients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("macronutrients"));
    }

    @Test
    @WithMockUser
    void returnMacronutrientsViewCorrectly() throws Exception {
        //given
        String username = "testUser";
        ActivityLevel activityLevel = ActivityLevel.MODERATELY_ACTIVE;
        Map<String, BigDecimal> macro = new HashMap<>();
        macro.put("bmr", BigDecimal.valueOf(3500));
        macro.put("protein", BigDecimal.valueOf(120));
        macro.put("carbohydrates", BigDecimal.valueOf(400));
        macro.put("fats", BigDecimal.valueOf(90));

        Principal principal = () -> username;

        when(macronutrientsCalculatorService.calculateHealthyMacronutrientsValues(username, activityLevel))
                .thenReturn(macro);

        //when, then
        mockMvc.perform(get("/macronutrients/{activityLevel}", activityLevel)
                .param("activityLevel", activityLevel.toString())
                .principal(principal))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("macronutrients"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("macro"))
                .andExpect(MockMvcResultMatchers.model().attribute("macro", macro));
    }
}