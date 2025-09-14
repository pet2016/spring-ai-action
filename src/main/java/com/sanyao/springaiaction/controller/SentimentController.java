package com.sanyao.springaiaction.controller;

import com.sanyao.springaiaction.entity.SentimentAnalysis;
import com.sanyao.springaiaction.entity.TextInput;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 23:37
 */
@RestController
public class SentimentController {

    private final ChatClient chatClient;

    public SentimentController(
            ChatClient.Builder chatClientBuilder,
            @Value("classpath:/sentimentSystemPrompt.st")
            Resource sentimentSystemPrompt) {

        this.chatClient = chatClientBuilder
                .defaultSystem(sentimentSystemPrompt)
                .build();
    }

    @PostMapping("/sentiment")
    public SentimentAnalysis getSentiment(@RequestBody TextInput textInput) {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text("User text: {text}")
                        .param("text", textInput.text()))
                .call()
                .entity(SentimentAnalysis.class);
    }

}
