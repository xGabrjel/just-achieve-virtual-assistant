package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserProfileEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserProfileRepository {

    private final UserProfileJpaRepository userProfileJpaRepository;
    private final UserProfileEntityMapper userProfileEntityMapper;

    public UserProfile findByUserUsername(String username) {
        return userProfileJpaRepository.findByUserUsername(username)
                .stream()
                .map(userProfileEntityMapper::mapFromEntity)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Could not find UserProfile by this username: [%s]".formatted(username)));
    }
}
