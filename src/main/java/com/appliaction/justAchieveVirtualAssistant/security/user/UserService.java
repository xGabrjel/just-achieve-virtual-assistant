package com.appliaction.justAchieveVirtualAssistant.security.user;

import com.appliaction.justAchieveVirtualAssistant.security.registration.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserEntity> getAllUsers();

    UserEntity registerUser(RegistrationRequest registrationRequest);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(int id);
    Optional<UserEntity> findByUsername(String username);

    void updateUser(int id, String username, String email);

    void deleteUser(int id);
}
