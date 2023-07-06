package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @InjectMocks
    private UserProfileService userProfileService;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserProfileJpaRepository userProfileJpaRepository;

    @Test
    void findByUsernameWorksCorrectly() {
        //given
        UserProfile userProfile = DomainFixtures.someUserProfile();

        when(userProfileRepository.findByUserUsername(userProfile.getUser().getUsername())).thenReturn(userProfile);
        //when
        UserProfile result = userProfileService.findByUsername(userProfile.getUser().getUsername());

        //then
        assertNotNull(result);
        assertEquals(userProfile.getUser().getUsername(), result.getUser().getUsername());
    }

    @Test
    void saveUserProfileDataIsPresent() {
        UserProfile userProfile = DomainFixtures.someUserProfile();
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();

        when(userProfileJpaRepository.findByUserUsername(userProfile.getUser().getUsername())).thenReturn(Optional.of(userProfileEntity));
        when(userProfileRepository.findByUserUsername(userProfile.getUser().getUsername())).thenReturn(userProfile);

        //when
        userProfileService.saveUserProfileData(userProfile.getUser().getUsername(), userProfile);

        //then
        verify(userProfileRepository, times(1)).delete(userProfile);
        verify(userProfileRepository, times(1)).saveUserProfileData(userProfile);
    }
    @Test
    void saveUserProfileDataIsNotPresent() {
        //given
        UserProfile userProfile = DomainFixtures.someUserProfile();

        when(userProfileJpaRepository.findByUserUsername(userProfile.getUser().getUsername())).thenReturn(Optional.empty());

        //when
        userProfileService.saveUserProfileData(userProfile.getUser().getUsername(), userProfile);

        //then
        verify(userProfileRepository, never()).delete(any(UserProfile.class));
        verify(userProfileRepository, times(1)).saveUserProfileData(userProfile);
    }
}