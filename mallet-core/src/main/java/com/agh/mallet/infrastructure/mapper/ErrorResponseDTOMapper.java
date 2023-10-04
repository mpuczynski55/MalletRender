package com.agh.mallet.infrastructure.mapper;

import com.agh.mallet.api.ErrorResponseDTO;
import com.agh.mallet.infrastructure.exception.MalletException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

public class ErrorResponseDTOMapper {

    private ErrorResponseDTOMapper() {
    }

    public static ErrorResponseDTO from(MalletException malletException) {
        HttpStatus httpStatus = malletException.getHttpStatus();

        return ErrorResponseDTO.builder()
                .code(httpStatus.value())
                .status(httpStatus)
                .stackTrace(ExceptionUtils.getStackTrace(malletException))
                .message(malletException.getMessage())
                .build();
    }

}
