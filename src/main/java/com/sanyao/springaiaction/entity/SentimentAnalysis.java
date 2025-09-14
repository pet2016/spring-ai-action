package com.sanyao.springaiaction.entity;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 23:36
 */
public record SentimentAnalysis(
        String text,
        double score,
        String explanation) {
}
