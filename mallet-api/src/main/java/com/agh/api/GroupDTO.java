package com.agh.api;

import lombok.NonNull;

import java.util.List;

public record GroupDTO(
        @NonNull
        Long id,
        String name,
        List<ContributionDTO> contributions
) {
}
