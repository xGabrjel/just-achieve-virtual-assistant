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
                .withUser(DomainFixtures.someUser().withUsername("test1"))
                .withWeight(BigDecimal.valueOf(60));

        when(userProfileRepository.findByUserUsername(userProfile.getUser().getUsername()))
                .thenReturn(userProfile);

        //when
        BigDecimal result = bmiCalculatorService.calculateBMI(userProfile.getUser().getUsername());

        //then
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(18), result);
    }

    @Test
    void underweightUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile underweightUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withUser(DomainFixtures.someUser().withUsername("test2"))
                .withWeight(BigDecimal.valueOf(60));

        when(userProfileRepository.findByUserUsername(underweightUser.getUser().getUsername()))
                .thenReturn(underweightUser);

        //when
        String underweightResult = bmiCalculatorService.calculateAndInterpretBMI(underweightUser.getUser().getUsername());

        //then
        assertNotNull(underweightResult);
        assertEquals("BMI value: [18] - You are below 18.5 – you're in the underweight range", underweightResult);
    }

    @Test
    void healthyUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile healthyUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withUser(DomainFixtures.someUser().withUsername("test3"))
                .withWeight(BigDecimal.valueOf(80));

        when(userProfileRepository.findByUserUsername(healthyUser.getUser().getUsername()))
                .thenReturn(healthyUser);

        //when
        String healthyResult = bmiCalculatorService.calculateAndInterpretBMI(healthyUser.getUser().getUsername());

        //then
        assertNotNull(healthyResult);
        assertEquals("BMI value: [24] - You are between 18.5 and 24.9 – you're in the healthy weight range", healthyResult);
    }

    @Test
    void overweightUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile overweightUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withUser(DomainFixtures.someUser().withUsername("test4"))
                .withWeight(BigDecimal.valueOf(90));

        when(userProfileRepository.findByUserUsername(overweightUser.getUser().getUsername()))
                .thenReturn(overweightUser);

        //when
        String overweightResult = bmiCalculatorService.calculateAndInterpretBMI(overweightUser.getUser().getUsername());

        //then
        assertNotNull(overweightResult);
        assertEquals("BMI value: [27] - You are between 25 and 29.9 – you're in the overweight range", overweightResult);
    }

    @Test
    void obeseUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile obeseUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withUser(DomainFixtures.someUser().withUsername("test5"))
                .withWeight(BigDecimal.valueOf(120));

        when(userProfileRepository.findByUserUsername(obeseUser.getUser().getUsername()))
                .thenReturn(obeseUser);

        //when
        String obeseResult = bmiCalculatorService.calculateAndInterpretBMI(obeseUser.getUser().getUsername());

        //then
        assertNotNull(obeseResult);
        assertEquals("BMI value: [36] - You are 30 or over – you're in the obese range", obeseResult);
    }
}