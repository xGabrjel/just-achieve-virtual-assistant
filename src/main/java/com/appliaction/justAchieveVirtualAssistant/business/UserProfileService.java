package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserProfileDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.UserProfileMapper;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Slf4j
@Service
@AllArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;
    private final ImagesService imagesService;
    private final UserService userService;
    private final DietGoalsService dietGoalsService;
    private final UserProfileMapper userProfileMapper;

    public UserProfile findByUsername(String username) {
        log.info("Username to find: [%s]".formatted(username));
        return userProfileRepository.findByUserUsername(username);
    }

    public UserProfileDTO findDTOByUsername(String username) {
        log.info("Username to find: [%s]".formatted(username));
        return userProfileMapper.map(userProfileRepository.findByUserUsername(username));
    }

    @Transactional
    public void saveUserProfileData(String username, int dietGoalId, UserProfileDTO userProfileDTO, MultipartFile file) throws IOException {
        log.info("Saving profile data of user: [%s]".formatted(username));
        UserProfile userProfile = UserProfile.builder()
                .user(userService.findByUsername(username).orElseThrow())
                .name(userProfileDTO.getName())
                .surname(userProfileDTO.getSurname())
                .phone(userProfileDTO.getPhone())
                .age(userProfileDTO.getAge())
                .sex(userProfileDTO.getSex())
                .weight(userProfileDTO.getWeight())
                .height(userProfileDTO.getHeight())
                .dietGoal(dietGoalsService.findById(dietGoalId).orElseThrow())
                .build();

        if (isPresent(username)) {
            userProfileRepository.delete(findByUsername(username));
        }
        userProfileRepository.saveUserProfileData(userProfile);
        imagesService.uploadProfilePhoto(file, username);
    }

    private boolean isPresent(String username) {
        return userProfileJpaRepository.findByUserUsername(username).isPresent();
    }

    public boolean isProfileCompleted(Principal principal) {
        try {
            if (principal != null) {
                String username = principal.getName();
                UserProfile userProfile = findByUsername(username);
                return userProfile != null;
            }
        } catch (Exception e) {
            log.error("Error occurred: [%s]".formatted(e));
        }
        return false;
    }
}
