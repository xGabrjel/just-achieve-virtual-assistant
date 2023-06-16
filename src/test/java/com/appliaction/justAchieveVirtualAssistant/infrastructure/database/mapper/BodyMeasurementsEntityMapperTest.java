package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import com.appliaction.justAchieveVirtualAssistant.util.EntityMapperFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BodyMeasurementsEntityMapperTest {

    private final BodyMeasurementsEntityMapper bodyMeasurementsEntityMapper = Mappers.getMapper(BodyMeasurementsEntityMapper.class);

    @Test
    void bodyMeasurementsEntityMapperWorksCorrectly() {
        //given
        BodyMeasurementsEntity entity = EntityMapperFixtures.someBodyMeasurements();

        //when
        var domain = bodyMeasurementsEntityMapper.mapFromEntity(entity);

        //then
        assertEquals(entity.getArm(), domain.getArm());
        assertEquals(entity.getDate(), domain.getDate());
        assertEquals(entity.getCalf(), domain.getCalf());
        assertEquals(entity.getThigh(), domain.getThigh());
        assertEquals(entity.getWaist(), domain.getWaist());
        assertEquals(entity.getChest(), domain.getChest());
        assertEquals(BodyMeasurements.class, domain.getClass());
        assertEquals(entity.getCurrentWeight(), domain.getCurrentWeight());
        assertEquals(entity.getMeasurementNote(), domain.getMeasurementNote());
        assertEquals(entity.getBodyMeasurementId(), domain.getBodyMeasurementId());
    }
}