package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.FitnessTips;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FitnessTipsEntity;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FitnessTipsEntityMapperTest {

    private final FitnessTipsEntityMapper fitnessTipsEntityMapper = Mappers.getMapper(FitnessTipsEntityMapper.class);

    @Test
    void fitnessTipsEntityMapperWorksCorrectly() {
        //given
        FitnessTipsEntity entity = EntityFixtures.someFitnessTipsEntity();

        //when
        FitnessTips domain = fitnessTipsEntityMapper.mapFromEntity(entity);
        FitnessTips nullMapping = fitnessTipsEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(entity.getTip(), domain.getTip());
        assertEquals(FitnessTips.class, domain.getClass());
        assertEquals(entity.getTipId(), domain.getTipId());
    }
}