package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.domain.FitnessTips;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.DietGoalsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FitnessTipsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.FitnessTipsEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.FitnessTipsJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FitnessTipsGeneratorServiceTest {

    @InjectMocks
    private FitnessTipsGeneratorService fitnessTipsGeneratorService;

    @Mock
    private FitnessTipsJpaRepository fitnessTipsJpaRepository;

    @Mock
    private FitnessTipsEntityMapper fitnessTipsEntityMapper;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Test
    void getRandomTipForDietGoalWorksCorrectly() {
        //given
        UserProfile user = DomainFixtures.someUserProfile();
        List<FitnessTipsEntity> fitnessTipsEntities = new ArrayList<>();
        fitnessTipsEntities.add(new FitnessTipsEntity(
                1,
                DietGoalsEntity
                        .builder()
                        .dietGoalId(1)
                        .dietGoal("MUSCLE BUILDING")
                        .build(), "Tip 1"));

        when(userProfileRepository.findByUserUsername(user.getUser().getUsername())).thenReturn(user);
        when(fitnessTipsJpaRepository.findByDietGoalDietGoalId(user.getDietGoal().getDietGoalId())).thenReturn(fitnessTipsEntities);
        when(fitnessTipsEntityMapper.mapFromEntity(any(FitnessTipsEntity.class)))
                .thenReturn(new FitnessTips(
                        1, DietGoals
                        .builder()
                        .dietGoalId(1)
                        .dietGoal("MUSCLE BUILDING")
                        .build(), "Tip 1"));

        //when
        String result = fitnessTipsGeneratorService.getRandomTipForDietGoal(user.getUser().getUsername());

        //then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(fitnessTipsEntities.stream().anyMatch(entity -> entity.getTip().equals(result)));

        verify(userProfileRepository).findByUserUsername(user.getUser().getUsername());
        verify(fitnessTipsJpaRepository).findByDietGoalDietGoalId(user.getDietGoal().getDietGoalId());
        verify(fitnessTipsEntityMapper, times(fitnessTipsEntities.size())).mapFromEntity(any(FitnessTipsEntity.class));
    }

    @Test
    void getRandomTipForDietGoalWorksCorrectlyWhenNoTipsAvailable() {
        //given
        UserProfile user = DomainFixtures.someUserProfile();
        List<FitnessTipsEntity> fitnessTipsEntities = new ArrayList<>();
        String noTipsCommunication = "Sorry, no tips available for the selected goal";

        when(userProfileRepository.findByUserUsername(user.getUser().getUsername())).thenReturn(user);
        when(fitnessTipsJpaRepository.findByDietGoalDietGoalId(user.getDietGoal().getDietGoalId())).thenReturn(fitnessTipsEntities);

        //when
        String result = fitnessTipsGeneratorService.getRandomTipForDietGoal(user.getUser().getUsername());

        //then
        assertEquals(noTipsCommunication, result);

        verify(userProfileRepository).findByUserUsername(user.getUser().getUsername());
        verify(fitnessTipsJpaRepository).findByDietGoalDietGoalId(user.getDietGoal().getDietGoalId());
        verify(fitnessTipsEntityMapper, never()).mapFromEntity(any(FitnessTipsEntity.class));
    }
}