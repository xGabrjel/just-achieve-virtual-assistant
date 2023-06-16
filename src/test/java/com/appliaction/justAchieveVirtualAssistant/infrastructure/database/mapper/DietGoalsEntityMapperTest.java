package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.DietGoalsEntity;
import com.appliaction.justAchieveVirtualAssistant.util.EntityMapperFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DietGoalsEntityMapperTest {

    private final DietGoalsEntityMapper dietGoalsEntityMapper = Mappers.getMapper(DietGoalsEntityMapper.class);

    @Test
    void dietGoalsEntityMapperWorksCorrectly() {
        //given
        DietGoalsEntity entity = EntityMapperFixtures.someDietGoals();

        //when
        DietGoals domain = dietGoalsEntityMapper.mapFromEntity(entity);

        //then
        assertEquals(DietGoals.class, domain.getClass());
        assertEquals(entity.getDietGoalId(), domain.getDietGoalId());
        assertEquals(entity.getDietGoal(), domain.getDietGoal());
    }
}