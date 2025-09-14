package com.sanyao.springaiaction.entity;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.List;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/12 20:24
 */

@Document
public record Conversation(
        @Id String conversationId,
        List<ConversationMessage> messages) {
}