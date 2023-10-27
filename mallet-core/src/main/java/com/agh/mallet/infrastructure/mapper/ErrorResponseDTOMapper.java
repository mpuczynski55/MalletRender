package com.agh.mallet.infrastructure.mapper;

import com.agh.api.ErrorResponseDTO;
import com.agh.mallet.infrastructure.exception.MalletException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

public class ErrorResponseDTOMapper {

    private ErrorResponseDTOMapper() {}

    public static ErrorResponseDTO from(MalletException malletException) {
        HttpStatus httpStatus = malletException.getHttpStatus();

        return ErrorResponseDTO.builder()
                .httpCode(httpStatus.value())
                .httpStatus(httpStatus.toString())
                .stackTrace(ExceptionUtils.getStackTrace(malletException))
                .message(malletException.getMessage())
                .build();
    }

}
