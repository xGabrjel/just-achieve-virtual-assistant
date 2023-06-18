package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserProfileEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @InjectMocks
    private UserProfileService userProfileService;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserProfileEntityMapper userProfileEntityMapper;

    @Test
    void getUserProfileWorksCorrectly() {
        //given
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();
        UserProfile userProfile = DomainFixtures.someUserProfile();

        when(userProfileRepository.findByPhone(userProfileEntity.getPhone())).thenReturn(Set.of(userProfileEntity));
        when(userProfileEntityMapper.mapFromEntity(userProfileEntity)).thenReturn(userProfile);

        //when
        UserProfile result = userProfileService.getUserProfile(userProfileEntity.getPhone());

        //then
        assertNotNull(result);
        verify(userProfileRepository, times(1)).findByPhone(userProfileEntity.getPhone());
        verify(userProfileEntityMapper, times(1)).mapFromEntity(userProfileEntity);
    }

    @Test
    void getUserProfileExceptionThrowingWorksCorrectly() {
        //given
        String phoneNumber = "+48 511 533 522";

        //when
        when(userProfileRepository.findByPhone(phoneNumber)).thenReturn(Collections.emptySet());

        //then
        assertThrows(NotFoundException.class, () -> userProfileService.getUserProfile(phoneNumber));
        verify(userProfileRepository, times(1)).findByPhone(phoneNumber);
    }
}