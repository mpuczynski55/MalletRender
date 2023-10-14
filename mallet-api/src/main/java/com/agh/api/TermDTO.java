package com.agh.api;

import lombok.Builder;

import java.util.Collection;

@Builder
public record TermDTO(
        long id,
        String term,
        String definition,
        String language,
        String grammaticalCategory,
        String topic,
        Collection<TermDTO> translations


) {

}
