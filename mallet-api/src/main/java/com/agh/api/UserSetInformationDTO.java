package com.agh.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record UserSetInformationDTO (
        long id,
        String name,
        String description,
        String creator,
        PermissionType permissionType,
        String nextChunkUri
) {
    public enum PermissionType {

        READ_WRITE,
        READ

    }

}