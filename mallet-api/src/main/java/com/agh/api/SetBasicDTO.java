package com.agh.api;

import lombok.Builder;

import java.util.List;

@Builder
public record SetBasicDTO(
        List<SetBasicInformationDTO> sets,
        String nextChunkUri
) {}