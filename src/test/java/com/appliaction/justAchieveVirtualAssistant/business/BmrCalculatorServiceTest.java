package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ActivityLevel;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
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
    private UserProfileRepository userProfileRepository;

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
                .withUser(DomainFixtures.someUser().withUsername("test1"))
                .withAge(25)
                .withHeight(BigDecimal.valueOf(1.80))
                .withWeight(BigDecimal.valueOf(80))
                .withSex("MALE");

        when(userProfileRepository.findByUserUsername(user.getUser().getUsername())).thenReturn(user);

        //when
        BigDecimal result = bmrCalculatorService.calculateActivityExcludedBMR(user.getUser().getUsername());

        //then
        assertNotNull(result);
        assertEquals(bmrCalculatorService
                .calculateByRevisedHarrisBenedictFormulaForMale(BigDecimal.valueOf(80), BigDecimal.valueOf(1.80), 25), result);
    }

    @Test
    void activityExcludedBmrCalculatorWorksCorrectlyForFemale() {
        //given
        UserProfile user = DomainFixtures.someUserProfile()
                .withUser(DomainFixtures.someUser().withUsername("test2"))
                .withAge(21)
                .withHeight(BigDecimal.valueOf(1.50))
                .withWeight(BigDecimal.valueOf(69))
                .withSex("FEMALE");

        when(userProfileRepository.findByUserUsername(user.getUser().getUsername())).thenReturn(user);

        //when
        BigDecimal result = bmrCalculatorService.calculateActivityExcludedBMR(user.getUser().getUsername());

        //then
        assertNotNull(result);
        assertEquals(bmrCalculatorService
                .calculateByRevisedHarrisBenedictFormulaForFemale(BigDecimal.valueOf(69), BigDecimal.valueOf(1.50), 21), result);
    }

    @Test
    void activityIncludedBmrCalculatorWorksCorrectly() {
        //given
        UserProfile user = DomainFixtures.someUserProfile()
                .withUser(DomainFixtures.someUser().withUsername("test3"))
                .withAge(21)
                .withHeight(BigDecimal.valueOf(1.50))
                .withWeight(BigDecimal.valueOf(69))
                .withSex("FEMALE");

        when(userProfileRepository.findByUserUsername(user.getUser().getUsername())).thenReturn(user);

        //when
        BigDecimal result1 = bmrCalculatorService.calculateActivityIncludedBMR(user.getUser().getUsername(), ActivityLevel.SEDENTARY);
        BigDecimal result2 = bmrCalculatorService.calculateActivityIncludedBMR(user.getUser().getUsername(), ActivityLevel.LIGHTLY_ACTIVE);
        BigDecimal result3 = bmrCalculatorService.calculateActivityIncludedBMR(user.getUser().getUsername(), ActivityLevel.MODERATELY_ACTIVE);
        BigDecimal result4 = bmrCalculatorService.calculateActivityIncludedBMR(user.getUser().getUsername(), ActivityLevel.ACTIVE);
        BigDecimal result5 = bmrCalculatorService.calculateActivityIncludedBMR(user.getUser().getUsername(), ActivityLevel.VERY_ACTIVE);

        //then
        assertNotNull(result1);
        assertEquals(new BigDecimal("1751.90"), result1);
        assertNotNull(result2);
        assertEquals(new BigDecimal("2007.39"), result2);
        assertNotNull(result3);
        assertEquals(new BigDecimal("2262.88"), result3);
        assertNotNull(result4);
        assertEquals(new BigDecimal("2518.36"), result4);
        assertNotNull(result5);
        assertEquals(new BigDecimal("2773.85"), result5);
    }
}