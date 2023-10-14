package com.agh.mallet.domain.vocabulary.entity;

import com.agh.mallet.infrastructure.exception.ExceptionType;
import com.agh.mallet.infrastructure.exception.MalletException;

import java.util.Arrays;

public enum Language {

    EN,
    PL;

    public static Language from(String language) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(language))
                .findAny()
                .orElseThrow(() -> new MalletException("Provided language is not supported", ExceptionType.INVALID_ARGUMENT));
    }

}
