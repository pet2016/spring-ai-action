package com.sanyao.springaiaction.service;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/04 15:56
 */
@Service
public class BoardGameServiceImpl implements BoardGameService{

    private static final Logger log =
            LoggerFactory.getLogger(BoardGameServiceImpl.class);


    private final ChatClient chatClient;
    private final GameRulesService gameRulesService;


    public BoardGameServiceImpl(ChatClient.Builder chatClientBuilder, GameRulesService gameRulesService){
        ChatOptions chatOptions = ChatOptions.builder()
                .model("gpt-4o-mini")
                .build();
        this.chatClient = chatClientBuilder.defaultOptions(chatOptions).build();
        this.gameRulesService = gameRulesService;
    }

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource promptTemplate;

//    @Value("classpath:/promptTemplates/questionPromptTemplate.st")
//    Resource questionPromptTemplate;

    private static final String questionPromptTemplate = """ 
          You are a helpful assistant, answering questions about tabletop games.
          If you don't know anything about the game or don't know the answer,
          say "I don't know".

          The game is {game}.

          The question is: {question}.
          """;

    @Override
    public Answer askQuestion(Question question) {
//        String prompt = "Answer this question about " + question.gameTitle() +": "+ question.question();
        String gameRules = gameRulesService.getRulesFor(question.gameTitle());

        // responseEntity is a ResponseEntity<ChatResponse, Answer>
        var responseEntity = chatClient.prompt()
        .system(systemSpec -> systemSpec
                .text(promptTemplate)
                .param("gameTitle", question.gameTitle())
                .param("rules", gameRules))
        .user(question.question())
        .call()
        .responseEntity(Answer.class);

        ChatResponse response = responseEntity.response();
        ChatResponseMetadata metadata = response.getMetadata();
        logUsage(metadata.getUsage());

        return responseEntity.entity();
    }

    private void logUsage(Usage usage) {
        log.info("Token usage: prompt={}, generation={}, total={}",
                usage.getPromptTokens(),
                usage.getCompletionTokens(),
                usage.getTotalTokens());
    }


    @Override
    public Flux<String> askQuestionFlux(Question question) {

        String gameRules = gameRulesService.getRulesFor(question.gameTitle());
        return chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(promptTemplate)
                        .param("gameTitle", question.gameTitle())
                        .param("rules", gameRules))
                .user(question.question())
                .stream()
                .content();
    }

}
