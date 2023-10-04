package com.agh.api;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserInformationDTO(
        Long id,
        String username,
        String password,
        LocalDate registrationDate,
        String email
) {
}
