package com.agh.api;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record SetInformationDTO(
        @NonNull
        Long id,
        @NonNull
        String name,
        @NonNull
        UserDTO creator,
        @NonNull
        int numberOfTerms,
        String description) {
}
