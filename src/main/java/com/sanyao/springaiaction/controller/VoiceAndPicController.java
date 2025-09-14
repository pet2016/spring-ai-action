package com.sanyao.springaiaction.controller;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.entity.Question;
import com.sanyao.springaiaction.service.BoardGameService;
import com.sanyao.springaiaction.service.VoiceService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 13:08
 */
@RestController
public class VoiceAndPicController {

    private final BoardGameService boardGameService;
    private final VoiceService voiceService;

    public VoiceAndPicController(BoardGameService boardGameService, VoiceService voiceService) {
        this.boardGameService = boardGameService;
        this.voiceService = voiceService;
    }


//    @PostMapping(path = "/audioAsk", produces = "application/json")
//    public Answer audioAsk(@RequestHeader(name = "X_AI_CONVERSATION_ID", defaultValue = "default") String conversationId,
//                           @RequestParam("audio") MultipartFile audioBlob, @RequestParam("gameTitle") String gameTitle) {
//
//        String transcription = voiceService.transcribe(audioBlob.getResource());
//        Question transcribedQuestion = new Question(gameTitle, transcription);
//        return boardGameService.askQuestionForChatMemory(transcribedQuestion, conversationId);
//
//    }

    @PostMapping(path = "/audioAsk", produces = "audio/mpeg")
    public Resource audioAskAudioResponse(
            @RequestHeader(name = "X_AI_CONVERSATION_ID",
                    defaultValue = "default") String conversationId,
            @RequestParam("audio") MultipartFile blob,
            @RequestParam("gameTitle") String game) {

        String transcription = voiceService.transcribe(blob.getResource());
        Question transcribedQuestion = new Question(game, transcription);
        Answer answer = boardGameService.askQuestionForChatMemory(transcribedQuestion, conversationId);
        return voiceService.textToSpeech(answer.answer());
    }

    @PostMapping(path = "/visionAsk",
            produces = "application/json",
            consumes = "multipart/form-data")
    public Answer visionAsk(
            @RequestHeader(name = "X_AI_CONVERSATION_ID",
                    defaultValue = "default") String conversationId,
            @RequestPart("image") MultipartFile image,
            @RequestPart("gameTitle") String game,
            @RequestPart("question") String questionIn) {

        Resource imageResource = image.getResource();
        String imageContentType = image.getContentType();

        Question question = new Question(game, questionIn);
        return boardGameService.askQuestion(
                question, imageResource, imageContentType, conversationId);
    }

}
