package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.DietGoalsDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DietGoalsMapperTest {

    private final DietGoalsMapper dietGoalsMapper = Mappers.getMapper(DietGoalsMapper.class);

    @Test
    void dietGoalsMapperWorksCorrectly() {
        //given
        DietGoals domain = DomainFixtures.someDietGoals();

        //when
        DietGoalsDTO dto = dietGoalsMapper.map(domain);
        DietGoalsDTO nullMapping = dietGoalsMapper.map(null);

        //then
        assertNull(nullMapping);
        assertEquals(DietGoalsDTO.class, dto.getClass());
        assertEquals(domain.getDietGoal(), dto.getDietGoal());
    }
}