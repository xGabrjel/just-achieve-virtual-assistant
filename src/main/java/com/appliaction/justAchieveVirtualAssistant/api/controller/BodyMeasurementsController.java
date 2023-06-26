package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.BodyMeasurementsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.BodyMeasurementsMapper;
import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.BodyMeasurementsRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;

@Controller
@RequestMapping("/measurements")
@AllArgsConstructor
public class BodyMeasurementsController {

    private BodyMeasurementsRepository bodyMeasurementsRepository;
    private BodyMeasurementsMapper bodyMeasurementsMapper;
    private UserProfileRepository userProfileRepository;

    @GetMapping
    public String getMeasurements(Model model, Principal principal, @RequestParam("date") OffsetDateTime date) {
        String username = principal.getName();
        List<BodyMeasurementsDTO> list = bodyMeasurementsRepository
                .findByDateAndProfileId(date, userProfileRepository.findByUserUsername(username)).stream()
                .map(bodyMeasurementsMapper::map)
                .toList();


        model.addAttribute("measurements", list);
        return "measurements";
    }

    @PostMapping
    public String postMeasurements(
            Model model,
            Principal principal,
            @RequestParam("date") OffsetDateTime date,
            @RequestParam("currentWeight") BigDecimal currentWeight,
            @RequestParam("calf") BigDecimal calf,
            @RequestParam("thigh") BigDecimal thigh,
            @RequestParam("waist") BigDecimal waist,
            @RequestParam("chest") BigDecimal chest,
            @RequestParam("arm") BigDecimal arm,
            @RequestParam("measurementNote") String measurementNote) {
        String username = principal.getName();
        UserProfile userProfile = userProfileRepository.findByUserUsername(username);

        BodyMeasurements toSave = BodyMeasurements.builder()
                .profileId(userProfile)
                .date(date)
                .currentWeight(currentWeight)
                .calf(calf)
                .thigh(thigh)
                .waist(waist)
                .chest(chest)
                .arm(arm)
                .measurementNote(measurementNote)
                .build();

        bodyMeasurementsRepository.saveBodyMeasurements(toSave);
        return "measurements";
    }
}
