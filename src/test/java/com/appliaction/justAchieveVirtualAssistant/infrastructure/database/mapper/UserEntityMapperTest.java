package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.security.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.util.EntityMapperFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserEntityMapperTest {

    private final UserEntityMapper userEntityMapper = Mappers.getMapper(UserEntityMapper.class);

    @Test
    void userEntityMapperWorksCorrectly() {
        //given
        UserEntity entity = EntityMapperFixtures.someUser();

        //when
        User domain = userEntityMapper.mapFromEntity(entity);

        //then
        assertEquals(User.class, domain.getClass());
        assertEquals(entity.getEmail(), domain.getEmail());
        assertEquals(entity.getUserId(), domain.getUserId());
        assertEquals(entity.getActive(), domain.getActive());
    }
}