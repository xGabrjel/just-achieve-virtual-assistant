package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.VerificationToken;
import com.appliaction.justAchieveVirtualAssistant.security.registration.token.VerificationTokenEntity;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class VerificationTokenEntityMapperTest {

    private final VerificationTokenEntityMapper verificationTokenEntityMapper = Mappers.getMapper(VerificationTokenEntityMapper.class);

    @Test
    void verificationTokenMapFromEntityMapperWorksCorrectly() {
        //given
        VerificationTokenEntity entity = new VerificationTokenEntity();
        entity.setId(1L);
        entity.setToken(UUID.randomUUID().toString());
        entity.setExpirationTime(Date.from(Instant.now()));
        entity.setUser(EntityFixtures.someUserEntity());

        //when
        VerificationToken domain = verificationTokenEntityMapper.mapFromEntity(entity);
        VerificationToken nullMapping = verificationTokenEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getToken(), domain.getToken());
        assertEquals(entity.getExpirationTime(), domain.getExpirationTime());
        assertEquals(VerificationToken.class, domain.getClass());
    }
    @Test
    void verificationTokenMapToEntityMapperWorksCorrectly() {
        VerificationToken domain = new VerificationToken();
        domain.setId(1L);
        domain.setToken(UUID.randomUUID().toString());
        domain.setExpirationTime(Date.from(Instant.now()));
        domain.setUser(DomainFixtures.someUser());

        //when
        VerificationTokenEntity entity = verificationTokenEntityMapper.mapToEntity(domain);
        VerificationTokenEntity nullMapping = verificationTokenEntityMapper.mapToEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getToken(), entity.getToken());
        assertEquals(domain.getExpirationTime(), entity.getExpirationTime());
        assertEquals(VerificationTokenEntity.class, entity.getClass());
    }
}