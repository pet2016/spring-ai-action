package com.sanyao.springaiaction.repository;

import com.sanyao.springaiaction.entity.Conversation;
import com.sanyao.springaiaction.entity.ConversationMessage;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.List;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/12 23:55
 */
public class MongoChatMemory implements ChatMemory {

    private final ConversationRepository conversationRepository;

    public MongoChatMemory(
            ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public void add(String conversationId, Message message) {
        ChatMemory.super.add(conversationId, message);
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<ConversationMessage> conversationMessages = messages.stream()
                .map(message -> new ConversationMessage(
                        message.getMessageType().getValue(), message.getText()))
                .toList();

        conversationRepository.findById(conversationId)
                .ifPresentOrElse(conversation -> {
                            List<ConversationMessage> existingMessages = conversation.messages();
                            existingMessages.addAll(conversationMessages);
                            conversationRepository.save(
                                    new Conversation(conversationId, existingMessages));
                        },
                        () -> conversationRepository.save(
                                new Conversation(conversationId, conversationMessages)));
    }

    @Override
    public List<Message> get(String conversationId) {
        return List.of();
    }


    public List<Message> get(String conversationId, int lastN) {
        return conversationRepository
                .findById(conversationId)
        .map(conversation -> {
            List<Message> messageList = conversation.messages().stream()
                    .map(conversationMessage -> {
                        String messageType = conversationMessage.messageType();
                        Message message =
                                messageType.equals(MessageType.USER.getValue()) ?
                                        new UserMessage(conversationMessage.content()) :
                                        new AssistantMessage(conversationMessage.content());
                        return message;
                    }).toList();

            return messageList.stream()
                    .skip(Math.max(0, messageList.size() - lastN))
              .toList();
        }).orElse(List.of());
    }

    @Override
    public void clear(String conversationId) {
        conversationRepository.deleteById(conversationId);
    }
}
