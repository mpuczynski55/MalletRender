package com.agh.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class ErrorResponseDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    Date timestamp = new Date();
    int httpCode;
    String httpStatus;
    String message;
    String stackTrace;

}
