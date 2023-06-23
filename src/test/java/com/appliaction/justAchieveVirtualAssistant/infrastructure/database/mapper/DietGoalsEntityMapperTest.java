package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.DietGoalsEntity;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DietGoalsEntityMapperTest {

    private final DietGoalsEntityMapper dietGoalsEntityMapper = Mappers.getMapper(DietGoalsEntityMapper.class);

    @Test
    void dietGoalsEntityMapperWorksCorrectly() {
        //given
        DietGoalsEntity entity = EntityFixtures.someDietGoalsEntity();

        //when
        DietGoals domain = dietGoalsEntityMapper.mapFromEntity(entity);
        DietGoals nullMapping = dietGoalsEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(DietGoals.class, domain.getClass());
        assertEquals(entity.getDietGoal(), domain.getDietGoal());
        assertEquals(entity.getDietGoalId(), domain.getDietGoalId());
    }
}