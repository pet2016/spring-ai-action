package com.sanyao.springaiaction.service;

import com.sanyao.springaiaction.exception.ModerationException;
import org.springframework.ai.moderation.ModerationModel;
import org.springframework.ai.moderation.ModerationPrompt;
import org.springframework.stereotype.Service;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 22:56
 */
@Service
public class ModerationService {

    private final ModerationModel moderationModel;

    public ModerationService(ModerationModel moderationModel) {
        this.moderationModel = moderationModel;
    }

    public void moderate(String text) {
        var moderationResponse =
                moderationModel.call(new ModerationPrompt(text));

        var moderationResult = moderationResponse.getResult()
                .getOutput().getResults().getFirst();
        var categories = moderationResult.getCategories();

        if (categories.isHate() || categories.isHateThreatening())
            throw new ModerationException("Hate");
        else if (categories.isHarassment() ||
                categories.isHarassmentThreatening())
            throw new ModerationException("Harassment");
        else if (categories.isViolence())
            throw new ModerationException("Violence");
    }

}

