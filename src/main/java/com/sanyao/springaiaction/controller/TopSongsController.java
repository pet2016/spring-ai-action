package com.sanyao.springaiaction.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/09 21:30
 */
@RestController
public class TopSongsController {

    @Value("classpath:/top-songs-prompt.st")
    Resource topSongPromptTemplate;

    private final ChatClient chatClient;

    public TopSongsController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping(path = "/topSongs", produces = "application/json")
    public List<String> topSongs(@RequestParam("year") String year) {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(topSongPromptTemplate)
                        .param("year", year))
                .call()
                .entity(new ParameterizedTypeReference<List<String>>() {});
    }

}
