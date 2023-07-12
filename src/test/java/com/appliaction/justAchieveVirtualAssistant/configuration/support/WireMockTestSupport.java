package com.appliaction.justAchieveVirtualAssistant.configuration.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public interface WireMockTestSupport {

    default void stubForCalorieNinja1(final WireMockServer wireMockServer, final String query) {
        wireMockServer.stubFor(get(urlPathEqualTo("/nutrition?query=" + query))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("wiremock/nutritionByQuery.json")
                ));
    }

    default void stubForCalorieNinja2(final WireMockServer wireMockServer, final String query) {
        wireMockServer.stubFor(get(("/food/details?query=" + query))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("wiremock/nutritionByQuery.json")));
    }
}
