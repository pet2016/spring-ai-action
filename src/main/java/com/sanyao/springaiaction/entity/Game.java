package com.sanyao.springaiaction.entity;

import org.springframework.data.annotation.Id;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/13 10:18
 */
public record Game(
        @Id Long id,
        String slug,
        String title){}
//        float complexity) {

//        public GameComplexity complexityEnum() {
//            int rounded = Math.round(complexity);
//            return GameComplexity.values()[rounded];
//        }
//}
