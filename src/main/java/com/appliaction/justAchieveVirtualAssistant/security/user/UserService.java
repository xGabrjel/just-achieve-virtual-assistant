package com.appliaction.justAchieveVirtualAssistant.security.user;

import com.appliaction.justAchieveVirtualAssistant.domain.Role;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.security.registration.RegistrationRequest;
import com.appliaction.justAchieveVirtualAssistant.security.registration.token.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private final UserEntityMapper userEntityMapper;
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userJpaRepository.findAll().stream()
                .map(userEntityMapper::mapFromEntity)
                .toList();
    }

    public User registerUser(RegistrationRequest registration) {
        User user = new User(
                registration.getUsername(),
                registration.getEmail(),
                passwordEncoder.encode(registration.getPassword()),
                List.of(new Role("USER")));

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void updateUser(int id, String username, String email) {
        userJpaRepository.update(username, email, id);
    }

    @Transactional
    public void deleteUser(int id) {
        Optional<User> theUser = userRepository.findById(id);
        theUser.ifPresent(user -> verificationTokenService.deleteUserToken(user.getUserId()));
        userJpaRepository.deleteById(id);
    }
}
