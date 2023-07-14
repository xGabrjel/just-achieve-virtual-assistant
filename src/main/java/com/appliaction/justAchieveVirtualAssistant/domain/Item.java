package com.appliaction.justAchieveVirtualAssistant.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "items"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {

    @JsonProperty("items")
    private List<Food> foods;
}
