package com.agh.mallet.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MalletException extends RuntimeException {

    private HttpStatus httpStatus;

    public MalletException(String message, ExceptionType type) {
        super(message);

        switch (type) {
            case NOT_FOUND -> onNotFound();
            case LOCKED -> onLocked();
            case INVALID_ARGUMENT -> onInvalid();
            case ALREADY_EXISTS -> onAlreadyExists();
            case BAD_GATEWAY -> onBadGateway();
            default -> onUnknown();
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void onNotFound() {
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    private void onLocked() {
        this.httpStatus = HttpStatus.FORBIDDEN;
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    private void onInvalid() {
        this.httpStatus = HttpStatus.PRECONDITION_FAILED;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    private void onAlreadyExists() {
        this.httpStatus = HttpStatus.FORBIDDEN;
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    private void onBadGateway() {
        this.httpStatus = HttpStatus.BAD_GATEWAY;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private void onUnknown() {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
