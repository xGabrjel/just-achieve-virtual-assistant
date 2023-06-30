package com.appliaction.justAchieveVirtualAssistant.security.registration.token;

import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;

import java.util.Optional;

public interface VerificationTokenService {

    String validateToken(String token);

    void saveVerificationTokenForUser(UserEntity user, String token);

    Optional<VerificationToken> findByToken(String token);

    void deleteUserToken(int userId);
}
