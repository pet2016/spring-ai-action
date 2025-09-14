package com.sanyao.springaiaction.exception;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 22:58
 */
public class ModerationException extends RuntimeException {
    public ModerationException(String category) {
        super(String.format(
                "Moderation failed. Content identified as %s.", category));
    }
}
