package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.FitnessTips;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.FitnessTipsEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.FitnessTipsJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class FitnessTipsGeneratorService {

    private final FitnessTipsJpaRepository fitnessTipsJpaRepository;
    private final FitnessTipsEntityMapper fitnessTipsEntityMapper;
    private final UserProfileRepository userProfileRepository;

    public FitnessTips getRandomTipForDietGoal(String username) {

        List<FitnessTips> tips = fitnessTipsJpaRepository.findByDietGoalDietGoalId(userProfileRepository
                        .findByUserUsername(username)
                        .getDietGoal()
                        .getDietGoalId())
                .stream()
                .map(fitnessTipsEntityMapper::mapFromEntity)
                .toList();

        log.info("Selected tip: [%s]".formatted(tips.get(new Random().nextInt(tips.size()))));
        return tips.get(new Random().nextInt(tips.size()));
    }
}
