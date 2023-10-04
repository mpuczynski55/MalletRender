package com.agh.api;

import lombok.Builder;

import java.util.List;

@Builder
public record SetInformationDTO(
        String name,
        String description,
        List<LearningSetDTO> learningSets
) {
}
