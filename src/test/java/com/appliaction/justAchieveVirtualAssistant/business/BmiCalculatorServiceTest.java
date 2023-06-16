package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserProfileEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

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
        String phoneNumber = "+48 511 533 522";
        UserProfileEntity userProfileEntity = new UserProfileEntity();

        Mockito.when(userProfileRepository.findByPhone(phoneNumber)).thenReturn(Collections.singleton(userProfileEntity));
        Mockito.when(userProfileEntityMapper.mapFromEntity(userProfileEntity)).thenReturn(new UserProfile());

        //when
        UserProfile userProfile = bmiCalculatorService.getUserProfile(phoneNumber);

        //then
        assertNotNull(userProfile);
        Mockito.verify(userProfileRepository, Mockito.times(1)).findByPhone(phoneNumber);
        Mockito.verify(userProfileEntityMapper, Mockito.times(1)).mapFromEntity(userProfileEntity);
    }

    @Test
    void getUserProfileExceptionThrowingWorksCorrectly() {
        //given
        String phoneNumber = "+48 511 533 522";

        //when
        Mockito.when(userProfileRepository.findByPhone(phoneNumber)).thenReturn(Collections.emptySet());

        //then
        assertThrows(NotFoundException.class, () -> bmiCalculatorService.getUserProfile(phoneNumber));
        Mockito.verify(userProfileRepository, Mockito.times(1)).findByPhone(phoneNumber);
    }

    @Test
    void calculateBMIWorksCorrectly() {
        //given
        UserProfile user = UserProfile.builder()
                .height(BigDecimal.valueOf(1.83))
                .phone("+48 511 522 162")
                .weight(BigDecimal.valueOf(60))
                .build();

        Mockito.when(userProfileRepository.findByPhone(user.getPhone())).thenReturn(Collections.singleton(new UserProfileEntity()));
        Mockito.when(userProfileEntityMapper.mapFromEntity(Mockito.any(UserProfileEntity.class))).thenReturn(user);

        //when
        BigDecimal result = bmiCalculatorService.calculateBMI(user.getPhone());

        //then
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(18), result);
    }

    @Test
    void underweightUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile underweightUser = UserProfile.builder().height(BigDecimal.valueOf(1.83)).phone("+48 511 522 162").weight(BigDecimal.valueOf(60)).build();

        Mockito.when(userProfileRepository.findByPhone(underweightUser.getPhone())).thenReturn(Collections.singleton(new UserProfileEntity()));
        Mockito.when(userProfileEntityMapper.mapFromEntity(Mockito.any(UserProfileEntity.class))).thenReturn(underweightUser);

        //when
        String underweightResult = bmiCalculatorService.interpretBMI(underweightUser.getPhone());

        //then
        assertNotNull(underweightResult);
        assertEquals("BMI value: [18] - You are below 18.5 – you're in the underweight range", underweightResult);
    }

    @Test
    void healthyUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile healthyUser = UserProfile.builder().height(BigDecimal.valueOf(1.83)).phone("+48 512 522 162").weight(BigDecimal.valueOf(80)).build();

        Mockito.when(userProfileRepository.findByPhone(healthyUser.getPhone())).thenReturn(Collections.singleton(new UserProfileEntity()));
        Mockito.when(userProfileEntityMapper.mapFromEntity(Mockito.any(UserProfileEntity.class))).thenReturn(healthyUser);

        //when
        String healthyResult = bmiCalculatorService.interpretBMI(healthyUser.getPhone());

        //then
        assertNotNull(healthyResult);
        assertEquals("BMI value: [24] - You are between 18.5 and 24.9 – you're in the healthy weight range", healthyResult);
    }

    @Test
    void overweightUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile overweightUser = UserProfile.builder().height(BigDecimal.valueOf(1.83)).phone("+48 513 522 162").weight(BigDecimal.valueOf(90)).build();

        Mockito.when(userProfileRepository.findByPhone(overweightUser.getPhone())).thenReturn(Collections.singleton(new UserProfileEntity()));
        Mockito.when(userProfileEntityMapper.mapFromEntity(Mockito.any(UserProfileEntity.class))).thenReturn(overweightUser);

        //when
        String overweightResult = bmiCalculatorService.interpretBMI(overweightUser.getPhone());

        //then
        assertNotNull(overweightResult);
        assertEquals("BMI value: [27] - You are between 25 and 29.9 – you're in the overweight range", overweightResult);
    }

    @Test
    void obeseUserInterpretBMIWorksCorrectly() {
        //given
        UserProfile obeseUser = UserProfile.builder().height(BigDecimal.valueOf(1.83)).phone("+48 511 524 162").weight(BigDecimal.valueOf(120)).build();

        Mockito.when(userProfileRepository.findByPhone(obeseUser.getPhone())).thenReturn(Collections.singleton(new UserProfileEntity()));
        Mockito.when(userProfileEntityMapper.mapFromEntity(Mockito.any(UserProfileEntity.class))).thenReturn(obeseUser);

        //when
        String obeseResult = bmiCalculatorService.interpretBMI(obeseUser.getPhone());

        //then
        assertNotNull(obeseResult);
        assertEquals("BMI value: [36] - You are 30 or over – you're in the obese range", obeseResult);
    }
}