package com.agh.mallet.infrastructure.exception;

public class MalletIllegalArgumentException extends MalletException{

    public MalletIllegalArgumentException(String message) {
        super(message, ExceptionType.INVALID_ARGUMENT);
    }

}
