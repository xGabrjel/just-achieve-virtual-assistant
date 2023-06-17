package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserProfileEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BmiCalculatorServiceTest {

    @InjectMocks
    private BmiCalculatorService bmiCalculatorService;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserProfileEntityMapper userProfileEntityMapper;

    @Test
    void getUserProfileWorksCorrectly() {
        //given
        UserProfileEntity user = EntityFixtures.someUserProfileEntity();
        UserProfile userProfile = DomainFixtures.someUserProfile();
        String phoneNumber = "+48 511 522 533";

        when(userProfileRepository.findByPhone(user.getPhone())).thenReturn(Set.of(user));
        when(userProfileEntityMapper.mapFromEntity(user)).thenReturn(userProfile);

        //when
        UserProfile result = bmiCalculatorService.getUserProfile(user.getPhone());

        //then
        assertNotNull(result);
        verify(userProfileRepository, times(1)).findByPhone(phoneNumber);
        verify(userProfileEntityMapper, times(1)).mapFromEntity(user);
    }

    @Test
    void getUserProfileExceptionThrowingWorksCorrectly() {
        //given
        String phoneNumber = "+48 511 533 522";

        //when
        when(userProfileRepository.findByPhone(phoneNumber)).thenReturn(Collections.emptySet());

        //then
        assertThrows(NotFoundException.class, () -> bmiCalculatorService.getUserProfile(phoneNumber));
        verify(userProfileRepository, times(1)).findByPhone(phoneNumber);
    }

    @Test
    void calculateBMIWorksCorrectly() {
        //given
        UserProfileEntity user = EntityFixtures.someUserProfileEntity();

        UserProfile userProfile = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withPhone("+48 511 522 162")
                .withWeight(BigDecimal.valueOf(60));

        when(userProfileRepository.findByPhone(userProfile.getPhone())).thenReturn(Set.of(user));
        when(userProfileEntityMapper.mapFromEntity(user)).thenReturn(userProfile);

        //when
        BigDecimal result = bmiCalculatorService.calculateBMI(userProfile.getPhone());

        //then
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(18), result);
    }

    @Test
    void underweightUserInterpretBMIWorksCorrectly() {
        //given
        UserProfileEntity user = EntityFixtures.someUserProfileEntity();

        UserProfile underweightUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withPhone("+48 511 522 162")
                .withWeight(BigDecimal.valueOf(60));

        when(userProfileRepository.findByPhone(underweightUser.getPhone())).thenReturn(Set.of(user));
        when(userProfileEntityMapper.mapFromEntity(user)).thenReturn(underweightUser);

        //when
        String underweightResult = bmiCalculatorService.interpretBMI(underweightUser.getPhone());

        //then
        assertNotNull(underweightResult);
        assertEquals("BMI value: [18] - You are below 18.5 – you're in the underweight range", underweightResult);
    }

    @Test
    void healthyUserInterpretBMIWorksCorrectly() {
        //given
        UserProfileEntity user = EntityFixtures.someUserProfileEntity();

        UserProfile healthyUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withPhone("+48 512 522 162")
                .withWeight(BigDecimal.valueOf(80));

        when(userProfileRepository.findByPhone(healthyUser.getPhone())).thenReturn(Set.of(user));
        when(userProfileEntityMapper.mapFromEntity(user)).thenReturn(healthyUser);

        //when
        String healthyResult = bmiCalculatorService.interpretBMI(healthyUser.getPhone());

        //then
        assertNotNull(healthyResult);
        assertEquals("BMI value: [24] - You are between 18.5 and 24.9 – you're in the healthy weight range", healthyResult);
    }

    @Test
    void overweightUserInterpretBMIWorksCorrectly() {
        //given
        UserProfileEntity user = EntityFixtures.someUserProfileEntity();

        UserProfile overweightUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withPhone("+48 513 522 162")
                .withWeight(BigDecimal.valueOf(90));

        when(userProfileRepository.findByPhone(overweightUser.getPhone())).thenReturn(Set.of(user));
        when(userProfileEntityMapper.mapFromEntity(user)).thenReturn(overweightUser);

        //when
        String overweightResult = bmiCalculatorService.interpretBMI(overweightUser.getPhone());

        //then
        assertNotNull(overweightResult);
        assertEquals("BMI value: [27] - You are between 25 and 29.9 – you're in the overweight range", overweightResult);
    }

    @Test
    void obeseUserInterpretBMIWorksCorrectly() {
        //given
        UserProfileEntity user = EntityFixtures.someUserProfileEntity();

        UserProfile obeseUser = DomainFixtures.someUserProfile()
                .withHeight(BigDecimal.valueOf(1.83))
                .withPhone("+48 511 524 162")
                .withWeight(BigDecimal.valueOf(120));

        when(userProfileRepository.findByPhone(obeseUser.getPhone())).thenReturn(Set.of(user));
        when(userProfileEntityMapper.mapFromEntity(user)).thenReturn(obeseUser);

        //when
        String obeseResult = bmiCalculatorService.interpretBMI(obeseUser.getPhone());

        //then
        assertNotNull(obeseResult);
        assertEquals("BMI value: [36] - You are 30 or over – you're in the obese range", obeseResult);
    }
}