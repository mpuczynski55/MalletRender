package com.agh.api;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record UserDTO(
        @NonNull
        long id,
        String name,
        String identifier,
        String email
) {
}
