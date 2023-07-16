package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserEntityMapperTest {

    private final UserEntityMapper userEntityMapper = Mappers.getMapper(UserEntityMapper.class);

    @Test
    void userEntityMapFromEntityWorksCorrectly() {
        //given
        UserEntity entity = EntityFixtures.someUserEntity();

        //when
        User domain = userEntityMapper.mapFromEntity(entity);
        User nullMapping = userEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(User.class, domain.getClass());
        assertEquals(entity.getEmail(), domain.getEmail());
        assertEquals(entity.getUserId(), domain.getUserId());
        assertEquals(entity.getActive(), domain.getActive());
        assertEquals(entity.getUsername(), domain.getUsername());
        assertEquals(entity.getPassword(), domain.getPassword());
        assertEquals(entity.getRoles().toArray().length, domain.getRoles().toArray().length);
    }
    @Test
    void userEntityMapToEntityWorksCorrectly() {
        //given
        User domain = DomainFixtures.someUser();

        //when
        UserEntity entity = userEntityMapper.mapToEntity(domain);
        UserEntity nullMapping = userEntityMapper.mapToEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(UserEntity.class, entity.getClass());
        assertEquals(domain.getEmail(), entity.getEmail());
        assertEquals(domain.getUserId(), entity.getUserId());
        assertEquals(domain.getActive(), entity.getActive());
        assertEquals(domain.getUsername(), entity.getUsername());
        assertEquals(domain.getPassword(), entity.getPassword());
        assertEquals(domain.getRoles().toArray().length, entity.getRoles().toArray().length);
    }

    @Test
    void nullMapFromEntityWorksCorrectly() {
        //given
        UserEntity entity1 = EntityFixtures.someUserEntity();
        entity1.setRoles(null);

        //when
        User domain1 = userEntityMapper.mapFromEntity(entity1);

        //then
        assertNull(domain1.getRoles());
    }
    @Test
    void nullMapToEntityWorksCorrectly() {
        //given
        User domain1 = DomainFixtures.someUser();
        domain1.setRoles(null);

        //when
        UserEntity entity1 = userEntityMapper.mapToEntity(domain1);

        //then
        assertNull(entity1.getRoles());
    }
}