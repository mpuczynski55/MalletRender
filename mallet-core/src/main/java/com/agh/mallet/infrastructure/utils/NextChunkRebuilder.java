package com.agh.mallet.infrastructure.utils;

import jakarta.servlet.http.HttpServletRequest;

public class NextChunkRebuilder {

    private static final String START_POSITION_PARAM_TEMPLATE = "startPosition=%d";
    private static final String LIMIT_PARAM_TEMPLATE = "limit=%d";
    private static final String AMPERSAND_CHARACTER = "&";
    private static final String QUESTION_MARK = "?";

    private NextChunkRebuilder() {}

    public static String rebuild(Integer startPosition,
                                 Integer limit,
                                 HttpServletRequest request) {
        String currentStartPositionTemplate = String.format(START_POSITION_PARAM_TEMPLATE, startPosition);
        String nextChunkStartPositionTemplate = String.format(START_POSITION_PARAM_TEMPLATE, startPosition + limit);

        String currentUrl = request.getRequestURL().toString();

        String rebuildNextChunkUri = currentUrl
                .replace(currentStartPositionTemplate, nextChunkStartPositionTemplate);

        if (!rebuildNextChunkUri.contains(nextChunkStartPositionTemplate)) {
            String limitTemplate = String.format(LIMIT_PARAM_TEMPLATE, limit);

            rebuildNextChunkUri = currentUrl + QUESTION_MARK + limitTemplate + AMPERSAND_CHARACTER + nextChunkStartPositionTemplate;
        }
        return rebuildNextChunkUri;
    }

}
