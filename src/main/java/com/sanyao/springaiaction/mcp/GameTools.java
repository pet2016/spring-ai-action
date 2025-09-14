package com.sanyao.springaiaction.mcp;

import com.sanyao.springaiaction.entity.Game;
import com.sanyao.springaiaction.entity.GameComplexityRequest;
import com.sanyao.springaiaction.entity.GameComplexityResponse;
import com.sanyao.springaiaction.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/13 10:20
 */

@Component
@Description("Fetches the complexity of a game.")
public class GameTools
        implements Function<GameComplexityRequest, GameComplexityResponse> {

    public static final Logger LOGGER =
            LoggerFactory.getLogger(GameTools.class);

    private final GameRepository gameRepository;

    public GameTools(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameComplexityResponse apply( GameComplexityRequest gameDataRequest) {
        String gameSlug = gameDataRequest.title()
                .toLowerCase()
                .replace(" ", "_");

        LOGGER.info("Getting complexity for {} ({})",
                gameDataRequest.title(), gameSlug);

        Optional<Game> gameOpt = gameRepository.findBySlug(gameSlug);

        Game game = gameOpt.orElseGet(() -> {
            LOGGER.warn("Game not found: {}", gameSlug);
            return new Game(
                    null,
                    gameSlug,
                    gameDataRequest.title());
        });

        return new GameComplexityResponse(
                game.title());
    }

}
