package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FitnessTipsDTO;
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

        //when
        FitnessTipsDTO dto = fitnessTipsMapper.map(domain);
        FitnessTipsDTO nullMapping = fitnessTipsMapper.map(null);

        //then
        assertNull(nullMapping);
        assertEquals(FitnessTipsDTO.class, dto.getClass());
        assertEquals(domain.getTip(), dto.getTip());
    }
}