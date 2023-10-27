package com.agh.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDate;

@Builder
public record UserDetailDTO(
        @NonNull
        Long id,
        @NonNull
        String identifier,
        @NonNull
        String username,
        @NonNull
        LocalDate registrationDate,
        @NonNull
        String email
) {
}
