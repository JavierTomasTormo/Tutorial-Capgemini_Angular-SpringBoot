package com.ccsw.tutorial.presentation.clients;

import com.ccsw.tutorial.application.clients.ClientService;
import com.ccsw.tutorial.domain.clients.Client;
import com.ccsw.tutorial.presentation.clients.model.ClientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Find All", description = "Método que devuelve todos los clientes")
    @GetMapping
    public List<ClientDto> findAll() {
        List<Client> clients = this.clientService.findAll();
        
        return clients.stream()
                .map(client -> mapper.map(client, ClientDto.class))
                .collect(Collectors.toList());
    }
    

    @Operation(summary = "Find One", description = "Método que devuelve un cliente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable("id") Long id) {
        Client client = this.clientService.findById(id);
        
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(mapper.map(client, ClientDto.class), HttpStatus.OK);
    }

    @Operation(summary = "Create", description = "Método que crea un nuevo cliente")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClientDto dto) {
        try {
            this.clientService.create(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update", description = "Método que actualiza un cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ClientDto dto) {
        try {
            this.clientService.update(id, dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete", description = "Método que elimina un cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            this.clientService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}