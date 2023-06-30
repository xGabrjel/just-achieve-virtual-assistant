package com.appliaction.justAchieveVirtualAssistant.security.registration.password;

import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenJpaRepository passwordResetTokenRepository;
    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String validatePasswordResetToken(String theToken) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(theToken);
        if (passwordResetToken.isEmpty()){
            return "invalid";
        }
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.get().getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "expired";
        }
        return "valid";
    }

    @Override
    public Optional<UserEntity> findUserByPasswordResetToken(String theToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(theToken).get().getUser());
    }

    @Override
    public void resetPassword(UserEntity theUser, String newPassword) {
        theUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(theUser);
    }
    @Override
    public void createPasswordResetTokenForUser(UserEntity user, String passwordResetToken) {
        PasswordResetToken resetToken = new PasswordResetToken(passwordResetToken, user);
        passwordResetTokenRepository.save(resetToken);
    }

}
