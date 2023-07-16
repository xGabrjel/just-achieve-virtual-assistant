package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.DietGoalsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.FitnessTipsDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.domain.FitnessTips;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FitnessTipsMapperTest {

    private final FitnessTipsMapper fitnessTipsMapper = Mappers.getMapper(FitnessTipsMapper.class);

    @Test
    void fitnessTipsMapperWorksCorrectly() {
        //given
        FitnessTips domain = DomainFixtures.someFitnessTips();
        FitnessTips nullDietGoal = DomainFixtures.someFitnessTips();

        domain.setDietGoal(DietGoals.builder()
                .dietGoalId(2)
                .dietGoal("FAT LOSS")
                .build());

        nullDietGoal.setDietGoal(null);

        //when
        FitnessTipsDTO dto = fitnessTipsMapper.map(domain);
        FitnessTipsDTO nullMapping = fitnessTipsMapper.map(null);
        FitnessTipsDTO dtoNullDietGoal = fitnessTipsMapper.map(nullDietGoal);

        //then
        assertNull(nullMapping);
        assertNull(dtoNullDietGoal.getDietGoal());
        assertEquals(domain.getTip(), dto.getTip());
        assertEquals(FitnessTipsDTO.class, dto.getClass());
        assertEquals(DietGoalsDTO.class, dto.getDietGoal().getClass());
        assertEquals(domain.getDietGoal().getDietGoal(), dto.getDietGoal().getDietGoal());
    }
}