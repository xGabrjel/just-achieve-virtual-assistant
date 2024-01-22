package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.api.dto.DietGoalsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.FitnessTipsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.FitnessTipsMapper;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    @Mock
    private FitnessTipsMapper fitnessTipsMapper;

    @Test
    void getRandomTipForDietGoalWorksCorrectly1() {
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

        when(userProfileRepository.findByUserUsername(user.getUser().getUsername()))
                .thenReturn(user);
        when(fitnessTipsJpaRepository.findByDietGoalDietGoalId(user.getDietGoal().getDietGoalId()))
                .thenReturn(fitnessTipsEntities);
        when(fitnessTipsEntityMapper.mapFromEntity(any(FitnessTipsEntity.class)))
                .thenReturn(new FitnessTips(
                        1,
                        DietGoals.builder().dietGoalId(1).dietGoal("MUSCLE BUILDING").build(),
                        "Tip 1"));
        when(fitnessTipsMapper.map(any(FitnessTips.class)))
                .thenReturn(FitnessTipsDTO
                        .builder()
                        .dietGoal(DietGoalsDTO.builder().dietGoal("MUSCLE BUILDING").build())
                        .tip("Tip 1")
                        .build());

        //when
        FitnessTipsDTO result = fitnessTipsGeneratorService.getRandomTipForDietGoal(user.getUser().getUsername());

        //then
        assertNotNull(result);
        verify(userProfileRepository).findByUserUsername(user.getUser().getUsername());
        verify(fitnessTipsJpaRepository).findByDietGoalDietGoalId(user.getDietGoal().getDietGoalId());
        verify(fitnessTipsEntityMapper, times(fitnessTipsEntities.size())).mapFromEntity(any(FitnessTipsEntity.class));
    }
}