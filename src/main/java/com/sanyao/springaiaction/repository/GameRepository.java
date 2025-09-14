package com.sanyao.springaiaction.repository;

import com.sanyao.springaiaction.entity.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/13 10:19
 */
public interface GameRepository extends CrudRepository<Game, Long> {

    Optional<Game> findBySlug(String slug);

}
