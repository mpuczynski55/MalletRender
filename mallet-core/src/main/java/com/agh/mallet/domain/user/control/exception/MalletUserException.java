package com.agh.mallet.domain.user.control.exception;

import com.agh.mallet.infrastructure.exception.ExceptionType;
import com.agh.mallet.infrastructure.exception.MalletException;

public class MalletUserException extends MalletException {
    public MalletUserException(String message, ExceptionType type) {
        super(message, type);
    }

}
