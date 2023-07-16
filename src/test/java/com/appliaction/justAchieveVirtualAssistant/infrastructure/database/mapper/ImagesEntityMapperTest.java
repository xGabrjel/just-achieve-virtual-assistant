package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.DietGoalsEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.ImagesEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ImagesEntityMapperTest {

    private final ImagesEntityMapper imagesEntityMapper = Mappers.getMapper(ImagesEntityMapper.class);

    @Test
    void imagesMapFromEntityWorksCorrectly() {
        //given
        ImagesEntity entity = ImagesEntity.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .profileId(EntityFixtures.someUserProfileEntity())
                .build();

        //when
        Images domain = imagesEntityMapper.mapFromEntity(entity);
        Images nullMapping = imagesEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getType(), domain.getType());
        assertArrayEquals(entity.getImageData(), domain.getImageData());
        assertEquals(UserProfile.class, domain.getProfileId().getClass());
        assertEquals(User.class, domain.getProfileId().getUser().getClass());
        assertEquals(entity.getProfileId().getSex(), domain.getProfileId().getSex());
        assertEquals(entity.getProfileId().getAge(), domain.getProfileId().getAge());
        assertEquals(DietGoals.class, domain.getProfileId().getDietGoal().getClass());
        assertEquals(entity.getProfileId().getName(), domain.getProfileId().getName());
        assertEquals(entity.getProfileId().getPhone(), domain.getProfileId().getPhone());
        assertEquals(entity.getProfileId().getWeight(), domain.getProfileId().getWeight());
        assertEquals(entity.getProfileId().getHeight(), domain.getProfileId().getHeight());
        assertEquals(entity.getProfileId().getSurname(), domain.getProfileId().getSurname());
        assertEquals(entity.getProfileId().getUser().getEmail(), domain.getProfileId().getUser().getEmail());
        assertEquals(entity.getProfileId().getUser().getActive(), domain.getProfileId().getUser().getActive());
        assertEquals(entity.getProfileId().getUser().getUserId(), domain.getProfileId().getUser().getUserId());
        assertEquals(entity.getProfileId().getUser().getUsername(), domain.getProfileId().getUser().getUsername());
        assertEquals(entity.getProfileId().getUser().getPassword(), domain.getProfileId().getUser().getPassword());
        assertEquals(entity.getProfileId().getDietGoal().getDietGoal(), domain.getProfileId().getDietGoal().getDietGoal());
        assertEquals(entity.getProfileId().getDietGoal().getDietGoalId(), domain.getProfileId().getDietGoal().getDietGoalId());
        assertEquals(entity.getProfileId().getUser().getRoles().toArray().length, domain.getProfileId().getUser().getRoles().toArray().length);
    }
    @Test
    void imagesMapToEntityWorksCorrectly() {
        //given
        Images domain = Images.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .profileId(DomainFixtures.someUserProfile())
                .build();

        //when
        ImagesEntity entity = imagesEntityMapper.mapToEntity(domain);
        ImagesEntity nullMapping = imagesEntityMapper.mapToEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getType(), entity.getType());
        assertArrayEquals(domain.getImageData(), entity.getImageData());
        assertEquals(UserProfileEntity.class, entity.getProfileId().getClass());
        assertEquals(UserEntity.class, entity.getProfileId().getUser().getClass());
        assertEquals(domain.getProfileId().getAge(), entity.getProfileId().getAge());
        assertEquals(domain.getProfileId().getSex(), entity.getProfileId().getSex());
        assertEquals(domain.getProfileId().getName(), entity.getProfileId().getName());
        assertEquals(domain.getProfileId().getPhone(), entity.getProfileId().getPhone());
        assertEquals(domain.getProfileId().getHeight(), entity.getProfileId().getHeight());
        assertEquals(domain.getProfileId().getWeight(), entity.getProfileId().getWeight());
        assertEquals(DietGoalsEntity.class, entity.getProfileId().getDietGoal().getClass());
        assertEquals(domain.getProfileId().getSurname(), entity.getProfileId().getSurname());
        assertEquals(domain.getProfileId().getUser().getEmail(), entity.getProfileId().getUser().getEmail());
        assertEquals(domain.getProfileId().getUser().getUserId(), entity.getProfileId().getUser().getUserId());
        assertEquals(domain.getProfileId().getUser().getActive(), entity.getProfileId().getUser().getActive());
        assertEquals(domain.getProfileId().getUser().getPassword(), entity.getProfileId().getUser().getPassword());
        assertEquals(domain.getProfileId().getUser().getUsername(), entity.getProfileId().getUser().getUsername());
        assertEquals(domain.getProfileId().getDietGoal().getDietGoal(), entity.getProfileId().getDietGoal().getDietGoal());
        assertEquals(domain.getProfileId().getDietGoal().getDietGoalId(), entity.getProfileId().getDietGoal().getDietGoalId());
        assertEquals(domain.getProfileId().getUser().getRoles().toArray().length, entity.getProfileId().getUser().getRoles().toArray().length);
    }

    @Test
    void nullMapFromEntity() {
        //given
        ImagesEntity entity1 = ImagesEntity.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .profileId(EntityFixtures.someUserProfileEntity())
                .build();

        entity1.getProfileId().setDietGoal(null);
        entity1.getProfileId().getUser().setRoles(null);

        ImagesEntity entity2 = ImagesEntity.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .profileId(EntityFixtures.someUserProfileEntity())
                .build();

        entity2.getProfileId().setUser(null);


        ImagesEntity entity3 = ImagesEntity.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .profileId(null)
                .build();

        //when
        Images domain1 = imagesEntityMapper.mapFromEntity(entity1);
        Images domain2 = imagesEntityMapper.mapFromEntity(entity2);
        Images domain3 = imagesEntityMapper.mapFromEntity(entity3);

        //then
        assertNull(domain3.getProfileId());
        assertNull(domain2.getProfileId().getUser());
        assertNull(domain1.getProfileId().getDietGoal());
        assertNull(domain1.getProfileId().getUser().getRoles());
    }
    @Test
    void nullMapToEntity() {
        //given
        Images domain1 = Images.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .profileId(DomainFixtures.someUserProfile())
                .build();

        domain1.getProfileId().setDietGoal(null);
        domain1.getProfileId().getUser().setRoles(null);

        Images domain2 = Images.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .profileId(DomainFixtures.someUserProfile())
                .build();

        domain2.getProfileId().setUser(null);


        Images domain3 = Images.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .profileId(null)
                .build();

        //when
        ImagesEntity entity1 = imagesEntityMapper.mapToEntity(domain1);
        ImagesEntity entity2 = imagesEntityMapper.mapToEntity(domain2);
        ImagesEntity entity3 = imagesEntityMapper.mapToEntity(domain3);

        //then
        assertNull(entity3.getProfileId());
        assertNull(entity2.getProfileId().getUser());
        assertNull(entity1.getProfileId().getDietGoal());
        assertNull(entity1.getProfileId().getUser().getRoles());
    }
}