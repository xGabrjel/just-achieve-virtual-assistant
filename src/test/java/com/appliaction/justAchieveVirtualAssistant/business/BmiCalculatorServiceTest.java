package com.appliaction.justAchieveVirtualAssistant.business;

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
class BmiCalculatorServiceTest {

    @InjectMocks
    private BmiCalculatorService bmiCalculatorService;

    @Mock
    private UserProfileRepository userProfileRepository;


    @Test
    void calculateBMIWorksCorrectly() {
        //given
        UserProfile userProfile = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withPhone("+48 511 522 162")
                .withWeight(BigDecimal.valueOf(60));

        when(userProfileRepository.getUserProfile(userProfile.getPhone())).thenReturn(userProfile);

        //when
        BigDecimal result = bmiCalculatorService.calculateBMI(userProfile.getPhone());

        //then
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(18), result);
    }

    @Test
    void underweightUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile underweightUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withPhone("+48 511 522 162")
                .withWeight(BigDecimal.valueOf(60));

        when(userProfileRepository.getUserProfile(underweightUser.getPhone())).thenReturn(underweightUser);

        //when
        String underweightResult = bmiCalculatorService.interpretBMI(underweightUser.getPhone());

        //then
        assertNotNull(underweightResult);
        assertEquals("BMI value: [18] - You are below 18.5 – you're in the underweight range", underweightResult);
    }

    @Test
    void healthyUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile healthyUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withPhone("+48 512 522 162")
                .withWeight(BigDecimal.valueOf(80));

        when(userProfileRepository.getUserProfile(healthyUser.getPhone())).thenReturn(healthyUser);

        //when
        String healthyResult = bmiCalculatorService.interpretBMI(healthyUser.getPhone());

        //then
        assertNotNull(healthyResult);
        assertEquals("BMI value: [24] - You are between 18.5 and 24.9 – you're in the healthy weight range", healthyResult);
    }

    @Test
    void overweightUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile overweightUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withPhone("+48 513 522 162")
                .withWeight(BigDecimal.valueOf(90));

        when(userProfileRepository.getUserProfile(overweightUser.getPhone())).thenReturn(overweightUser);

        //when
        String overweightResult = bmiCalculatorService.interpretBMI(overweightUser.getPhone());

        //then
        assertNotNull(overweightResult);
        assertEquals("BMI value: [27] - You are between 25 and 29.9 – you're in the overweight range", overweightResult);
    }

    @Test
    void obeseUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile obeseUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withPhone("+48 511 524 162")
                .withWeight(BigDecimal.valueOf(120));

        when(userProfileRepository.getUserProfile(obeseUser.getPhone())).thenReturn(obeseUser);

        //when
        String obeseResult = bmiCalculatorService.interpretBMI(obeseUser.getPhone());

        //then
        assertNotNull(obeseResult);
        assertEquals("BMI value: [36] - You are 30 or over – you're in the obese range", obeseResult);
    }
}