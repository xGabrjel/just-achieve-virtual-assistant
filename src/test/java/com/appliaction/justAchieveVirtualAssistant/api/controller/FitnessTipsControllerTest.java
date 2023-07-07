package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.DietGoalsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.FitnessTipsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.FitnessTipsMapper;
import com.appliaction.justAchieveVirtualAssistant.business.FitnessTipsGeneratorService;
import com.appliaction.justAchieveVirtualAssistant.domain.FitnessTips;
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

@WebMvcTest(FitnessTipsController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class FitnessTipsControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private FitnessTipsGeneratorService fitnessTipsGeneratorService;

    @MockBean
    private FitnessTipsMapper fitnessTipsMapper;

    @Test
    @WithMockUser
    void tipsReturnsTipsViewCorrectly() throws Exception {
        // given
        String username = "testUser";
        DietGoalsDTO dietGoalsDTO = new DietGoalsDTO();
        dietGoalsDTO.setDietGoal("FAT LOSS");
        FitnessTipsDTO tipsDTO = new FitnessTipsDTO(dietGoalsDTO, "Just eat less");

        Principal principal = () -> username;

        when(fitnessTipsGeneratorService.getRandomTipForDietGoal(username)).thenReturn(new FitnessTips());
        when(fitnessTipsMapper.map(new FitnessTips())).thenReturn(tipsDTO);

        // when, then
        mockMvc.perform(get("/tips")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("tips"))
                .andExpect(model().attributeExists("dietGoal", "tip"))
                .andExpect(model().attribute("dietGoal", tipsDTO.getDietGoal().getDietGoal()))
                .andExpect(model().attribute("tip", tipsDTO.getTip()));
    }
}