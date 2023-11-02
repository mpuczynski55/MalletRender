package com.agh.api;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record TermDetailDTO(
        @NonNull
        Long id,
        @NonNull
        String term,
        String definition,
        Language language,
        TermDetailDTO translation

) {

}
