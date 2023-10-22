package com.agh.api;

import lombok.Builder;

import java.util.List;

@Builder
public record UserKnownTermsUpdateDTO(

        List<Long> termIds
) {
}
