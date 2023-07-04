package com.appliaction.justAchieveVirtualAssistant.security.user;

import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {

    private UserJpaRepository userJpaRepository;
    private UserEntityMapper userEntityMapper;

    public User save(User user) {
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        UserEntity save = userJpaRepository.save(userEntity);
        return userEntityMapper.mapFromEntity(save);
    }

    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).stream()
                .map(userEntityMapper::mapFromEntity)
                .findFirst();
    }

    public Optional<User> findById(int id) {
        return userJpaRepository.findById(id).stream()
                .map(userEntityMapper::mapFromEntity)
                .findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).stream()
                .map(userEntityMapper::mapFromEntity)
                .findFirst();
    }
}
