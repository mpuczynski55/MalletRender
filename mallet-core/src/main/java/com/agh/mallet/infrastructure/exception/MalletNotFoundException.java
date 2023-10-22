package com.agh.mallet.infrastructure.exception;

public class MalletNotFoundException extends MalletException{
    public MalletNotFoundException(String message) {
        super(message, ExceptionType.NOT_FOUND);
    }

}
