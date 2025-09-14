package com.sanyao.springaiaction.entity;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 23:17
 */
public record TranslationQuestion(
        String gameTitle,
        String question,
        String language) {

    public static final String DEFAULT_LANGUAGE = "English";

    public TranslationQuestion {
        if (language == null || language.isBlank()) {
            language = DEFAULT_LANGUAGE;
        }
    }

    public TranslationQuestion(String gameTitle, String question) {
        this(gameTitle, question, DEFAULT_LANGUAGE);
    }

}