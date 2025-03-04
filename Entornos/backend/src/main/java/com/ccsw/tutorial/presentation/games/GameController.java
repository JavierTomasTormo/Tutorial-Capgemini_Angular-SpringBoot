package com.ccsw.tutorial.presentation.games;

import com.ccsw.tutorial.application.exceptions.BusinessRuleException;
import com.ccsw.tutorial.application.exceptions.EntityNotFoundException;
import com.ccsw.tutorial.application.exceptions.InvalidArgumentException;
import com.ccsw.tutorial.application.games.GameService;
import com.ccsw.tutorial.domain.games.Game;
import com.ccsw.tutorial.presentation.games.model.GameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Juegos encontrados correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<GameDto> findAll() {
        try {
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
        } catch (Exception e) {
            throw new BusinessRuleException("Error retrieving games: " + e.getMessage(), e);
        }
    }

    @Operation(summary = "Find By Id", description = "get one para los juegos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Juego encontrado"),
        @ApiResponse(responseCode = "404", description = "Juego no encontrado"),
        @ApiResponse(responseCode = "400", description = "ID de juego inválido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public GameDto findById(@PathVariable Long id) {
        // Validate input
        if (id == null || id <= 0) {
            throw new InvalidArgumentException("Game ID", id);
        }
        
        try {
            Game game = this.gameService.findById(id);
            
            if (game == null) {
                throw new EntityNotFoundException("Game", id);
            }
            
            GameDto dto = new GameDto();
            dto.setId(game.getId());
            dto.setTitle(game.getTitle());
            dto.setAge(game.getAge());
            dto.setCategoryId(game.getCategoryId());
            dto.setAuthorId(game.getAuthorId());
            
            return dto;
        } catch (EntityNotFoundException e) {
            // Rethrow EntityNotFoundException to maintain proper HTTP status
            throw e;
        } catch (Exception e) {
            // Wrap other exceptions in BusinessRuleException
            throw new BusinessRuleException("Error retrieving game: " + e.getMessage(), e);
        }
    }

    @Operation(summary = "Save", description = "Save se utiliza para crear o actualizar un game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Juego guardado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de juego inválidos"),
        @ApiResponse(responseCode = "404", description = "Categoría o autor no encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflicto al guardar el juego"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void save(@Valid @RequestBody GameDto dto) {
        // Validate input
        if (dto == null) {
            throw new InvalidArgumentException("Los datos del jugeo no pueden ser nulos");
        }
        
        // Validate title
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new InvalidArgumentException("El titulo no puede estar vacio");
        }
        
        // Validate age
        if (dto.getAge() != null && dto.getAge() < 0) {
            throw new InvalidArgumentException("La edad no puede ser negativa");
        }
        
        // Validate category
        if (dto.getCategoryId() == null) {
            throw new InvalidArgumentException("El ID no puede estar vacio");
        }
        
        if (dto.getAuthorId() == null) {
            throw new InvalidArgumentException("El ID no puede estar vacio");
        }
        
        try {
            this.gameService.save(dto);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Error al guardar el juego: " + e.getMessage(), e);
        }
    }

    @Operation(summary = "Delete", description = "este metodo elimina el game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Juego eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Juego no encontrado"),
        @ApiResponse(responseCode = "409", description = "No se puede eliminar el juego porque está prestado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new InvalidArgumentException("Game ID", id);
        }
        
        try {
            Game game = this.gameService.findById(id);
            if (game == null) {
                throw new EntityNotFoundException("Game", id);
            }
            
            this.gameService.delete(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Error al elimnar el juego: " + e.getMessage(), e);
        }
    }
}