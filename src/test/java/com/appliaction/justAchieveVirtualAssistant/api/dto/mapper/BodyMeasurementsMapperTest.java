package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.BodyMeasurementsDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BodyMeasurementsMapperTest {

    private final BodyMeasurementsMapper bodyMeasurementsMapper = Mappers.getMapper(BodyMeasurementsMapper.class);

    @Test
    void bodyMeasurementsMapperWorksCorrectly() {
        //given
        BodyMeasurements domain = DomainFixtures.someBodyMeasurements();

        //when
        BodyMeasurementsDTO dto = bodyMeasurementsMapper.map(domain);

        //then
        assertEquals(domain.getArm(), dto.getArm());
        assertEquals(domain.getDate(), dto.getDate());
        assertEquals(domain.getCalf(), dto.getCalf());
        assertEquals(domain.getThigh(), dto.getThigh());
        assertEquals(domain.getWaist(), dto.getWaist());
        assertEquals(domain.getChest(), dto.getChest());
        assertEquals(BodyMeasurementsDTO.class, dto.getClass());
        assertEquals(domain.getCurrentWeight(), dto.getCurrentWeight());
        assertEquals(domain.getMeasurementNote(), dto.getMeasurementNote());
    }
}