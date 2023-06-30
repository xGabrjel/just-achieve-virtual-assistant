package com.appliaction.justAchieveVirtualAssistant.security.registration.password;

import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;

import java.util.Optional;

public interface PasswordResetTokenService {
    String validatePasswordResetToken(String theToken);

    Optional<UserEntity> findUserByPasswordResetToken(String theToken);

    void resetPassword(UserEntity theUser, String password);

    void createPasswordResetTokenForUser(UserEntity user, String passwordResetToken);
}
