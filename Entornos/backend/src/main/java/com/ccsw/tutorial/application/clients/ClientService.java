package com.ccsw.tutorial.application.clients;

import com.ccsw.tutorial.domain.clients.Client;
import com.ccsw.tutorial.presentation.clients.model.ClientDto;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Client findById(Long id);

    void create(ClientDto dto) throws Exception;

    void update(Long id, ClientDto dto) throws Exception;

    void delete(Long id) throws Exception;
}