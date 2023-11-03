package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;
    private final ImagesService imagesService;

    public UserProfile findByUsername(String username) {
        log.info("Username to find: [%s]".formatted(username));
        return userProfileRepository.findByUserUsername(username);
    }

    public void saveUserProfileData(String username, UserProfile userProfile, MultipartFile file) throws IOException {
        log.info("Saving profile data of user: [%s]".formatted(username));

        if (isPresent(username)) {
            userProfileRepository.delete(findByUsername(username));
        }
        userProfileRepository.saveUserProfileData(userProfile);
        imagesService.uploadProfilePhoto(file, username);
    }

    private boolean isPresent(String username) {
        return userProfileJpaRepository.findByUserUsername(username).isPresent();
    }
}
