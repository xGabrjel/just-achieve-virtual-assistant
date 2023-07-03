package com.appliaction.justAchieveVirtualAssistant.security.registration.token;

import com.appliaction.justAchieveVirtualAssistant.domain.VerificationToken;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.VerificationTokenEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class VerificationTokenRepository {

    private final VerificationTokenJpaRepository verificationTokenJpaRepository;
    private final VerificationTokenEntityMapper verificationTokenEntityMapper;

    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenJpaRepository.findByToken(token).stream()
                .map(verificationTokenEntityMapper::mapFromEntity)
                .findFirst();
    }

    public VerificationTokenEntity save(VerificationToken verificationToken) {
        VerificationTokenEntity verificationTokenEntity = verificationTokenEntityMapper.mapToEntity(verificationToken);
        return verificationTokenJpaRepository.save(verificationTokenEntity);
    }

    public void deleteByUserUserId(int userId) {
        verificationTokenJpaRepository.deleteByUserUserId(userId);
    }
}
