package com.agh.api;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record TermBasicDTO(
        @NonNull
        Long id,
        @NonNull
        String term,
        String definition,
        Language language,
        TermBasicDTO translation

) {

}
