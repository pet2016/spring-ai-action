package com.sanyao.springaiaction.controller;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.entity.Question;
import com.sanyao.springaiaction.service.BoardGameService;
import com.sanyao.springaiaction.service.BoardGameServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 22:36
 */

@RestController
public class SecurityController {


    private final BoardGameService boardGameService;

    public SecurityController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @PostMapping(path = "/securityAsk", produces = "application/json")
    public Answer ask(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(name="X_AI_CONVERSATION_ID",
                    defaultValue = "default") String conversationId,
            @RequestBody Question question) {
        return boardGameService.askQuestionForSecurity(question,
                userDetails.getUsername() + "_" + conversationId);
    }


}
