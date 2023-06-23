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

        when(userProfileJpaRepository.findByPhone(userProfileEntity.getPhone())).thenReturn(Optional.of(userProfileEntity));
        when(userProfileEntityMapper.mapFromEntity(userProfileEntity)).thenReturn(userProfile);

        //when
        UserProfile result = userProfileRepository.getUserProfile(userProfileEntity.getPhone());

        //then
        assertNotNull(result);
        verify(userProfileJpaRepository, times(1)).findByPhone(userProfileEntity.getPhone());
        verify(userProfileEntityMapper, times(1)).mapFromEntity(userProfileEntity);
    }

    @Test
    void getUserProfileExceptionThrowingWorksCorrectly() {
        //given
        String phoneNumber = "+48 511 533 522";

        when(userProfileJpaRepository.findByPhone(phoneNumber)).thenReturn(Optional.empty());

        //when, then
        assertThrows(NotFoundException.class, () -> userProfileRepository.getUserProfile(phoneNumber));
        verify(userProfileJpaRepository, times(1)).findByPhone(phoneNumber);
    }
}