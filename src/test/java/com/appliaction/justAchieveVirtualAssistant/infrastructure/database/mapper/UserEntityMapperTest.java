package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserEntityMapperTest {

    private final UserEntityMapper userEntityMapper = Mappers.getMapper(UserEntityMapper.class);

    @Test
    void userEntityMapperWorksCorrectly() {
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
        assertEquals(entity.getUsername(), domain.getUsername());
        assertEquals(entity.getActive(), domain.getActive());
    }
}