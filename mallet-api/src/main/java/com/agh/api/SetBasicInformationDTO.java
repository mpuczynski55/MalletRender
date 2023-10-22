package com.agh.api;

import lombok.Builder;

@Builder
public record SetBasicInformationDTO(long id,
                                     String name,
                                     UserDTO creator,
                                     int numberOfTerms,
                                     String description) {
}
