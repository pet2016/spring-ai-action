package com.sanyao.springaiaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/09 20:24
 */
public class GameRulesService {

    private static final Logger LOG =
            LoggerFactory.getLogger(GameRulesService.class);

    public String getRulesFor(String gameName) {
        try {
            String filename = String.format(
                    "classpath:/gameRules/%s.txt",
                    gameName.toLowerCase().replace(" ", "_"));

            return new DefaultResourceLoader()
                    .getResource(filename)
                    .getContentAsString(Charset.defaultCharset());
        } catch (IOException e) {
            LOG.info("No rules found for game: " + gameName);
            return "";
        }
    }

}
