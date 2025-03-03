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


    @Operation(summary = "Find", description = "Esto es un getAll para los clientes")
    @GetMapping
    public List<ClientDto> findAll() {
        List<Client> clients = this.clientService.findAll();
        
        return clients.stream()
                .map(client -> mapper.map(client, ClientDto.class))
                .collect(Collectors.toList());
    }


    @Operation(summary = "Save or Update", description = "Este metodo crea y guarda nuestros clientes")
    @RequestMapping(path = {"", "/{id}"}, method = RequestMethod.PUT)
    public ResponseEntity<?> save(@PathVariable(name = "id", required = false) Long id, @RequestBody ClientDto dto) {
        try {
            this.clientService.save(id, dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete", description = "Elimina los clientes")
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