package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.BodyMeasurementsDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.BodyMeasurementsMapper;
import com.appliaction.justAchieveVirtualAssistant.business.BodyMeasurementsService;
import com.appliaction.justAchieveVirtualAssistant.business.UserProfileService;
import com.appliaction.justAchieveVirtualAssistant.domain.BodyMeasurements;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.BodyMeasurementsEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/measurements")
@AllArgsConstructor
public class BodyMeasurementsController {

    private BodyMeasurementsService bodyMeasurementsService;
    private BodyMeasurementsMapper bodyMeasurementsMapper;
    private BodyMeasurementsEntityMapper bodyMeasurementsEntityMapper;
    private UserProfileService userProfileService;

    @GetMapping
    public String page() {
        return "measurements";
    }

    @GetMapping("/get")
    public String getMeasurements(
            Model model,
            Principal principal,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") String date
    ) {
        String username = principal.getName();
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        List<BodyMeasurementsDTO> list = bodyMeasurementsService
                .findByDateAndProfileId(offsetDateTime, userProfileService.findByUsername(username)).stream()
                .map(bodyMeasurementsEntityMapper::mapFromEntity)
                .map(bodyMeasurementsMapper::map)
                .toList();

        model.addAttribute("measurements", list);
        return "measurements";
    }

    @PostMapping("/add")
    public String postMeasurements(
            Model model,
            Principal principal,
            @RequestParam(value = "date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") String date,
            @RequestParam(value = "currentWeight", required = true) BigDecimal currentWeight,
            @RequestParam(value = "calf", required = true) BigDecimal calf,
            @RequestParam(value = "thigh", required = true) BigDecimal thigh,
            @RequestParam(value = "waist", required = true) BigDecimal waist,
            @RequestParam(value = "chest", required = true) BigDecimal chest,
            @RequestParam(value = "arm", required = true) BigDecimal arm,
            @RequestParam(value = "measurementNote", required = false) String measurementNote
    ) {
        String username = principal.getName();
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        UserProfile userProfile = userProfileService.findByUsername(username);

        BodyMeasurements toSave = BodyMeasurements.builder()
                .profileId(userProfile)
                .date(offsetDateTime)
                .currentWeight(currentWeight)
                .calf(calf)
                .thigh(thigh)
                .waist(waist)
                .chest(chest)
                .arm(arm)
                .measurementNote(measurementNote)
                .build();

        bodyMeasurementsService.saveBodyMeasurements(toSave);
        return "measurements";
    }
}
