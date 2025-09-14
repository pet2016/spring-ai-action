package com.sanyao.springaiaction.configuration;

import com.sanyao.springaiaction.mcp.GameTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/13 10:22
 */

@Configuration
public class AutoConfigurationTool {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder,
                          VectorStore vectorStore,
                          GameTools gameTools) {
        return chatClientBuilder
                .defaultAdvisors(
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest.builder().build()).build(),
                        VectorStoreChatMemoryAdvisor.builder(vectorStore).build())
                .defaultTools(gameTools)
                .build();
    }

}
