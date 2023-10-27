package com.agh.api;

import lombok.Builder;
import lombok.NonNull;

import java.util.Collection;

@Builder
public record TermDTO(
        @NonNull
        Long id,
        @NonNull
        String term,
        @NonNull
        String definition,
        String language,
        String grammaticalCategory,
        String topic,
        @NonNull
        Collection<TermDTO> translations


) {

}
