package com.appliaction.justAchieveVirtualAssistant.security.registration.token;

import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository tokenRepository;
    private final UserJpaRepository userRepository;

    @Override
    public String validateToken(String token) {
        Optional<VerificationToken> theToken = tokenRepository.findByToken(token);
        if (theToken.isEmpty()) {
            return "Invalid verification token";
        }
        UserEntity user = theToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if ((theToken.get().getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "expired";
        }
        user.setActive(true);
        userRepository.save(user);
        return "VALID";
    }

    @Override
    public void saveVerificationTokenForUser(UserEntity user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void deleteUserToken(int userId) {
        tokenRepository.deleteByUserUserId(userId);
    }
}
