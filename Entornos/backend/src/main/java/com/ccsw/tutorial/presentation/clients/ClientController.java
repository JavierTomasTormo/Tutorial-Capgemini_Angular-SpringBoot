package com.ccsw.tutorial.presentation.clients;

import com.ccsw.tutorial.application.clients.ClientService;
import com.ccsw.tutorial.application.exceptions.BusinessRuleException;
import com.ccsw.tutorial.application.exceptions.EntityNotFoundException;
import com.ccsw.tutorial.application.exceptions.InvalidArgumentException;
import com.ccsw.tutorial.domain.clients.Client;
import com.ccsw.tutorial.presentation.clients.model.ClientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Client", description = "API de los clientes")
@RequestMapping(value = "/clients")
@RestController
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Find All", description = "devuelve todos los clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes encontrados correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<ClientDto> findAll() {
        try {
            List<Client> clients = this.clientService.findAll();
            
            return clients.stream()
                    .map(client -> mapper.map(client, ClientDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Error recuperando clientes: " + e.getMessage(), e);
        }
    }

    @Operation(summary = "Find One", description = "devuelve un cliente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "400", description = "ID de cliente inválido"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ClientDto findById(@PathVariable("id") Long id) {
        // Validate ID
        if (id == null || id <= 0) {
            throw new InvalidArgumentException("Client ID", id);
        }
        
        try {
            Client client = this.clientService.findById(id);
            
            if (client == null) {
                throw new EntityNotFoundException("Client", id);
            }
            
            return mapper.map(client, ClientDto.class);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Error recuperando cliente: " + e.getMessage(), e);
        }
    }

    @Operation(summary = "Create", description = "crea un nuevo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de cliente inválidos"),
        @ApiResponse(responseCode = "409", description = "Conflicto al crear cliente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ClientDto dto) {
        if (dto == null) {
            throw new InvalidArgumentException("Client data cannot be null");
        }
        
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new InvalidArgumentException("Client name cannot be empty");
        }
        
        try {
            this.clientService.create(dto);
        } catch (EntityNotFoundException | InvalidArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Error en la creación del cliente: " + e.getMessage(), e);
        }
    }

    @Operation(summary = "Update", description = "actualiza un cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de cliente inválidos"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflicto al actualizar cliente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @Valid @RequestBody ClientDto dto) {
        if (id == null || id <= 0) {
            throw new InvalidArgumentException("Client ID", id);
        }
        
        if (dto == null) {
            throw new InvalidArgumentException("Client data cannot be null");
        }
        
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new InvalidArgumentException("Client name cannot be empty");
        }
        
        try {
            Client existingClient = this.clientService.findById(id);
            if (existingClient == null) {
                throw new EntityNotFoundException("Client", id);
            }
            
            this.clientService.update(id, dto);
        } catch (EntityNotFoundException | InvalidArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Error actualizando el cliente: " + e.getMessage(), e);
        }
    }

    @Operation(summary = "Delete", description = "elimina un cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
        @ApiResponse(responseCode = "400", description = "ID de cliente inválido"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "409", description = "No se puede eliminar el cliente porque tiene préstamos activos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new InvalidArgumentException("Client ID", id);
        }
        
        try {
            Client existingClient = this.clientService.findById(id);
            if (existingClient == null) {
                throw new EntityNotFoundException("Client", id);
            }
            
            this.clientService.delete(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Ha ocurrido un error al eliminar este cliente: " + e.getMessage(), e);
        }
    }
}