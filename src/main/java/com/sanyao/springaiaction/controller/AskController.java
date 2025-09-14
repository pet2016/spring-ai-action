package com.sanyao.springaiaction.controller;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.entity.Question;
import com.sanyao.springaiaction.service.BoardGameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/04 16:01
 */
@RestController
public class AskController {

    private final BoardGameService boardGameService;

    public AskController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

//    @PostMapping(path="/ask", produces = "application/json")
//    public Answer ask(@RequestBody Question question){
//        return this.boardGameService.askQuestion(question);
//    }

    @PostMapping(path = "/ask", produces = "application/json")
    public Answer ask(@RequestBody @Valid Question question) {
        return boardGameService.askQuestion(question);
    }


    @PostMapping(path = "/askFlux", produces = "application/ndjson")
    public Answer askFlux(@RequestBody @Valid Question question) {
        return boardGameService.askQuestion(question);
    }


    @PostMapping(path = "/askChatMemory", produces = "application/json")
    public Answer askChatMemory(
            @RequestHeader(name = "X_AI_CONVERSATION_ID",
                    defaultValue = "default") String conversationId,
            @RequestBody @Valid Question question) {
        return boardGameService.askQuestionForChatMemory(question, conversationId);
    }

}
