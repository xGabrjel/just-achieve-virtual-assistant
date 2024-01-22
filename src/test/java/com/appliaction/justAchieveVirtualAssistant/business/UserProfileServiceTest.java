package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.api.dto.DietGoalsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.UserProfileDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserService;
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
import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @Mock
    private UserService userService;
    @Mock
    private DietGoalsService dietGoalsService;

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
        UserProfileDTO userDTO = UserProfileDTO.builder()
                .age(userProfile.getAge())
                .name(userProfile.getName())
                .surname(userProfile.getSurname())
                .sex(userProfile.getSex())
                .phone(userProfile.getPhone())
                .height(userProfile.getHeight())
                .weight(userProfile.getWeight())
                .dietGoal(new DietGoalsDTO(userProfile.getDietGoal().getDietGoal()))
                .build();
        UserProfileEntity userProfileEntity = EntityFixtures.someUserProfileEntity();
        String fileName = "file";
        byte[] fileContent = new byte[]{0x01, 0x23, 0x45, 0x67};
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileContent);

        when(userProfileJpaRepository.findByUserUsername(userProfile.getUser().getUsername()))
                .thenReturn(Optional.of(userProfileEntity));
        when(userProfileRepository.findByUserUsername(userProfile.getUser().getUsername()))
                .thenReturn(userProfile);
        when(userService.findByUsername(userProfile.getUser().getUsername()))
                .thenReturn(Optional.of(new User()));
        when(dietGoalsService.findById(1))
                .thenReturn(Optional.of(DietGoals.builder().build()));

        //when
        userProfileService.saveUserProfileData(userProfile.getUser().getUsername(), 1, userDTO, multipartFile);

        //then
        verify(userProfileRepository, times(1)).delete(any(UserProfile.class));
        verify(userProfileRepository, times(1)).saveUserProfileData(any(UserProfile.class));
    }

    @Test
    void saveUserProfileDataIsNotPresentWorksCorrectly() throws IOException {
        //given
        UserProfile userProfile = DomainFixtures.someUserProfile();
        UserProfileDTO userDTO = UserProfileDTO.builder()
                .age(userProfile.getAge())
                .name(userProfile.getName())
                .surname(userProfile.getSurname())
                .sex(userProfile.getSex())
                .phone(userProfile.getPhone())
                .height(userProfile.getHeight())
                .weight(userProfile.getWeight())
                .dietGoal(new DietGoalsDTO(userProfile.getDietGoal().getDietGoal()))
                .build();
        String fileName = "file";
        byte[] fileContent = new byte[]{0x01, 0x23, 0x45, 0x67};
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileContent);

        when(userProfileJpaRepository.findByUserUsername(userProfile.getUser().getUsername()))
                .thenReturn(Optional.empty());
        when(userService.findByUsername(userProfile.getUser().getUsername()))
                .thenReturn(Optional.of(new User()));
        when(dietGoalsService.findById(1))
                .thenReturn(Optional.of(DietGoals.builder().build()));

        //when
        userProfileService.saveUserProfileData(userProfile.getUser().getUsername(), 1, userDTO, multipartFile);

        //then
        verify(userProfileRepository, never()).delete(any(UserProfile.class));
        verify(userProfileRepository, times(1)).saveUserProfileData(any(UserProfile.class));
    }

    @Test
    void testIsProfileCompletedWithValidPrincipal() {
        //given
        Principal principal = mock(Principal.class);
        UserProfile userProfile = mock(UserProfile.class);

        //when
        when(principal.getName()).thenReturn("testUser");
        when(userProfileRepository.findByUserUsername("testUser")).thenReturn(userProfile);

        //then
        assertTrue(userProfileService.isProfileCompleted(principal));
        verify(userProfileRepository, times(1)).findByUserUsername("testUser");

    }

    @Test
    void testIsProfileCompletedWithNullPrincipal() {
        //given, when, then
        assertFalse(userProfileService.isProfileCompleted(null));
        verify(userProfileRepository, never()).findByUserUsername(any());
    }

    @Test
    void testIsProfileCompletedWithInvalidPrincipal() {
        //given
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("invalidUser");
        when(userProfileRepository.findByUserUsername("invalidUser")).thenReturn(null);

        //when, then
        assertFalse(userProfileService.isProfileCompleted(principal));
        verify(userProfileRepository, times(1)).findByUserUsername("invalidUser");
    }
}