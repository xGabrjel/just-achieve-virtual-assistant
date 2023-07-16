package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserProfileEntityMapperTest {

    private final UserProfileEntityMapper userProfileEntityMapper = Mappers.getMapper(UserProfileEntityMapper.class);

    @Test
    void userProfileToDomainMapperWorksCorrectly() {
        //given
        UserProfileEntity entity = EntityFixtures.someUserProfileEntity();

        //when
        UserProfile domain = userProfileEntityMapper.mapFromEntity(entity);
        UserProfile nullMapping = userProfileEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
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
        UserProfileEntity nullMapping = userProfileEntityMapper.mapToEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getAge(), entity.getAge());
        assertEquals(domain.getSex(), entity.getSex());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getPhone(), entity.getPhone());
        assertEquals(domain.getWeight(), entity.getWeight());
        assertEquals(domain.getHeight(), entity.getHeight());
        assertEquals(domain.getSurname(), entity.getSurname());
        assertEquals(UserProfileEntity.class, entity.getClass());
        assertEquals(domain.getProfileId(), entity.getProfileId());
    }

    @Test
    void nullMapFromEntityWorksCorrectly() {
        //given
        UserProfileEntity entity1 = EntityFixtures.someUserProfileEntity();
        entity1.setDietGoal(null);
        entity1.setUser(null);

        UserProfileEntity entity2 = EntityFixtures.someUserProfileEntity();
        entity2.getUser().setRoles(null);

        //when
        UserProfile domain1 = userProfileEntityMapper.mapFromEntity(entity1);
        UserProfile domain2 = userProfileEntityMapper.mapFromEntity(entity2);

        //then
        assertNull(domain1.getUser());
        assertNull(domain2.getUser().getRoles());
        assertNull(domain1.getDietGoal());
    }
    @Test
    void nullMapToEntityWorksCorrectly() {
        //given
        UserProfile domain1 = DomainFixtures.someUserProfile();
        domain1.setDietGoal(null);
        domain1.setUser(null);

        UserProfile domain2 = DomainFixtures.someUserProfile();
        domain2.getUser().setRoles(null);

        //when
        UserProfileEntity entity1 = userProfileEntityMapper.mapToEntity(domain1);
        UserProfileEntity entity2 = userProfileEntityMapper.mapToEntity(domain2);

        //then
        assertNull(entity1.getUser());
        assertNull(entity2.getUser().getRoles());
        assertNull(entity1.getDietGoal());
    }
}