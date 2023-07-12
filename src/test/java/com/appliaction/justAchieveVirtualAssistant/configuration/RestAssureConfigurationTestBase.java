package com.appliaction.justAchieveVirtualAssistant.configuration;

import com.appliaction.justAchieveVirtualAssistant.configuration.support.ControllerTestSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;


public class RestAssureConfigurationTestBase extends AbstractIT implements ControllerTestSupport {

    @LocalServerPort
    private int serverPort;

    @Value("${server.servlet.context-path}")
    private String basePath;

    @Autowired
    protected ObjectMapper objectMapper;

    protected static WireMockServer wireMockServer;

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(
                wireMockConfig()
                        .port(9999)
                        .extensions(new ResponseTemplateTransformer(false))
        );
        wireMockServer.start();
    }
    @AfterEach
    void afterEach() {
        wireMockServer.resetAll();
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    public RequestSpecification requestSpecification() {
        return RestAssured
                .given()
                .config(getConfig())
                .basePath(basePath)
                .port(serverPort)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }
    private RestAssuredConfig getConfig() {
        return RestAssuredConfig
                .config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}
