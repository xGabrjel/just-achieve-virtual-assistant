package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.VerificationToken;
import com.appliaction.justAchieveVirtualAssistant.security.event.Listener.RegistrationCompleteEventListener;
import com.appliaction.justAchieveVirtualAssistant.security.registration.RegistrationController;
import com.appliaction.justAchieveVirtualAssistant.security.registration.RegistrationRequest;
import com.appliaction.justAchieveVirtualAssistant.security.registration.password.PasswordResetTokenService;
import com.appliaction.justAchieveVirtualAssistant.security.registration.token.VerificationTokenService;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserService;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RegistrationControllerTest {

    private final MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private VerificationTokenService tokenService;
    @MockBean
    private PasswordResetTokenService passwordResetTokenService;
    @MockBean
    private final RegistrationCompleteEventListener eventListener;

    @Test
    void showRegistrationFormWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/registration/registration-form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("registration"));
    }

    @Test
    void registerUserWorksCorrectly() throws Exception {
        //given
        User user = new User();
        RegistrationRequest registration = new RegistrationRequest();

        when(userService.registerUser(registration)).thenReturn(user);

        //when, then
        mockMvc.perform(post("/registration/register")
                .flashAttr("user", registration))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/registration-form?success"));
    }

    @Test
    void verifyEmailRedirectToLoginWhenTokenValidAndUserActiveWorksCorrectly() throws Exception {
        // given
        String validToken = "valid";
        User activeUser = DomainFixtures.someUser();
        VerificationToken verificationToken = new VerificationToken(validToken, activeUser);

        when(tokenService.findByToken(validToken))
                .thenReturn(Optional.of(verificationToken));

        // when, then
        mockMvc.perform(get("/registration/verifyEmail")
                .param("token", validToken))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?verified"));
    }

    @Test
    void verifyEmailRedirectToErrorWhenTokenExpiredWorksCorrectly() throws Exception {
        // given
        String expiredToken = "expired-token";
        User user = DomainFixtures.someUser().withActive(false);
        VerificationToken verificationToken = new VerificationToken(expiredToken, user);

        when(tokenService.findByToken(expiredToken))
                .thenReturn(Optional.of(verificationToken));
        when(tokenService.validateToken(expiredToken))
                .thenReturn("expired");

        // when, then
        mockMvc.perform(get("/registration/verifyEmail")
                .param("token", expiredToken))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error?expired"));
    }

    @Test
    void verifyEmailRedirectToLoginWhenTokenValidWorksCorrectly() throws Exception {
        // given
        String validToken = "valid-token";
        User user = DomainFixtures.someUser().withActive(false);
        VerificationToken verificationToken = new VerificationToken(validToken, user);

        when(tokenService.findByToken(validToken))
                .thenReturn(Optional.of(verificationToken));
        when(tokenService.validateToken(validToken))
                .thenReturn("valid");

        // when, then
        mockMvc.perform(get("/registration/verifyEmail")
                .param("token", validToken))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?valid"));
    }

    @Test
    void verifyEmailRedirectToErrorWhenTokenInvalidWorksCorrectly() throws Exception {
        // given
        String invalidToken = "invalid-token";
        User user = DomainFixtures.someUser().withActive(false);

        when(tokenService.validateToken(invalidToken))
                .thenReturn("");
        when(tokenService.findByToken(invalidToken))
                .thenReturn(Optional.empty());

        // when, then
        mockMvc.perform(get("/registration/verifyEmail")
                .param("token", invalidToken))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error?invalid"));
    }

    @Test
    void forgotPasswordWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/registration/forgot-password-request"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgot-password-form"));
    }

    @Test
    void resetPasswordRequestRedirectToNotFoundWhenEmailNotFoundWorksCorrectly() throws Exception {
        // given
        String notFoundEmail = "notfound@example.com";

        when(userService.findByEmail(notFoundEmail))
                .thenReturn(Optional.empty());

        // when, then
        mockMvc.perform(post("/registration/forgot-password")
                .param("email", notFoundEmail))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/forgot-password-request?not_found"));
    }

    @Test
    void resetPasswordRequestRedirectToSuccessWhenEmailFoundWorksCorrectly() throws Exception {
        // given
        User user = DomainFixtures.someUser();

        when(userService.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        // when, then
        mockMvc.perform(post("/registration/forgot-password")
                .param("email", user.getEmail()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration/forgot-password-request?success"));
    }

    @Test
    void passwordResetFormReturnPasswordResetFormViewWorksCorrectly() throws Exception {
        // given
        String token = "testToken";

        // when, then
        mockMvc.perform(get("/registration/password-reset-form")
                .param("token", token))
                .andExpect(status().isOk())
                .andExpect(view().name("password-reset-form"))
                .andExpect(model().attributeExists("token"))
                .andExpect(model().attribute("token", token));
    }

    @Test
    void resetPasswordReturnErrorWhenTokenInvalidWorksCorrectly() throws Exception {
        // given
        String invalidToken = "invalid-token";
        String password = "newPassword";
        String tokenVerificationResult = "invalid";

        when(passwordResetTokenService.validatePasswordResetToken(invalidToken))
                .thenReturn(tokenVerificationResult);

        // when, then
        mockMvc.perform(post("/registration/reset-password")
                .param("token", invalidToken)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error?invalid_token"));
    }

    @Test
    void resetPasswordReturnSuccessWhenTokenValidWorksCorrectly() throws Exception {
        // given
        String validToken = "valid-token";
        String password = "newPassword";
        Optional<User> user = Optional.of(DomainFixtures.someUser());

        when(passwordResetTokenService.validatePasswordResetToken(validToken))
                .thenReturn("valid");
        when(passwordResetTokenService.findUserByPasswordResetToken(validToken))
                .thenReturn(user);

        // when, then
        mockMvc.perform(post("/registration/reset-password")
                .param("token", validToken)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?reset_success"));
    }

    @Test
    void resetPasswordReturnErrorWhenUserNotFoundWorksCorrectly() throws Exception {
        // given
        String validToken = "valid-token";
        String password = "newPassword";
        Optional<User> user = Optional.empty();

        when(passwordResetTokenService.validatePasswordResetToken(validToken))
                .thenReturn("valid");
        when(passwordResetTokenService.findUserByPasswordResetToken(validToken))
                .thenReturn(user);

        // when, then
        mockMvc.perform(post("/registration/reset-password")
                .param("token", validToken)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error?not_found"));
    }
}