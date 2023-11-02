package com.agh.mallet.infrastructure.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collection;

@Component
@RequestScope
public class NextChunkRebuilder {

    private static final String START_POSITION_PARAM_TEMPLATE = "startPosition=%d";
    private static final String LIMIT_PARAM_TEMPLATE = "limit=%d";
    private static final String AMPERSAND_CHARACTER = "&";
    private static final String QUESTION_MARK = "?";

    private final HttpServletRequest servletRequest;

    public NextChunkRebuilder(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public <T> String rebuild(Collection<T> resultResource,
                              Integer startPosition,
                              Integer limit) {
        if (resultResource.isEmpty() || resultResource.size() < limit) {
            return null;
        }

        return rebuild(startPosition, limit);
    }

    private String rebuild(Integer startPosition,
                          Integer limit) {
        String currentStartPositionTemplate = String.format(START_POSITION_PARAM_TEMPLATE, startPosition);
        String nextChunkStartPositionTemplate = String.format(START_POSITION_PARAM_TEMPLATE, startPosition + limit);

        String currentUrl = servletRequest.getRequestURI();

        String rebuildNextChunkUri = currentUrl
                .replace(currentStartPositionTemplate, nextChunkStartPositionTemplate);

        if (!rebuildNextChunkUri.contains(nextChunkStartPositionTemplate)) {
            String limitTemplate = String.format(LIMIT_PARAM_TEMPLATE, limit);

            rebuildNextChunkUri = currentUrl + QUESTION_MARK + limitTemplate + AMPERSAND_CHARACTER + nextChunkStartPositionTemplate;
        }

        return rebuildNextChunkUri;
    }

}
