package com.agh.api;

import lombok.Builder;

import java.util.List;

@Builder
public record SetInformationDTO(
        long id,
        String name,
        String description,
        String creator,
        List<TermDTO> terms,
        String nextChunkUri
) {
}
