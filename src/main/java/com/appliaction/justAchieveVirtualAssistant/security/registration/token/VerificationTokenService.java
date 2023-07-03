package com.appliaction.justAchieveVirtualAssistant.security.registration.token;

import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.VerificationToken;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public String validateToken(String token) {
        Optional<VerificationToken> theToken = tokenRepository.findByToken(token);
        if (theToken.isEmpty()) {
            return "Invalid verification token";
        }
        User user = theToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if ((theToken.get().getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "expired";
        }
        user.setActive(true);
        userRepository.save(user);
        return "VALID";
    }

    public void saveVerificationTokenForUser(User user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    public Optional<VerificationToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteUserToken(int userId) {
        tokenRepository.deleteByUserUserId(userId);
    }
}
