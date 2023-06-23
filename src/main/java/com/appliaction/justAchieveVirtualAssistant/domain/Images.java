package com.appliaction.justAchieveVirtualAssistant.domain;

import lombok.*;

@With
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name", "type"})
public class Images {

    private Long id;

    private String name;

    private String type;

    private byte[] imageData;
}
