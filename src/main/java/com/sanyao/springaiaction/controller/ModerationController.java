package com.sanyao.springaiaction.controller;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.entity.Question;
import com.sanyao.springaiaction.service.BoardGameService;
import com.sanyao.springaiaction.service.ModerationService;
import com.sanyao.springaiaction.service.VoiceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 22:59
 */
@RestController
public class ModerationController {

    private final BoardGameService boardGameService;
    private final VoiceService voiceService;
    private final ModerationService moderationService;

    public ModerationController(BoardGameService boardGameService,
                         VoiceService voiceService,
                         ModerationService moderationService) {
        this.boardGameService = boardGameService;
        this.voiceService = voiceService;
        this.moderationService = moderationService;
    }

    @PostMapping(path = "/moderationAsk", produces = "application/json")
    public Answer ask(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(name="X_AI_CONVERSATION_ID",
                    defaultValue = "default") String conversationId,
            @RequestBody Question question) {

        moderationService.moderate(question.question());

        return boardGameService.askQuestionForSecurity(question, userDetails.getUsername() + "_" + conversationId);
    }




}
