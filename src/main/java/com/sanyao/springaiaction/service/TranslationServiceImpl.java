package com.sanyao.springaiaction.service;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.entity.Translation;
import com.sanyao.springaiaction.entity.TranslationQuestion;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor.FILTER_EXPRESSION;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 23:30
 */
public class TranslationServiceImpl implements TranslationService {

    private ChatClient chatClient;

    public TranslationServiceImpl(ChatClient.Builder chatClientBuilder, GameRulesService gameRulesService) {
        ChatOptions chatOptions = ChatOptions.builder()
                .model("gpt-4o-mini")
                .build();
        this.chatClient = chatClientBuilder.defaultOptions(chatOptions).build();
    }

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource promptTemplate;

    @Override
    public Answer askQuestion(TranslationQuestion question, String conversationId) {
        return chatClient.prompt()
                .user(question.question())
                .system(systemSpec -> systemSpec
                        .text(promptTemplate)
                        .param("gameTitle", question.gameTitle())
                        .param("targetLanguage", question.language()))
                .advisors(advisorSpec -> advisorSpec
                        .param(FILTER_EXPRESSION,
                                getDocumentMatchExpression(question.gameTitle()))
                        .param(CONVERSATION_ID, conversationId))
                .call()
                .entity(Answer.class);
    }
    private String getDocumentMatchExpression(String gameTitle) {
        return String.format("gameTitle == '%s' %s",
                gameTitle,
                getPremiumContentFilterExpression());
    }
    private static String getPremiumContentFilterExpression() {
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (!authorities.stream().anyMatch(
                a -> a.getAuthority().equals("ROLE_PREMIUM_USER"))) {
            return "AND documentType != 'PREMIUM'";
        }
        return "";
    }
}
