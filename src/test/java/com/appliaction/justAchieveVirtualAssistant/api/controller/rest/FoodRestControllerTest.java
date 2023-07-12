package com.appliaction.justAchieveVirtualAssistant.api.controller.rest;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.configuration.RestAssureConfigurationTestBase;
import com.appliaction.justAchieveVirtualAssistant.configuration.support.CalorieNinjaTestSupport;
import com.appliaction.justAchieveVirtualAssistant.configuration.support.WireMockTestSupport;
import com.appliaction.justAchieveVirtualAssistant.domain.Item;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class FoodRestControllerTest extends RestAssureConfigurationTestBase implements CalorieNinjaTestSupport, WireMockTestSupport {

    @Test
    void thatGetFoodDetailsWorksCorrectly() {
        //given
        String query1 = "apple";

        stubForCalorieNinja1(wireMockServer, query1);

        //when
        FoodDTO foodDetails1 = getFoodDetails(query1);

        //then
        Assertions.assertThat(foodDetails1).isNotNull();
        Assertions.assertThat(foodDetails1).hasFieldOrPropertyWithValue("servingSizeG", 100);
        Assertions.assertThat(foodDetails1).hasFieldOrPropertyWithValue("name", "APPLE");
    }

    @Test
    public void thatAnotherGetFoodDetailsWorksCorrectly() {
        //given
        String query = "apple";

        stubForCalorieNinja2(wireMockServer, query);

        //when
        Response response = given()
                .port(9999)
                .when()
                .get("/food/details?query=" + query)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .response();

        //then
        Item result = response.getBody().as(Item.class);
        assertThat(result).isNotNull();
        assertThat(result.getFoods().get(0).getCalories()).isEqualTo(BigDecimal.valueOf(53));
        assertThat(result.getFoods().get(0).getName()).isEqualToIgnoringCase("apple");
        assertThat(result.getFoods().get(0).getServingSizeG()).isEqualTo(100);
    }
}