package com.appliaction.justAchieveVirtualAssistant.security.registration.password;

import com.appliaction.justAchieveVirtualAssistant.domain.PasswordResetToken;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.PasswordResetTokenEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PasswordResetTokenRepository {

    private final PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;
    private final PasswordResetTokenEntityMapper passwordResetTokenEntityMapper;

    public Optional<PasswordResetToken> findByToken(String theToken) {
        return passwordResetTokenJpaRepository.findByToken(theToken).stream().map(passwordResetTokenEntityMapper::mapFromEntity).findFirst();
    }

    public void save(PasswordResetToken passwordResetToken) {
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenEntityMapper.mapToEntity(passwordResetToken);
        passwordResetTokenJpaRepository.save(passwordResetTokenEntity);
    }
}
