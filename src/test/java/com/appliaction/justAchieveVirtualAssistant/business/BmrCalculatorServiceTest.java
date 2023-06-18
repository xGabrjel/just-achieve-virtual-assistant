package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BmrCalculatorServiceTest {

    @InjectMocks
    private BmrCalculatorService bmrCalculatorService;

    @Mock
    private UserProfileService userProfileService;

    @Test
    void calculateByRevisedHarrisBenedictFormulaForFemaleWorksCorrectly() {
        //given
        BigDecimal weight = BigDecimal.valueOf(60);
        BigDecimal height = BigDecimal.valueOf(1.69);
        Integer age = 25;

        //when
        BigDecimal result = bmrCalculatorService.calculateByRevisedHarrisBenedictFormulaForFemale(weight, height, age);

        //then
        assertNotNull(result);
        assertEquals(new BigDecimal("1418.25"), result);
    }

    @Test
    void calculateByRevisedHarrisBenedictFormulaForMaleWorksCorrectly() {
        //given
        BigDecimal weight = BigDecimal.valueOf(80);
        BigDecimal height = BigDecimal.valueOf(1.80);
        Integer age = 25;

        //when
        BigDecimal result = bmrCalculatorService.calculateByRevisedHarrisBenedictFormulaForMale(weight, height, age);

        //then
        assertNotNull(result);
        assertEquals(new BigDecimal("1882.40"), result);
    }

    @Test
    void activityExcludedBmrCalculatorWorksCorrectlyForMale() {
        //given
        UserProfile user = DomainFixtures.someUserProfile()
                .withPhone("+48 567 778 990")
                .withAge(25)
                .withHeight(BigDecimal.valueOf(1.80))
                .withWeight(BigDecimal.valueOf(80))
                .withSex("MALE");

        when(userProfileService.getUserProfile(user.getPhone())).thenReturn(user);

        //when
        BigDecimal result = bmrCalculatorService.calculateActivityExcludedBMR(user.getPhone());

        //then
        assertNotNull(result);
        assertEquals(bmrCalculatorService
                .calculateByRevisedHarrisBenedictFormulaForMale(BigDecimal.valueOf(80), BigDecimal.valueOf(1.80), 25), result);
    }

    @Test
    void activityExcludedBmrCalculatorWorksCorrectlyForFemale() {
        //given
        UserProfile user = DomainFixtures.someUserProfile()
                .withPhone("+48 567 778 990")
                .withAge(21)
                .withHeight(BigDecimal.valueOf(1.50))
                .withWeight(BigDecimal.valueOf(69))
                .withSex("FEMALE");

        when(userProfileService.getUserProfile(user.getPhone())).thenReturn(user);

        //when
        BigDecimal result = bmrCalculatorService.calculateActivityExcludedBMR(user.getPhone());

        //then
        assertNotNull(result);
        assertEquals(bmrCalculatorService
                .calculateByRevisedHarrisBenedictFormulaForFemale(BigDecimal.valueOf(69), BigDecimal.valueOf(1.50), 21), result);
    }

    @Test
    void activityIncludedBmrCalculatorWorksCorrectly() {
        //given
        UserProfile user = DomainFixtures.someUserProfile()
                .withPhone("+48 567 778 990")
                .withAge(21)
                .withHeight(BigDecimal.valueOf(1.50))
                .withWeight(BigDecimal.valueOf(69))
                .withSex("FEMALE");

        when(userProfileService.getUserProfile(user.getPhone())).thenReturn(user);

        //when
        String result1 = bmrCalculatorService.calculateActivityIncludedBMR(user.getPhone(), ActivityLevel.SEDENTARY);
        String result2 = bmrCalculatorService.calculateActivityIncludedBMR(user.getPhone(), ActivityLevel.LIGHTLY_ACTIVE);
        String result3 = bmrCalculatorService.calculateActivityIncludedBMR(user.getPhone(), ActivityLevel.MODERATELY_ACTIVE);
        String result4 = bmrCalculatorService.calculateActivityIncludedBMR(user.getPhone(), ActivityLevel.ACTIVE);
        String result5 = bmrCalculatorService.calculateActivityIncludedBMR(user.getPhone(), ActivityLevel.VERY_ACTIVE);

        //then
        assertNotNull(result1);
        assertEquals("Your BMR including activity level is equal to: [1751.90] kcal", result1);
        assertNotNull(result2);
        assertEquals("Your BMR including activity level is equal to: [2007.39] kcal", result2);
        assertNotNull(result3);
        assertEquals("Your BMR including activity level is equal to: [2262.88] kcal", result3);
        assertNotNull(result4);
        assertEquals("Your BMR including activity level is equal to: [2518.36] kcal", result4);
        assertNotNull(result5);
        assertEquals("Your BMR including activity level is equal to: [2773.85] kcal", result5);
    }
}