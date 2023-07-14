package com.appliaction.justAchieveVirtualAssistant.security.registration.token;

import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.VerificationToken;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationTokenServiceTest {

    @InjectMocks
    private VerificationTokenService tokenService;
    @Mock
    private VerificationTokenRepository tokenRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void validateTokenWorksCorrectly() {
        //given
        String token = "valid-token";
        VerificationToken verificationToken = new VerificationToken(token, new User());
        Optional<VerificationToken> optionalToken = Optional.of(verificationToken);

        when(tokenRepository.findByToken(token))
                .thenReturn(optionalToken);

        //when
        String result = tokenService.validateToken(token);

        //then
        assertNotNull(result);
        assertEquals("VALID", result);
        assertTrue(verificationToken.getUser().getActive());
        verify(userRepository, times(1)).save(verificationToken.getUser());
    }

    @Test
    void validateTokenErrorThrowingWorksCorrectly() {
        //given
        String token = "invalid-token";
        Optional<VerificationToken> optionalToken = Optional.empty();

        when(tokenRepository.findByToken(token))
                .thenReturn(optionalToken);

        //when
        String result = tokenService.validateToken(token);

        //then
        assertNotNull(result);
        assertEquals("Invalid verification token", result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void validateTokenWithExpiredTokenWorksCorrectly() {
        //given
        String token = "expired-token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -20);
        VerificationToken verificationToken = new VerificationToken(token, new User());
        verificationToken.setExpirationTime(calendar.getTime());
        Optional<VerificationToken> optionalToken = Optional.of(verificationToken);

        when(tokenRepository.findByToken(token))
                .thenReturn(optionalToken);

        //when
        String result = tokenService.validateToken(token);

        //then
        assertNotNull(result);
        assertEquals("expired", result);
        assertFalse(verificationToken.getUser().getActive());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void saveVerificationTokenForUserWorksCorrectly() {
        //given
        User user = new User();
        String token = "token";
        VerificationToken verificationToken = new VerificationToken(token, user);

        //when
        tokenService.saveVerificationTokenForUser(user, token);

        //then
        verify(tokenRepository, times(1)).save(verificationToken);
    }

    @Test
    void findByTokenWorksCorrectly() {
        //given
        String token = "token";
        VerificationToken verificationToken = new VerificationToken(token, new User());
        Optional<VerificationToken> optionalToken = Optional.of(verificationToken);

        when(tokenRepository.findByToken(token))
                .thenReturn(optionalToken);

        //when
        Optional<VerificationToken> result = tokenService.findByToken(token);

        //then
        assertNotNull(result);
        assertEquals(optionalToken, result);
    }
}