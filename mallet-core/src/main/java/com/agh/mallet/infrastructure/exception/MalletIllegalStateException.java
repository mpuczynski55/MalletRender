package com.agh.mallet.infrastructure.exception;

public class MalletIllegalStateException extends MalletException{
    public MalletIllegalStateException(String message, ExceptionType type) {
        super(message, type);
    }
}
