package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;

    public UserProfile findByUsername(String username) {
        log.info("Username to find: [%s]".formatted(username));
        return userProfileRepository.findByUserUsername(username);
    }

    public void saveUserProfileData(String username, UserProfile userProfile) {
        if (userProfileJpaRepository.findByUserUsername(username).isPresent()) {
            userProfileRepository.delete(findByUsername(username));
        }
        userProfileRepository.saveUserProfileData(userProfile);
    }
}