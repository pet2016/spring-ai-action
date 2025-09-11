package com.sanyao.springaiaction.service;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.entity.Question;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/04 16:16
 */
public class BoardGameServiceImplTests {

    @Autowired
    private BoardGameService boardGameService;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    private RelevancyEvaluator relevancyEvaluator;

    private FactCheckingEvaluator factCheckingEvaluator;

    public void setup(){
        this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
        this.factCheckingEvaluator = new FactCheckingEvaluator(chatClientBuilder);
    }

    @Test
    public void evaluateRelevancy(){
        String userText = "Why is the sky blue?";
        Question question = new Question("",userText);
        Answer answer = boardGameService.askQuestion(question);
        String referenceAnswer = "The sky is blue because of that was the paint color that was on sale.";

        EvaluationRequest evaluationRequest = new EvaluationRequest(userText,answer.answer());
        EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);

        Assertions.assertThat(evaluationResponse.isPass()).withFailMessage(
                """
                        ======================================
                        The answer "%s"
                        is not considered relevant to the question
                        "%s".
                        """, answer.answer(),userText).isTrue();
    }

}
