package com.sanyao.springaiaction.entity;

import org.springframework.context.annotation.Description;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/13 10:27
 */
@Description("Request data about a game, given the game title.")
public record GameComplexityRequest(String title) {
}
