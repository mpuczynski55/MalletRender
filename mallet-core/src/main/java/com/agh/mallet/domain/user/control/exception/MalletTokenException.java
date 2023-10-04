package com.agh.mallet.domain.user.control.exception;

import com.agh.mallet.infrastructure.exception.ExceptionType;
import com.agh.mallet.infrastructure.exception.MalletException;

public class MalletTokenException extends MalletException {
    public MalletTokenException(String message, ExceptionType type) {
        super(message, type);
    }
}
