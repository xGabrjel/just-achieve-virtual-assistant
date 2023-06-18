package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserProfileEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileEntityMapper userProfileEntityMapper;

    public UserProfile getUserProfile(String phoneNumber) {
        return userProfileRepository.findByPhone(phoneNumber)
                .stream()
                .map(userProfileEntityMapper::mapFromEntity)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Could not find UserProfile by this number: [%s]".formatted(phoneNumber)));
    }
}
