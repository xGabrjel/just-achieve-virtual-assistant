package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.DietGoalsEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.UserEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.DietGoalsJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;
    private final DietGoalsJpaRepository dietGoalsJpaRepository;
    private final DietGoalsEntityMapper dietGoalsEntityMapper;
    private final UserProfileJpaRepository userProfileJpaRepository;

    public UserProfile findByUsername(String username) {
        log.info("Username to find: [%s]".formatted(username));
        return userProfileRepository.findByUserUsername(username);
    }

    public void saveUserProfileData(
            String username,
            String name,
            String surname,
            String phone,
            Integer age,
            String sex,
            BigDecimal weight,
            BigDecimal height,
            Integer dietGoalsId
    ) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(userJpaRepository.findByUsername(username).stream().map(userEntityMapper::mapFromEntity).findFirst().get());
        userProfile.setName(name);
        userProfile.setSurname(surname);
        userProfile.setPhone(phone);
        userProfile.setAge(age);
        userProfile.setSex(sex);
        userProfile.setWeight(weight);
        userProfile.setHeight(height);

        switch (dietGoalsId) {
            case 1 -> userProfile.setDietGoal(dietGoalsJpaRepository.findById(1).stream()
                    .map(dietGoalsEntityMapper::mapFromEntity)
                    .findFirst()
                    .get());
            case 2 -> userProfile.setDietGoal(dietGoalsJpaRepository.findById(2).stream()
                    .map(dietGoalsEntityMapper::mapFromEntity)
                    .findFirst()
                    .get());
            case 3 -> userProfile.setDietGoal(dietGoalsJpaRepository.findById(3).stream()
                    .map(dietGoalsEntityMapper::mapFromEntity)
                    .findFirst()
                    .get());
        }

        if (userProfileJpaRepository.findByUserUsername(username).isPresent()) {
            userProfileRepository.delete(findByUsername(username));
        }
        userProfileRepository.saveUserProfileData(userProfile);
    }
}
