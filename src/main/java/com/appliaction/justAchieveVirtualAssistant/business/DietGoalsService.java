package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.DietGoals;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.DietGoalsEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.DietGoalsJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
@AllArgsConstructor
public class DietGoalsService {

    private final DietGoalsJpaRepository dietGoalsJpaRepository;
    private final DietGoalsEntityMapper dietGoalsEntityMapper;

    public Optional<DietGoals> findById(Integer id) {
        log.info("Finding diet goal by id: [%s]".formatted(id));
        return dietGoalsJpaRepository.findById(id).stream()
                .map(dietGoalsEntityMapper::mapFromEntity)
                .findFirst();
    }
}
