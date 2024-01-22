package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.FoodMapper;
import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.Item;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.FoodEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.FoodRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class FoodService {

    private final WebClient webClient;
    private final FoodRepository foodRepository;
    private final FoodEntityMapper foodEntityMapper;
    private final UserProfileRepository userProfileRepository;
    private final FoodMapper foodMapper;

    public FoodDTO findFinalProduct(String query) {
        Item result = findByQuery(query)
                .orElseThrow(() -> new NotFoundException("Food: [%s] not found".formatted(query)));

        return FoodDTO.builder()
                .name(result.getFoods().get(0).getName().toUpperCase())
                .calories(result.getFoods().get(0).getCalories())
                .servingSizeG(result.getFoods().get(0).getServingSizeG())
                .fatTotalG(result.getFoods().get(0).getFatTotalG())
                .fatSaturatedG(result.getFoods().get(0).getFatSaturatedG())
                .proteinG(result.getFoods().get(0).getProteinG())
                .sodiumMg(result.getFoods().get(0).getSodiumMg())
                .potassiumMg(result.getFoods().get(0).getPotassiumMg())
                .cholesterolMg(result.getFoods().get(0).getCholesterolMg())
                .carbohydratesTotalG(result.getFoods().get(0).getCarbohydratesTotalG())
                .fiberG(result.getFoods().get(0).getFiberG())
                .sugarG(result.getFoods().get(0).getSugarG())
                .build();
    }

    public Optional<Item> findByQuery(String query) {
        log.info("Query input: [%s]".formatted(query));

        try {
            var result = webClient.get()
                    .uri("/nutrition?query=" + query)
                    .retrieve()
                    .bodyToMono(Item.class)
                    .block();

            return Optional.ofNullable(result);
        } catch (Exception e) {
            log.error("Exception while finding food by Query: [%s]".formatted(e));
            return Optional.empty();
        }
    }

    public void saveProduct(FoodDTO foodDTO, String username) {
        Food food = Food.builder()
                .profileId(userProfileRepository.findByUserUsername(username))
                .name(foodDTO.getName())
                .calories(foodDTO.getCalories())
                .servingSizeG(foodDTO.getServingSizeG())
                .fatTotalG(foodDTO.getFatTotalG())
                .fatSaturatedG(foodDTO.getFatSaturatedG())
                .proteinG(foodDTO.getProteinG())
                .sodiumMg(foodDTO.getSodiumMg())
                .potassiumMg(foodDTO.getPotassiumMg())
                .cholesterolMg(foodDTO.getCholesterolMg())
                .carbohydratesTotalG(foodDTO.getCarbohydratesTotalG())
                .fiberG(foodDTO.getFiberG())
                .sugarG(foodDTO.getSugarG())
                .build();
        foodRepository.saveIntoDatabase(food);
    }

    public List<FoodDTO> findAllDTOByUsername(String username) {
        List<FoodEntity> allProducts = foodRepository.findAllProducts(username);
        List<FoodDTO> result = new ArrayList<>();

        for (FoodEntity product : allProducts) {
            result.add(foodMapper.map(foodEntityMapper.mapFromEntity(product)));
        }

        return result;
    }

    public void deleteAll() {
        foodRepository.deleteAllProducts();
    }
}
