package com.ccsw.tutorial.domain.games;

import com.ccsw.tutorial.application.games.GameService;
import com.ccsw.tutorial.presentation.games.model.GameDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Override
    public List<Game> findAll() {
        return StreamSupport.stream(this.gameRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Game findById(Long id) throws NoSuchElementException {
        return this.gameRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Juego con id: " + id + " no encontrado"));
    }

    @Override
    public void save(GameDto dto) {
        Game game = new Game();

        if (dto.getId() != null) {
            game.setId(dto.getId());
        }

        game.setTitle(dto.getTitle());
        game.setAge(dto.getAge());
        game.setCategoryId(dto.getCategoryId());
        game.setAuthorId(dto.getAuthorId());

        try {
            this.gameRepository.save(game);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el Juego: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            this.gameRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Un error ha surgido al intentar eliminar el juego con id: " + id, e);
        }
    }
}