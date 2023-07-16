package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.BodyMeasurementsEntity;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class BodyMeasurementsEntityMapperTest {

    private final BodyMeasurementsEntityMapper bodyMeasurementsEntityMapper = Mappers.getMapper(BodyMeasurementsEntityMapper.class);

    @Test
    void bodyMeasurementsMapFromEntityMapperWorksCorrectly() {
        //given
        BodyMeasurementsEntity entity = EntityFixtures.someBodyMeasurementsEntity();

        //when
        BodyMeasurements domain = bodyMeasurementsEntityMapper.mapFromEntity(entity);
        BodyMeasurements nullMapping = bodyMeasurementsEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
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
        assertNotEquals(entity.getProfileId().getUser().getRoles().toArray(), domain.getProfileId().getUser().getRoles().toArray());
    }

    @Test
    void bodyMeasurementsMapToEntityMapperWorksCorrectly() {
        //given
        BodyMeasurements domain = DomainFixtures.someBodyMeasurements();

        //when
        BodyMeasurementsEntity entity = bodyMeasurementsEntityMapper.mapToEntity(domain);
        BodyMeasurementsEntity nullMapping = bodyMeasurementsEntityMapper.mapToEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getArm(), entity.getArm());
        assertEquals(domain.getDate(), entity.getDate());
        assertEquals(domain.getCalf(), entity.getCalf());
        assertEquals(domain.getThigh(), entity.getThigh());
        assertEquals(domain.getWaist(), entity.getWaist());
        assertEquals(domain.getChest(), entity.getChest());
        assertEquals(BodyMeasurementsEntity.class, entity.getClass());
        assertEquals(domain.getCurrentWeight(), entity.getCurrentWeight());
        assertEquals(domain.getMeasurementNote(), entity.getMeasurementNote());
        assertEquals(domain.getBodyMeasurementId(), entity.getBodyMeasurementId());
        assertNotEquals(domain.getProfileId().getUser().getRoles().toArray(), entity.getProfileId().getUser().getRoles().toArray());
    }

    @Test
    void nullMapFromEntityWorksCorrectly() {
        //given
        BodyMeasurementsEntity nullEntity1 = EntityFixtures.someBodyMeasurementsEntity();
        nullEntity1.getProfileId().getUser().setRoles(null);
        nullEntity1.getProfileId().setDietGoal(null);

        BodyMeasurementsEntity nullEntity2 = EntityFixtures.someBodyMeasurementsEntity();
        nullEntity2.getProfileId().setUser(null);

        //when
        BodyMeasurements nullRoles1 = bodyMeasurementsEntityMapper.mapFromEntity(nullEntity1);
        BodyMeasurements nullRoles2 = bodyMeasurementsEntityMapper.mapFromEntity(nullEntity2);

        //then
        assertNull(nullRoles2.getProfileId().getUser());
        assertNull(nullRoles1.getProfileId().getDietGoal());
        assertNull(nullRoles1.getProfileId().getUser().getRoles());
    }

    @Test
    void nullMapToEntityWorksCorrectly() {
        //given
        BodyMeasurements nullDomain1 = DomainFixtures.someBodyMeasurements();
        nullDomain1.getProfileId().getUser().setRoles(null);
        nullDomain1.getProfileId().setDietGoal(null);

        BodyMeasurements nullDomain2 = DomainFixtures.someBodyMeasurements();
        nullDomain2.getProfileId().setUser(null);

        //when
        BodyMeasurementsEntity nullRoles1 = bodyMeasurementsEntityMapper.mapToEntity(nullDomain1);
        BodyMeasurementsEntity nullRoles2 = bodyMeasurementsEntityMapper.mapToEntity(nullDomain2);

        //then
        assertNull(nullRoles2.getProfileId().getUser());
        assertNull(nullRoles1.getProfileId().getDietGoal());
        assertNull(nullRoles1.getProfileId().getUser().getRoles());
    }
}