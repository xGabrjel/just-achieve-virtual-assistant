package com.appliaction.justAchieveVirtualAssistant.infrastructure.database;

import com.appliaction.justAchieveVirtualAssistant.configuration.AbstractIT;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserProfileEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class UserProfileRepositoryTest extends AbstractIT {

    private final UserProfileRepository userProfileRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;
    private final UserProfileEntityMapper userProfileEntityMapper;

    @Test
    void findByUserUsernameWorksCorrectly() {
        //given
        String adminUsername = "admin";

        //when
        UserProfile result = userProfileRepository.findByUserUsername(adminUsername);

        //then
        assertNotNull(result);
        assertEquals(adminUsername, result.getUser().getUsername());
    }

    @Test
    void findByUserUsernameThrowsExceptionWorksCorrectly() {
        //given
        String username = UUID.randomUUID()
                .toString()
                .substring(0, 6);

        //when, then
        assertThatThrownBy(() ->
                userProfileRepository.findByUserUsername(username))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void saveUserProfileDataWorksCorrectly() {
        //given
        String adminUsername = "admin";
        UserProfile adminProfile = userProfileRepository.findByUserUsername(adminUsername);
        adminProfile.setAge(40);

        //when
        userProfileRepository.saveUserProfileData(adminProfile);

        //then
        assertEquals(userProfileRepository.findByUserUsername(adminUsername).getAge(), 40);
    }

    @Test
    void deleteWorksCorrectly() {
        //given
        UserEntity userEntity = EntityFixtures.someUserEntity();
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();
        UserProfile userProfile = userProfileEntityMapper.mapFromEntity(userProfileEntity);

        userJpaRepository.saveAndFlush(userEntity);
        userProfileJpaRepository.saveAndFlush(userProfileEntity);

        //when
        userProfileRepository.delete(userProfile);

        //then
        assertThatThrownBy(() ->
                userProfileRepository.findByUserUsername(userProfileEntity.getUser().getUsername()))
                .isInstanceOf(NotFoundException.class);
    }
}