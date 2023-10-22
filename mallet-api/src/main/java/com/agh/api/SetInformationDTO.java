package com.agh.api;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public record SetInformationDTO (
        @NonNull
        long id,
        String name,
        String description,
        @NonNull
        List<TermDTO> terms,
        @NonNull
        String nextChunkUri
) {
}
