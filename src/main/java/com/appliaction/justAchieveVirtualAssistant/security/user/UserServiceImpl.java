package com.appliaction.justAchieveVirtualAssistant.security.user;

import com.appliaction.justAchieveVirtualAssistant.security.registration.RegistrationRequest;
import com.appliaction.justAchieveVirtualAssistant.security.registration.token.VerificationTokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenServiceImpl verificationTokenService;
    @Override
    public List<UserEntity> getAllUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public UserEntity registerUser(RegistrationRequest registration) {
        var user = new UserEntity(
                registration.getUsername(),
                registration.getEmail(),
                passwordEncoder.encode(registration.getPassword()),
                List.of(new RoleEntity("USER")));
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return Optional.ofNullable(userJpaRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Optional<UserEntity> findById(int id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public void updateUser(int id, String username, String email) {
        userJpaRepository.update(username, email, id);
    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        Optional<UserEntity> theUser = userJpaRepository.findById(id);
        theUser.ifPresent(user -> verificationTokenService.deleteUserToken(user.getUserId()));
        userJpaRepository.deleteById(id);
    }
}
