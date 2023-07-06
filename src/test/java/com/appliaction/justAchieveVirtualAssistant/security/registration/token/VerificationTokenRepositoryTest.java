package com.appliaction.justAchieveVirtualAssistant.security.registration.token;

import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.VerificationToken;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.VerificationTokenEntityMapper;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificationTokenRepositoryTest {

    @InjectMocks
    private VerificationTokenRepository tokenRepository;
    @Mock
    private VerificationTokenJpaRepository verificationTokenJpaRepository;
    @Mock
    private VerificationTokenEntityMapper verificationTokenEntityMapper;

    @Test
    void findByTokenForExistingTokenWorksCorrectly() {
        //given
        String token = "existing-token";
        UserEntity userEntity = EntityFixtures.someUserEntity();
        User user = DomainFixtures.someUser();
        VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity(token, userEntity);
        VerificationToken verificationToken = new VerificationToken(token, user);
        Optional<VerificationTokenEntity> optionalEntity = Optional.of(verificationTokenEntity);

        when(verificationTokenJpaRepository.findByToken(token)).thenReturn(optionalEntity);
        when(verificationTokenEntityMapper.mapFromEntity(verificationTokenEntity)).thenReturn(verificationToken);

        //when
        Optional<VerificationToken> result = tokenRepository.findByToken(token);

        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(verificationToken, result.get());
    }

    @Test
    void findByTokenForNonExistingTokenWorksCorrectly() {
        //given
        String token = "non-existing-token";
        Optional<VerificationTokenEntity> optionalEntity = Optional.empty();
        when(verificationTokenJpaRepository.findByToken(token)).thenReturn(optionalEntity);

        //when
        Optional<VerificationToken> result = tokenRepository.findByToken(token);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void saveWorksCorrectly() {
        //given
        String token = "existing-token";
        User user = DomainFixtures.someUser();
        VerificationToken verificationToken = new VerificationToken(token, user);
        VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity();
        when(verificationTokenEntityMapper.mapToEntity(verificationToken)).thenReturn(verificationTokenEntity);
        when(verificationTokenJpaRepository.save(verificationTokenEntity)).thenReturn(verificationTokenEntity);

        //when
        VerificationTokenEntity result = tokenRepository.save(verificationToken);

        //then
        assertNotNull(result);
        assertEquals(verificationTokenEntity, result);
    }
}