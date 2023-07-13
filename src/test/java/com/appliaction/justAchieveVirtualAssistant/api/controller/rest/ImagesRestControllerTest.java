package com.appliaction.justAchieveVirtualAssistant.api.controller.rest;

import com.appliaction.justAchieveVirtualAssistant.configuration.RestAssureConfigurationTestBase;
import com.appliaction.justAchieveVirtualAssistant.configuration.support.ImageSupport;
import org.junit.jupiter.api.Test;

class ImagesRestControllerTest extends RestAssureConfigurationTestBase implements ImageSupport {

    @Test
    void downloadImageWorksCorrectly() {
        //given
        String fileName = "test.png";

        //when, then
        getImage(fileName);
    }

    @Test
    void uploadImageWorksCorrectly() {
        //given
        String fileName = "test2.png";
        byte[] fileContent = new byte[] { 0x01, 0x23, 0x45, 0x67};

        //when, then
        uploadImage(fileName, fileContent);
    }

    @Test
    void deleteImageWorksCorrectly() {
        //given
        String fileName = "test.png";

        //when, then
        deleteImage(fileName);
    }
    @Test
    void deleteImageThrowingExceptionWorksCorrectly() {
        //given
        String fileName = "notForDelete";

        //when, then
        deleteImageThrowsException(fileName);
    }

    @Test
    void updateImageWorksCorrectly()  {
        // given
        String fileName = "test.png";
        byte[] fileContent = new byte[] { 0x01, 0x23, 0x45, 0x67};

        // when, then
        updateImage(fileName, fileContent);
    }
    @Test
    void updateImageThrowingExceptionWorksCorrectly() {
        // given
        String fileName = "notForUpdating";
        byte[] fileContent = new byte[] { 0x01, 0x23, 0x45, 0x67};

        // when, then
        updateImageThrowsException(fileName, fileContent);
    }
}