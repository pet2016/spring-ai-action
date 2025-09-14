package com.sanyao.springaiaction.controller;

import com.sanyao.springaiaction.entity.Translation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 23:15
 */
@RestController
public class TranslatorController {

    @Value("classpath:/translationPromptTemplate.st")
    private Resource userPromptMessage;

    private final ChatClient chatClient;

    public TranslatorController(
            ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/translate")
    public Translation translate(@RequestBody Translation request) {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptMessage)
                        .param("sourceLanguage", request.sourceLanguage())
                        .param("targetLanguage", request.targetLanguage())
                        .param("sourceText", request.text()))
                .call()
                .entity(Translation.class);
    }

}
