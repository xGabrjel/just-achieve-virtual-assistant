package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserProfileEntityMapperTest {

    private final UserProfileEntityMapper userProfileEntityMapper = Mappers.getMapper(UserProfileEntityMapper.class);

    @Test
    void userProfileToDomainMapperWorksCorrectly() {
        //given
        UserProfileEntity entity = EntityFixtures.someUserProfileEntity();

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

    @Test
    void userProfileToEntityMapperWorksCorrectly() {
        //given
        UserProfile domain = DomainFixtures.someUserProfile();

        //when
        UserProfileEntity entity = userProfileEntityMapper.mapToEntity(domain);

        //then
        assertEquals(domain.getAge(), entity.getAge());
        assertEquals(domain.getSex(), entity.getSex());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(UserProfileEntity.class, entity.getClass());
        assertEquals(domain.getPhone(), entity.getPhone());
        assertEquals(domain.getWeight(), entity.getWeight());
        assertEquals(domain.getHeight(), entity.getHeight());
        assertEquals(domain.getSurname(), entity.getSurname());
        assertEquals(domain.getProfileId(), entity.getProfileId());
    }
}