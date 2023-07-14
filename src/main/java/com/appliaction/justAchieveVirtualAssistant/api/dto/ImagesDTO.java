package com.appliaction.justAchieveVirtualAssistant.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImagesDTO {

    private Long id;
    private String name;
    private String type;
    private byte[] imageData;
}
