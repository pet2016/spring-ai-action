package com.sanyao.springaiaction.service;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.entity.Question;
import com.sanyao.springaiaction.mcp.GameTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor.FILTER_EXPRESSION;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/13 10:24
 */
public class BoradGameToolServiceImpl implements BoardGameService{

    private final ChatClient chatClient;
    private final GameTools gameTools;

    public BoradGameToolServiceImpl(ChatClient chatClient,
                                    GameTools gameTools) {
        this.chatClient = chatClient;
        this.gameTools = gameTools;
    }

    @Override
    public Answer askQuestion(Question question) {
        return null;
    }

    @Override
    public Flux<String> askQuestionFlux(Question question) {
        return null;
    }

    @Override
    public Answer askQuestionForChatMemory(Question question, String conversationId) {
        return chatClient.prompt()
                .user(question.question())
                .tools(gameTools)
                .call()
                .entity(Answer.class);
    }

    @Override
    public Answer askQuestionForSecurity(Question question, String conversationId) {
        return null;
    }

    @Override
    public Answer summarizeRules(String text) {
        return null;
    }


    @Override
    public Answer askQuestion(Question question, Resource image, String imageContentType, String conversationId) {
        return null;
    }
}
