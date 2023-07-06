package com.appliaction.justAchieveVirtualAssistant.security.registration.password;

import com.appliaction.justAchieveVirtualAssistant.domain.PasswordResetToken;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.PasswordResetTokenEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import com.appliaction.justAchieveVirtualAssistant.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenRepositoryTest {

    @InjectMocks
    private PasswordResetTokenRepository tokenRepository;
    @Mock
    private PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;
    @Mock
    private PasswordResetTokenEntityMapper passwordResetTokenEntityMapper;

    @Test
    void findByTokenWorksCorrectly() {
        //given
        String token = "existing-token";
        UserEntity userEntity = EntityFixtures.someUserEntity();
        User user = DomainFixtures.someUser();
        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity(token, userEntity);
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        Optional<PasswordResetTokenEntity> optionalEntity = Optional.of(passwordResetTokenEntity);

        when(passwordResetTokenJpaRepository.findByToken(token)).thenReturn(optionalEntity);
        when(passwordResetTokenEntityMapper.mapFromEntity(passwordResetTokenEntity)).thenReturn(passwordResetToken);

        //when
        Optional<PasswordResetToken> result = tokenRepository.findByToken(token);

        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(passwordResetToken, result.get());
    }

    @Test
    void findByTokenForNonExistingTokenWorksCorrectly() {
        //given
        String token = "non-existing-token";
        Optional<PasswordResetTokenEntity> optionalEntity = Optional.empty();
        when(passwordResetTokenJpaRepository.findByToken(token)).thenReturn(optionalEntity);

        //when
        Optional<PasswordResetToken> result = tokenRepository.findByToken(token);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void saveWorksCorrectly() {
        //given
        String token = "token";
        User user = DomainFixtures.someUser();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        when(passwordResetTokenEntityMapper.mapToEntity(passwordResetToken)).thenReturn(passwordResetTokenEntity);

        //when
        tokenRepository.save(passwordResetToken);

        //then
        verify(passwordResetTokenJpaRepository, times(1)).save(passwordResetTokenEntity);
    }
}