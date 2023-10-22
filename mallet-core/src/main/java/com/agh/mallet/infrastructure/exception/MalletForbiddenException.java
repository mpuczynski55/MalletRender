package com.agh.mallet.infrastructure.exception;

public class MalletForbiddenException extends MalletException{
    public MalletForbiddenException(String message) {
        super(message, ExceptionType.FORBIDDEN);
    }
}
