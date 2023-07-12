package com.appliaction.justAchieveVirtualAssistant.api.controller.rest;

import com.appliaction.justAchieveVirtualAssistant.business.ImagesService;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;

@WebMvcTest(ImagesRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ImagesRestControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private final ImagesService service;

    @Test
    void downloadImageWorksCorrectly() throws Exception {
        //given
        String fileName = "example.png";
        byte[] imageData = new byte[]{1, 2, 3};
        when(service.downloadImage(fileName)).thenReturn(imageData);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/image-manager/download/{fileName}", fileName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.IMAGE_PNG))
                .andExpect(MockMvcResultMatchers.content().bytes(imageData));

        verify(service, times(1)).downloadImage(fileName);
    }

    @Test
    void uploadImageWorksCorrectly() throws Exception {
        //given
        String uploadImage = "example.png";
        MockMultipartFile file = new MockMultipartFile("image", "example.png", MediaType.IMAGE_PNG_VALUE, new byte[]{1, 2, 3});
        when(service.uploadImage(file)).thenReturn(uploadImage);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/image-manager/upload")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(uploadImage));

        verify(service, times(1)).uploadImage(file);
    }

    @Test
    void deleteImageWorksCorrectly() throws Exception {
        //given
        String fileName = "example.png";
        String expectedMessage = "Image [%s] deleted successfully.".formatted(fileName);
        when(service.downloadImage(fileName)).thenReturn(new byte[]{1, 2, 3});

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/image-manager/delete/{fileName}", fileName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedMessage));

        verify(service, times(1)).downloadImage(fileName);
        verify(service, times(1)).deleteImage(fileName);
    }

    @Test
    void deleteImageNotFoundWorksCorrectly() throws Exception {
        //given
        String fileName = "example.png";
        NotFoundException exception = new NotFoundException("Image not found");
        when(service.downloadImage(fileName)).thenThrow(exception);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/image-manager/delete/{fileName}", fileName))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(exception.getMessage()));

        verify(service, times(1)).downloadImage(fileName);
        verify(service, never()).deleteImage(anyString());
    }

    @Test
    void updateImageWorksCorrectly() throws Exception {
        //given
        String fileName = "example.png";
        String updateImage = "updated.png";
        MockMultipartFile file = new MockMultipartFile("image", "updated.png", MediaType.IMAGE_PNG_VALUE, new byte[]{4, 5, 6});
        when(service.downloadImage(fileName)).thenReturn(new byte[]{1, 2, 3});
        when(service.updateImage(fileName, file)).thenReturn(updateImage);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/image-manager/update/{fileName}", fileName)
                        .file(file)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(updateImage));

        verify(service, times(1)).downloadImage(fileName);
        verify(service, times(1)).updateImage(fileName, file);
    }

    @Test
    void updateImageNotFoundWorksCorrectly() throws Exception {
        //given
        String fileName = "example.png";
        MockMultipartFile file = new MockMultipartFile("image", "updated.png", MediaType.IMAGE_PNG_VALUE, new byte[]{4, 5, 6});
        NotFoundException exception = new NotFoundException("Image not found");
        when(service.downloadImage(fileName)).thenThrow(exception);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/image-manager/update/{fileName}", fileName)
                        .file(file)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(exception.getMessage()));

        verify(service, times(1)).downloadImage(fileName);
        verify(service, never()).updateImage(anyString(), any(MultipartFile.class));
    }
}