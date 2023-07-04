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

        Mockito.when(bmrCalculatorService.calculateActivityIncludedBMR(userProfile.getUser().getUsername(), activityLevel))
                .thenReturn(bmr);
        //when
        Map<String, BigDecimal> result = macronutrientsCalculatorService
                .calculateHealthyMacronutrientsValues(userProfile.getUser().getUsername(), activityLevel);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(BigDecimal.valueOf(3500), result.get("bmr"));
        Assertions.assertEquals(BigDecimal.valueOf(175.0), result.get("protein"));
        Assertions.assertEquals(BigDecimal.valueOf(437.5), result.get("carbohydrates"));
        Assertions.assertEquals(BigDecimal.valueOf(116.7), result.get("fats"));
    }
}