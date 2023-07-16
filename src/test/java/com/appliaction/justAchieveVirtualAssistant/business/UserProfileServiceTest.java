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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Mock
    private ImagesService imagesService;

    @Test
    void findByUsernameWorksCorrectly() {
        //given
        UserProfile userProfile = DomainFixtures.someUserProfile();

        when(userProfileRepository.findByUserUsername(userProfile.getUser().getUsername()))
                .thenReturn(userProfile);

        //when
        UserProfile result = userProfileService.findByUsername(userProfile.getUser().getUsername());

        //then
        assertNotNull(result);
        assertEquals(userProfile.getUser().getUsername(), result.getUser().getUsername());
    }

    @Test
    void saveUserProfileDataIsPresentWorksCorrectly() throws IOException {
        //given
        UserProfile userProfile = DomainFixtures.someUserProfile();
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();
        String fileName = "file";
        byte[] fileContent = new byte[] { 0x01, 0x23, 0x45, 0x67};
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileContent);

        when(userProfileJpaRepository.findByUserUsername(userProfile.getUser().getUsername()))
                .thenReturn(Optional.of(userProfileEntity));
        when(userProfileRepository.findByUserUsername(userProfile.getUser().getUsername()))
                .thenReturn(userProfile);

        //when
        userProfileService.saveUserProfileData(userProfile.getUser().getUsername(), userProfile, multipartFile);

        //then
        verify(userProfileRepository, times(1)).delete(userProfile);
        verify(userProfileRepository, times(1)).saveUserProfileData(userProfile);
    }
    @Test
    void saveUserProfileDataIsNotPresentWorksCorrectly() throws IOException {
        //given
        UserProfile userProfile = DomainFixtures.someUserProfile();
        String fileName = "file";
        byte[] fileContent = new byte[] { 0x01, 0x23, 0x45, 0x67};
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileContent);

        when(userProfileJpaRepository.findByUserUsername(userProfile.getUser().getUsername()))
                .thenReturn(Optional.empty());

        //when
        userProfileService.saveUserProfileData(userProfile.getUser().getUsername(), userProfile, multipartFile);

        //then
        verify(userProfileRepository, never()).delete(any(UserProfile.class));
        verify(userProfileRepository, times(1)).saveUserProfileData(userProfile);
    }
}