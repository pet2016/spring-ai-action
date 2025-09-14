package com.sanyao.springaiaction.controller;

import com.sanyao.springaiaction.entity.Answer;
import com.sanyao.springaiaction.entity.Question;
import com.sanyao.springaiaction.service.BoardGameService;
import com.sanyao.springaiaction.service.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 15:52
 */
@RestController
public class BurgerBattleArtController {

    private final BoardGameService boardGameService;
    private final ImageService imageService;

    public BurgerBattleArtController(BoardGameService boardGameService,
                                     ImageService imageService) {
        this.boardGameService = boardGameService;
        this.imageService = imageService;
    }

    @GetMapping(path="/burgerBattleArt")
    public String burgerBattleArt(@RequestParam("burger") String burger) {
        String instructions = getImageInstructions(burger);
        return imageService.generateImageForUrl(instructions);
    }

    @GetMapping(path="/burgerBattleArt", produces = "image/png")
    public byte[] burgerBattleArtImage(@RequestParam("burger") String burger) {
        String instructions = getImageInstructions(burger);
        return imageService.generateImageForImageBytes(instructions);
    }

    private String getImageInstructions(String burger) {
        Question question = new Question(
                "Burger Battle",
                "What ingredients are on the " + burger + " burger?");
        Answer answer = boardGameService.askQuestionForChatMemory(question, "art_conversation");

        return "A burger called " + burger + " " +
                "with the following ingredients: " + answer.answer() + ". " +
                "Style the background to match the name of the burger.";
    }

}
