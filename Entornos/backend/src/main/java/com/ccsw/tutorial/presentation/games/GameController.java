package com.ccsw.tutorial.presentation.games;

import com.ccsw.tutorial.application.games.GameService;
import com.ccsw.tutorial.domain.games.Game;
import com.ccsw.tutorial.presentation.games.model.GameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Game", description = "API de los juegos")
@RequestMapping(value = "/games")
@RestController
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Find", description = "get all de los games")
    @GetMapping
    public List<GameDto> findAll() {
        List<Game> games = this.gameService.findAll();

        return games.stream().map(game -> {
            GameDto dto = new GameDto();
            dto.setId(game.getId());
            dto.setTitle(game.getTitle());
            dto.setAge(game.getAge());
            dto.setCategoryId(game.getCategoryId());
            dto.setAuthorId(game.getAuthorId());
            
            return dto;
        }).collect(Collectors.toList());
    }

    @Operation(summary = "Find By Id", description = "get one para los juegos")
    @GetMapping("/{id}")
    public ResponseEntity<GameDto> findById(@PathVariable Long id) {
        Game game = this.gameService.findById(id);
        
        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        GameDto dto = new GameDto();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setAge(game.getAge());
        dto.setCategoryId(game.getCategoryId());
        dto.setAuthorId(game.getAuthorId());
        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Save", description = "Save se utiliza para crear o actualizar un game")
    @PostMapping
    public void save(@RequestBody GameDto dto) {
        this.gameService.save(dto);
    }

    @Operation(summary = "Delete", description = "este metodo elimina el game")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.gameService.delete(id);
    }
}