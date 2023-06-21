package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

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

        Mockito.when(bmrCalculatorService.calculateActivityIncludedBMR(userProfile.getPhone(), activityLevel))
                .thenReturn(bmr);
        //when
        Map<String, BigDecimal> result = macronutrientsCalculatorService.calculateHealthyMacronutrientsValues(userProfile.getPhone(), activityLevel);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(BigDecimal.valueOf(3500), result.get("BMR"));
        Assertions.assertEquals(BigDecimal.valueOf(175.0), result.get("Protein"));
        Assertions.assertEquals(BigDecimal.valueOf(194.4), result.get("Carbohydrates"));
        Assertions.assertEquals(BigDecimal.valueOf(262.5), result.get("Fats"));
    }
}