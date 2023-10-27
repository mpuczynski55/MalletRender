package com.agh.api;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record UserDTO(
        @NonNull
        Long id,
        @NonNull
        String name,
        @NonNull
        String identifier
) {
}
