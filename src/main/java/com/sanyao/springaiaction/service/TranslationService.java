package com.sanyao.springaiaction.service;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.entity.Translation;
import com.sanyao.springaiaction.entity.TranslationQuestion;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 23:29
 */
public interface TranslationService {
    Answer askQuestion(TranslationQuestion question, String conversationId);
}
