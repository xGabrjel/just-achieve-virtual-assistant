package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserProfileDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.UserProfileMapper;
import com.appliaction.justAchieveVirtualAssistant.business.DietGoalsService;
import com.appliaction.justAchieveVirtualAssistant.business.ImagesService;
import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserService;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class UserProfileControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private UserProfileService userProfileService;
    @MockBean
    private UserProfileMapper userProfileMapper;
    @MockBean
    private DietGoalsService dietGoalsService;
    @MockBean
    private UserService userService;
    @MockBean
    private ImagesService imagesService;

    @Test
    @WithMockUser
    void homeProfilePageWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/user-profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"));
    }

    @Test
    @WithMockUser
    void profilePageReturnUserProfileViewWithUserProfileAttributeWorksCorrectly() throws Exception {
        // given
        User user = DomainFixtures.someUser();
        String username = user.getUsername();
        byte[] fileContent = new byte[] { 0x01, 0x23, 0x45, 0x67};
        String imageBase64 = Base64.getEncoder().encodeToString(fileContent);
        UserProfile userProfile = DomainFixtures.someUserProfile().withUser(user);
        UserProfileDTO userProfileDTO = userProfileMapper.map(userProfile);

        Principal principal = () -> username;

        when(userProfileService.findByUsername(username))
                .thenReturn(userProfile);
        when(userProfileMapper.map(userProfile))
                .thenReturn(userProfileDTO);
        when(imagesService.downloadImageByProfileId(username))
                .thenReturn(fileContent);

        // when, then
        mockMvc.perform(get("/user-profile/data")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userProfile", userProfileDTO))
                .andExpect(model().attribute("imageBase64", imageBase64))
                .andExpect(view().name("user-profile"));
    }
    @Test
    @WithMockUser
    void profilePageReturnUserProfileViewWithoutUserProfileAttributeWorksCorrectly() throws Exception {
        // given
        String username = "testUser";
        byte[] fileContent = new byte[] { 0x01, 0x23, 0x45, 0x67};
        String imageBase64 = Base64.getEncoder().encodeToString(fileContent);
        Principal principal = () -> username;

        when(userProfileService.findByUsername(username))
                .thenReturn(null);
        when(imagesService.downloadImageByProfileId(username))
                .thenReturn(fileContent);

        // when, then
        mockMvc.perform(get("/user-profile/data")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("userProfile"))
                .andExpect(model().attributeExists("imageBase64"))
                .andExpect(view().name("user-profile"));
    }

    @Test
    @WithMockUser
    void submitProfileDataRedirectToUserProfileWithSuccessParameterWorksCorrectly() throws Exception {
        // given
        String username = "testUser";
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        Integer dietGoalId = 1;

        Principal principal = () -> username;
        User user = new User().withUsername(username);
        DietGoals dietGoals = DomainFixtures.someDietGoals();

        when(userService.findByUsername(username))
                .thenReturn(Optional.of(user));
        when(dietGoalsService.findById(dietGoalId))
                .thenReturn(Optional.of(dietGoals));

        // when, then
        mockMvc.perform(post("/user-profile/new-user-profile-data")
                .flashAttr("userProfileDTO", userProfileDTO)
                .param("dietGoalId", dietGoalId.toString())
                .principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user-profile?success"));
    }

    @ParameterizedTest
    @MethodSource("phoneValidationData")
    @WithMockUser
    public void phoneValidationForUserProfileWorksCorrectly(Boolean expectedResult, String phoneNumber) throws Exception {
        //given
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setPhone(phoneNumber);

        String username = "testUser";
        User user = new User().withUsername(username);
        Principal principal = () -> username;

        DietGoals dietGoals = DomainFixtures.someDietGoals()
                .withDietGoalId(1);

        when(userService.findByUsername(username))
                .thenReturn(Optional.of(user));
        when(dietGoalsService.findById(1))
                .thenReturn(Optional.of(dietGoals));

        //when, then
        if (expectedResult) {
            mockMvc.perform(post("/user-profile/new-user-profile-data")
                            .principal(principal)
                            .flashAttr("userProfileDTO", userProfileDTO)
                            .param("dietGoalId", "1"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/user-profile?success"));
        } else {
            mockMvc.perform(post("/user-profile/new-user-profile-data")
                    .principal(principal)
                    .flashAttr("userProfileDTO", userProfileDTO)
                    .param("dietGoalId", "1"))
                    .andExpect(status().isBadRequest())
                    .andExpect(view().name("default-error"));
        }
    }
    public static Stream<Arguments> phoneValidationData() {
        return Stream.of(
                Arguments.of(false, ""),
                Arguments.of(false, "+48 504 203 260@@"),
                Arguments.of(false, "+48.504.203.260"),
                Arguments.of(false, "+55(123) 456-78-90-"),
                Arguments.of(false, "+55(123) - 456-78-90"),
                Arguments.of(false, "504.203.260"),
                Arguments.of(false, " "),
                Arguments.of(false, "-"),
                Arguments.of(false, "()"),
                Arguments.of(false, "() + ()"),
                Arguments.of(false, "(21 777"),
                Arguments.of(false, "+"),
                Arguments.of(false, " 1"),
                Arguments.of(false, "1"),
                Arguments.of(false, "+48 (12) 504 203 260"),
                Arguments.of(false, "+48 (12) 504-203-260"),
                Arguments.of(false, "+48(12)504203260"),
                Arguments.of(false, "555-5555-555"),
                Arguments.of(true, "+48 504 203 260")
        );
    }
}