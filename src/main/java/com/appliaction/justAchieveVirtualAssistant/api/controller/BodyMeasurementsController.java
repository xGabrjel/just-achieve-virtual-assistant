package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.BodyMeasurementsDTO;
import com.appliaction.justAchieveVirtualAssistant.business.BodyMeasurementsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.appliaction.justAchieveVirtualAssistant.api.controller.BodyMeasurementsController.ROOT;

@Controller
@AllArgsConstructor
@RequestMapping(ROOT)
public class BodyMeasurementsController {

    static final String ROOT = "/measurements";
    static final String GET_MEASUREMENTS = "/available-measurement";
    static final String POST_MEASUREMENTS = "/new-measurement";

    private BodyMeasurementsService bodyMeasurementsService;

    @GetMapping
    public String page() {
        return "measurements";
    }

    @GetMapping(GET_MEASUREMENTS)
    public String getMeasurements(
            Model model,
            Principal principal,
            @RequestParam("date") String date
    ) {
        model.addAttribute("measurements", bodyMeasurementsService.findFinalBodyMeasurementsDTO(date, principal.getName()));
        return "measurements";
    }

    @PostMapping(POST_MEASUREMENTS)
    public String postMeasurements(
            Model model,
            Principal principal,
            @Valid @ModelAttribute("bodyMeasurementsDTO") BodyMeasurementsDTO bodyMeasurementsDTO
    ) {
        bodyMeasurementsService.saveBodyMeasurements(bodyMeasurementsDTO, principal.getName());
        return "redirect:/measurements?success";
    }
}
