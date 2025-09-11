package com.sanyao.springaiaction.service;

import com.sanyao.springaiaction.entity.Question;
import com.sanyao.springaiaction.entity.Answer;
import reactor.core.publisher.Flux;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/04 15:06
 */
public interface BoardGameService {

    Answer askQuestion(Question question);

    Flux<String> askQuestionFlux(Question question);
}
