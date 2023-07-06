package com.appliaction.justAchieveVirtualAssistant.security.registration.password;

import com.appliaction.justAchieveVirtualAssistant.domain.PasswordResetToken;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenServiceTest {

    @InjectMocks
    private PasswordResetTokenService tokenService;
    @Mock
    private PasswordResetTokenRepository tokenRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void validatePasswordResetTokenWorksCorrectly() {
        //given
        String token = "valid-token";
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, new User());
        Optional<PasswordResetToken> optionalToken = Optional.of(passwordResetToken);

        when(tokenRepository.findByToken(token)).thenReturn(optionalToken);

        //when
        String result = tokenService.validatePasswordResetToken(token);

        //then
        assertNotNull(result);
        assertEquals("valid", result);
    }

    @Test
    void validatePasswordResetTokenForInvalidTokenWorksCorrectly() {
        //given
        String token = "invalid-token";
        Optional<PasswordResetToken> optionalToken = Optional.empty();

        when(tokenRepository.findByToken(token)).thenReturn(optionalToken);

        //when
        String result = tokenService.validatePasswordResetToken(token);

        //then
        assertNotNull(result);
        assertEquals("invalid", result);
    }

    @Test
    void validatePasswordResetTokenForExpiredTokenWorksCorrectly() {
        //given
        String token = "expired-token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, new User());
        passwordResetToken.setExpirationTime(calendar.getTime());
        Optional<PasswordResetToken> optionalToken = Optional.of(passwordResetToken);

        when(tokenRepository.findByToken(token)).thenReturn(optionalToken);

        //when
        String result = tokenService.validatePasswordResetToken(token);

        //then
        assertNotNull(result);
        assertEquals("expired", result);
    }

    @Test
    void findUserByPasswordResetTokenWorksCorrectly() {
        //given
        String token = "token";
        User user = new User();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        Optional<PasswordResetToken> optionalToken = Optional.of(passwordResetToken);
        when(tokenRepository.findByToken(token)).thenReturn(optionalToken);

        //when
        Optional<User> result = tokenService.findUserByPasswordResetToken(token);

        //when
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void resetPasswordWorksCorrectly() {
        //given
        User user = new User();
        String newPassword = "new-password";
        String encodedPassword = "encoded-password";

        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        //when
        tokenService.resetPassword(user, newPassword);

        //then
        assertEquals(encodedPassword, user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createPasswordResetTokenForUserWorksCorrectly() {
        //given
        User user = new User();
        String token = "token";
        PasswordResetToken resetToken = new PasswordResetToken(token, user);

        //when
        tokenService.createPasswordResetTokenForUser(user, token);

        //then
        verify(tokenRepository, times(1)).save(resetToken);
    }

    @Test
    void findUserByPasswordResetTokenForNonExistingTokenWorksCorrectly() {
        //given
        String token = "non-existing-token";
        Optional<PasswordResetToken> optionalToken = Optional.empty();
        when(tokenRepository.findByToken(token)).thenReturn(optionalToken);

        //when, then
        assertThrows(NoSuchElementException.class, () -> tokenService.findUserByPasswordResetToken(token));
    }
}