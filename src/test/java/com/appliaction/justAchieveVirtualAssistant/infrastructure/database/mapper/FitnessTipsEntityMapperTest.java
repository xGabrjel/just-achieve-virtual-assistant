package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.FitnessTips;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FitnessTipsEntity;
import com.appliaction.justAchieveVirtualAssistant.util.EntityMapperFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FitnessTipsEntityMapperTest {

    private final FitnessTipsEntityMapper fitnessTipsEntityMapper = Mappers.getMapper(FitnessTipsEntityMapper.class);

    @Test
    void fitnessTipsEntityMapperWorksCorrectly() {
        //given
        FitnessTipsEntity entity = EntityMapperFixtures.someFitnessTips();

        //when
        FitnessTips domain = fitnessTipsEntityMapper.mapFromEntity(entity);

        //then
        assertEquals(FitnessTips.class, domain.getClass());
        assertEquals(entity.getTipId(), domain.getTipId());
        assertEquals(entity.getTip(), domain.getTip());
    }
}