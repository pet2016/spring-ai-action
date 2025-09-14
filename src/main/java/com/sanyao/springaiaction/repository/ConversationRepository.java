package com.sanyao.springaiaction.repository;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/12 21:50
 */

import com.sanyao.springaiaction.entity.Conversation;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository extends  CrudRepository<Conversation, String>{
}
