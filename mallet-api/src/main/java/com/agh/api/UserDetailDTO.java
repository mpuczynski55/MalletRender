package com.agh.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserDetailDTO(
        long id,
        String identifier,
        String username,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate registrationDate,
        String email
) {
}
