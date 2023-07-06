package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.PasswordResetToken;
import com.appliaction.justAchieveVirtualAssistant.security.registration.password.PasswordResetTokenEntity;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PasswordResetTokenEntityMapperTest {

    private final PasswordResetTokenEntityMapper passwordResetTokenEntityMapper = Mappers.getMapper(PasswordResetTokenEntityMapper.class);

    @Test
    void passwordResetTokenMapFromEntityMapperWorksCorrectly() {
        //given
        PasswordResetTokenEntity entity = new PasswordResetTokenEntity();
        entity.setId(1L);
        entity.setToken(UUID.randomUUID().toString());
        entity.setExpirationTime(Date.from(Instant.now()));
        entity.setUser(EntityFixtures.someUserEntity());

        //when
        PasswordResetToken domain = passwordResetTokenEntityMapper.mapFromEntity(entity);
        PasswordResetToken nullMapping = passwordResetTokenEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getToken(), domain.getToken());
        assertEquals(entity.getExpirationTime(), domain.getExpirationTime());
        assertEquals(PasswordResetToken.class, domain.getClass());
    }

    @Test
    void passwordResetTokenMapToEntityMapperWorksCorrectly() {
        PasswordResetToken domain = new PasswordResetToken();
        domain.setId(1L);
        domain.setToken(UUID.randomUUID().toString());
        domain.setExpirationTime(Date.from(Instant.now()));
        domain.setUser(DomainFixtures.someUser());

        //when
        PasswordResetTokenEntity entity = passwordResetTokenEntityMapper.mapToEntity(domain);
        PasswordResetTokenEntity nullMapping = passwordResetTokenEntityMapper.mapToEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getToken(), entity.getToken());
        assertEquals(domain.getExpirationTime(), entity.getExpirationTime());
        assertEquals(PasswordResetTokenEntity.class, entity.getClass());
    }
}