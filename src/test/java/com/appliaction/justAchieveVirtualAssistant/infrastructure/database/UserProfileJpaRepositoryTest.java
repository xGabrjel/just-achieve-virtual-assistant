package com.appliaction.justAchieveVirtualAssistant.infrastructure.database;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserProfileEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileJpaRepositoryTest {

    @InjectMocks
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserProfileJpaRepository userProfileJpaRepository;

    @Mock
    private UserProfileEntityMapper userProfileEntityMapper;

    @Test
    void getUserProfileWorksCorrectly() {
        //given
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();
        UserProfile userProfile = DomainFixtures.someUserProfile();

        when(userProfileJpaRepository.findByUserUsername(userProfileEntity.getUser().getUsername())).thenReturn(Optional.of(userProfileEntity));
        when(userProfileEntityMapper.mapFromEntity(userProfileEntity)).thenReturn(userProfile);

        //when
        UserProfile result = userProfileRepository.findByUserUsername(userProfileEntity.getUser().getUsername());

        //then
        assertNotNull(result);
        verify(userProfileJpaRepository, times(1)).findByUserUsername(userProfileEntity.getUser().getUsername());
        verify(userProfileEntityMapper, times(1)).mapFromEntity(userProfileEntity);
    }

    @Test
    void getUserProfileExceptionThrowingWorksCorrectly() {
        //given
        String username = "test1";

        when(userProfileJpaRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        //when, then
        assertThrows(NotFoundException.class, () -> userProfileRepository.findByUserUsername(username));
        verify(userProfileJpaRepository, times(1)).findByUserUsername(username);
    }
}