package com.ccsw.tutorial.application.games;

import com.ccsw.tutorial.domain.games.Game;
import com.ccsw.tutorial.presentation.games.model.GameDto;

import java.util.List;

public interface GameService {

    List<Game> findAll();

    Game findById(Long id);

    void save(GameDto dto);

    void delete(Long id);
}