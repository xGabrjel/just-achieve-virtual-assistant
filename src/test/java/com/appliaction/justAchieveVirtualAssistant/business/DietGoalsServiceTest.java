package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.DietGoalsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.DietGoalsEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.DietGoalsJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DietGoalsServiceTest {

    @InjectMocks
    private DietGoalsService dietGoalsService;
    @Mock
    private DietGoalsJpaRepository dietGoalsJpaRepository;
    @Mock
    private DietGoalsEntityMapper dietGoalsEntityMapper;

    @Test
    void findByIdWorksCorrectly() {
        //given
        DietGoalsEntity dietGoalsEntity = EntityFixtures.someDietGoalsEntity();
        DietGoals dietGoals = DomainFixtures.someDietGoals();

        when(dietGoalsJpaRepository.findById(dietGoalsEntity.getDietGoalId()))
                .thenReturn(Optional.of(dietGoalsEntity));
        when(dietGoalsEntityMapper.mapFromEntity(dietGoalsEntity))
                .thenReturn(dietGoals);

        //when
        Optional<DietGoals> result = dietGoalsService.findById(dietGoalsEntity.getDietGoalId());

        //then
        assertNotNull(result);
        assertEquals(Optional.of(dietGoals), result);
        verify(dietGoalsJpaRepository, times(1)).findById(dietGoalsEntity.getDietGoalId());
        verify(dietGoalsEntityMapper, times(1)).mapFromEntity(dietGoalsEntity);
    }
}