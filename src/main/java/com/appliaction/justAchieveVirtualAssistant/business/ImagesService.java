package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ImagesUtils;
import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.ImagesRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ImagesService {

    private final ImagesRepository repository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public String uploadImage(MultipartFile file) throws IOException {
        log.info("Uploading file [%s]".formatted(file.getOriginalFilename()));

        Images image = Images.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImagesUtils.compressImage(file.getBytes()))
                .build();

        repository.save(image);
        return "File uploaded successfully: [%s]".formatted(file.getOriginalFilename());
    }

    @Transactional
    public void uploadProfilePhoto(MultipartFile file, String username) throws IOException {
        log.info("Uploading file [%s]".formatted(file.getOriginalFilename()));

        Images image = Images.builder()
                .name("%s_profile_photo".formatted(username) + "_" + file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImagesUtils.compressImage(file.getBytes()))
                .profileId(userProfileRepository.findByUserUsername(username))
                .build();

        repository.save(image);
    }

    @Transactional
    public byte[] downloadImage(String fileName) {
        log.info("Downloading file [%s]".formatted(fileName));

        Optional<Images> bdImageData = repository.getImage(fileName);

        return bdImageData
                .map(image -> ImagesUtils.decompressImage(image.getImageData()))
                .orElseThrow(() -> new NotFoundException("File: [%s] not found".formatted(fileName)));
    }

    @Transactional
    public byte[] downloadImageByProfileId(String username) {
        log.info("Downloading profile photo of user: [%s]".formatted(username));

        Optional<Images> bdImageData = repository.getImageByUserProfile(username);

        return bdImageData
                .map(image -> ImagesUtils.decompressImage(image.getImageData()))
                .orElseThrow(() -> new NotFoundException("Profile photo of user: [%s] not found".formatted(username)));
    }

    public String downloadAndConvertImage(String username) {
        byte[] bytes = downloadImageByProfileId(username);
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Transactional
    public String deleteImage(String fileName) {
        log.info("Deleting file [%s]".formatted(fileName));

        repository.deleteImage(fileName);
        return "File deleted successfully: [%s]".formatted(fileName);
    }

    @Transactional
    public String updateImage(String fileName, MultipartFile file) throws IOException {
        log.info("The file [%s] will be replaced with new file: [%s]".formatted(fileName, file.getOriginalFilename()));

        if (isEmpty(fileName)) {
            throw new NotFoundException("File: [%s] not found".formatted(fileName));
        }

        Images image1 = repository.getImage(fileName).orElseThrow();
        image1.setName(file.getOriginalFilename());
        image1.setType(file.getContentType());
        image1.setImageData(ImagesUtils.compressImage(file.getBytes()));
        repository.save(image1);

        return "File updated successfully: [%s]".formatted(file.getOriginalFilename());
    }

    private boolean isEmpty(String fileName) {
        return repository.getImage(fileName).isEmpty();
    }
}
