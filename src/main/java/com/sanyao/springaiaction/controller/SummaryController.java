package com.sanyao.springaiaction.controller;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.service.BoardGameService;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 23:12
 */
@RestController
public class SummaryController {

    private final BoardGameService boardGameService;

    public SummaryController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @PostMapping("/summarize")
    public Answer summarize(
            @RequestPart("rulesDocument") MultipartFile rulesDocument) {

        var reader = new TikaDocumentReader(rulesDocument.getResource());
        var rulesText = reader.get().getFirst().getText();

        return boardGameService.summarizeRules(rulesText);
    }

}
