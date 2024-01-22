package com.appliaction.justAchieveVirtualAssistant.configuration.support;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface CalorieNinjaTestSupport {

    RequestSpecification requestSpecification();

    default FoodDTO getFoodDetails(final String query) {
        return requestSpecification()
                .get("/food-manager/nutrients/" + query)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(FoodDTO.class);
    }
}
