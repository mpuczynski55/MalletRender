package com.agh.mallet.domain.common;

import com.agh.api.ErrorResponseDTO;
import com.agh.mallet.infrastructure.exception.MalletException;
import com.agh.mallet.infrastructure.mapper.ErrorResponseDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MalletException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(MalletException malletException) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTOMapper.from(malletException);

        return new ResponseEntity<>(errorResponseDTO, malletException.getHttpStatus());
    }

}
