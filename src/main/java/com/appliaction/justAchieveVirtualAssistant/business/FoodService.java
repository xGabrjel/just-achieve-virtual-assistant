package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.Item;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.FoodEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.FoodRepository;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserRepository;
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
    private final UserRepository userRepository;

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
                .userId(userRepository.findByUsername(username).orElseThrow())
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

    public List<Food> findAllByUsername(String username) {
        List<FoodEntity> allProducts = foodRepository.findAllProducts(username);
        List<Food> result = new ArrayList<>();

        for (FoodEntity product : allProducts) {
            Food food = foodEntityMapper.mapFromEntity(product);
            result.add(food);
        }

        return result;
    }

    public void deleteAll() {
        foodRepository.deleteAllProducts();
    }
}
