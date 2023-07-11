package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.domain.Item;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class FoodService {

    private final WebClient webClient;

    public Optional<Item> findByQuery(String query) {
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
}
