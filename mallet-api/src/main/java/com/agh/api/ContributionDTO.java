package com.agh.api;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ContributionDTO (
        @NonNull
        long id,
        PermissionType setPermissionType,
        PermissionType groupPermissionType,
        UserDTO contributor
){

}
