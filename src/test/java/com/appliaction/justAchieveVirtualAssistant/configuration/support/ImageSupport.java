package com.appliaction.justAchieveVirtualAssistant.configuration.support;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public interface ImageSupport {

    RequestSpecification requestSpecification();

    default void getImage(final String fileName) {
        requestSpecification()
                .when()
                .get("/image-manager/downloads/" + fileName)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.IMAGE_PNG_VALUE)
                .assertThat()
                .body(notNullValue());
    }

    default void uploadImage(final String fileName, final byte[] fileContent) {
        MockMultipartFile file = new MockMultipartFile("image", fileName, MediaType.IMAGE_PNG_VALUE, fileContent);

        requestSpecification()
                .given()
                .multiPart("image", fileName, fileContent)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .expect()
                .when()
                .post("/image-manager/uploads")
                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body(notNullValue());
    }

    default void deleteImage(final String fileName) {
        requestSpecification()
                .given()
                .expect()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue())
                .body(equalTo("Image [%s] deleted successfully.".formatted(fileName)))
                .when()
                .delete("/image-manager/deletion/{fileName}", fileName);
    }

    default void deleteImageThrowsException(final String fileName) {
        requestSpecification()
                .given()
                .expect()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(notNullValue())
                .body(equalTo("File: [%s] not found".formatted(fileName)))
                .when()
                .delete("/image-manager/deletion/{fileName}", fileName);
    }

    default void updateImage(final String fileName, final byte[] fileContent) {
        requestSpecification()
                .given()
                .multiPart("image", fileName, fileContent)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .when()
                .put("/image-manager/updates/{fileName}", fileName)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue());
    }
    default void updateImageThrowsException(final String fileName, final byte[] fileContent) {
        requestSpecification()
                .given()
                .multiPart("image", fileName, fileContent)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .expect()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(notNullValue())
                .when()
                .put("/image-manager/updates/{fileName}", fileName);
    }
}
