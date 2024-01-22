package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MacronutrientsCalculatorServiceTest {

    @InjectMocks
    private MacronutrientsCalculatorService macronutrientsCalculatorService;
    @Mock
    private BmrCalculatorService bmrCalculatorService;

    @Test
    void calculateHealthyMacronutrientsValuesWorksCorrectly() {
        //given
        UserProfile userProfile = DomainFixtures.someUserProfile();
        ActivityLevel activityLevel = ActivityLevel.MODERATELY_ACTIVE;
        BigDecimal bmr = BigDecimal.valueOf(3500);

        Mockito.when(bmrCalculatorService.calculateActivityIncludedBMR(userProfile.getUser().getUsername(), activityLevel))
                .thenReturn(bmr);
        //when
        Map<String, BigDecimal> result = macronutrientsCalculatorService
                .calculateHealthyMacronutrientsValues(userProfile.getUser().getUsername(), activityLevel);

        //then
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(3500), result.get("bmr"));
        assertEquals(BigDecimal.valueOf(175.0), result.get("protein"));
        assertEquals(BigDecimal.valueOf(437.5), result.get("carbohydrates"));
        assertEquals(BigDecimal.valueOf(116.7), result.get("fats"));
    }
}