package com.agh.api;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public record TermBasicListDTO(

        @NonNull
        List<TermBasicDTO> terms,
        String nextChunkUri

) {
}
