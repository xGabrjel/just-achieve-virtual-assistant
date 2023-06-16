package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.util.EntityMapperFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserProfileEntityMapperTest {

    private final UserProfileEntityMapper userProfileEntityMapper = Mappers.getMapper(UserProfileEntityMapper.class);

    @Test
    void userProfileEntityMapperWorksCorrectly() {
        //given
        UserProfileEntity entity = EntityMapperFixtures.someUserProfile();

        //when
        UserProfile domain = userProfileEntityMapper.mapFromEntity(entity);

        //then
        assertEquals(entity.getAge(), domain.getAge());
        assertEquals(entity.getSex(), domain.getSex());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(UserProfile.class, domain.getClass());
        assertEquals(entity.getPhone(), domain.getPhone());
        assertEquals(entity.getWeight(), domain.getWeight());
        assertEquals(entity.getHeight(), domain.getHeight());
        assertEquals(entity.getSurname(), domain.getSurname());
        assertEquals(entity.getProfileId(), domain.getProfileId());
    }
}