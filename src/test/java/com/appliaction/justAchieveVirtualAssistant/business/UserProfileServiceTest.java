package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @InjectMocks
    private UserProfileService userProfileService;

    @Mock
    private UserProfileRepository userProfileRepository;

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

}