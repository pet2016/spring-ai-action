package com.sanyao.springaiaction.entity;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/10 14:35
 */
public record GameTitle(String title) {

    public String getNormalizedTitle() {
        return title.toLowerCase().replace(" ", "_");
    }

}