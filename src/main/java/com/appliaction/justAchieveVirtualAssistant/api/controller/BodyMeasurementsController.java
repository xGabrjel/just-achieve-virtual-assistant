package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.BodyMeasurementsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.BodyMeasurementsMapper;
import com.appliaction.justAchieveVirtualAssistant.business.BodyMeasurementsService;
import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/measurements")
@AllArgsConstructor
public class BodyMeasurementsController {

    private BodyMeasurementsService bodyMeasurementsService;
    private BodyMeasurementsMapper bodyMeasurementsMapper;
    private UserProfileService userProfileService;

    @GetMapping
    public String page() {
        return "measurements";
    }

    @GetMapping("/get")
    public String getMeasurements(
            Model model,
            Principal principal,
            @RequestParam("date") String date
    ) {
        String username = principal.getName();
        LocalDate parseResult = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

        BodyMeasurements list = bodyMeasurementsService.findByDateAndProfileId(parseResult, userProfileService.findByUsername(username));
        BodyMeasurementsDTO bodyMeasurementsDTO = bodyMeasurementsMapper.map(list);

        model.addAttribute("measurements", bodyMeasurementsDTO);
        return "measurements";
    }

    @PostMapping("/add")
    public String postMeasurements(
            Model model,
            Principal principal,
            @Valid @ModelAttribute("bodyMeasurementsDTO") BodyMeasurementsDTO bodyMeasurementsDTO
    ) {
        String username = principal.getName();
        UserProfile userProfile = userProfileService.findByUsername(username);

        BodyMeasurements toSave = BodyMeasurements.builder()
                .profileId(userProfile)
                .date(bodyMeasurementsDTO.getDate())
                .currentWeight(bodyMeasurementsDTO.getCurrentWeight())
                .calf(bodyMeasurementsDTO.getCalf())
                .thigh(bodyMeasurementsDTO.getThigh())
                .waist(bodyMeasurementsDTO.getWaist())
                .chest(bodyMeasurementsDTO.getChest())
                .arm(bodyMeasurementsDTO.getArm())
                .measurementNote(bodyMeasurementsDTO.getMeasurementNote())
                .build();

        bodyMeasurementsService.saveBodyMeasurements(toSave);
        return "redirect:/measurements?success";
    }
}
