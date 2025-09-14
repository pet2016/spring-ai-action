package com.sanyao.springaiaction.configuration;

import com.sanyao.springaiaction.repository.ConversationRepository;
import com.sanyao.springaiaction.repository.MongoChatMemory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/12 10:25
 */
@Configuration
public class AutoConfigurationMemoryChat {

//    @Bean
//    ChatMemory chatMemory() {
//        return new InMemoryChatMemory();
//    }

    @Bean
    ChatClient chatClientForMessage(ChatClient.Builder chatClientBuilder, VectorStore vectorStore, ChatMemory chatMemory) {
        return chatClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                QuestionAnswerAdvisor.builder(vectorStore)
                        .searchRequest(SearchRequest.builder().build()).build())
        .build();
    }

    @Bean
    ChatClient chatClientForPrompt(
            ChatClient.Builder chatClientBuilder,
            VectorStore vectorStore,
            ChatMemory chatMemory) {
        return chatClientBuilder
                .defaultAdvisors(
                        PromptChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest.builder().build()).build())
                .build();
    }

    @Bean
    ChatMemory chatMemory(ConversationRepository conversationRepository) {
        return new MongoChatMemory(conversationRepository);
    }


    //Storing long-term memory in a vector store.
    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        return chatClientBuilder
                .defaultAdvisors(
                        VectorStoreChatMemoryAdvisor.builder(vectorStore).build(),
                QuestionAnswerAdvisor.builder(vectorStore)
                        .searchRequest(SearchRequest.builder().build()).build())
      .build();
    }



}
