package com.ccsw.tutorial.domain.clients;

import com.ccsw.tutorial.application.clients.ClientService;
import com.ccsw.tutorial.presentation.clients.model.ClientDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }


    @Override
    public void save(Long id, ClientDto dto) throws Exception {
        Client client;

        if (id == null) {
            if (this.clientRepository.existsByName(dto.getName())) {
                throw new Exception("Client with name '" + dto.getName() + "' already exists");
            }
            client = new Client();
        } else {
            client = this.clientRepository.findById(id).orElse(null);
            if (client == null) {
                throw new Exception("Client with id " + id + " not found");
            }
            
            if (this.clientRepository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new Exception("Client with name '" + dto.getName() + "' already exists");
            }
        }
        
        client.setName(dto.getName());
        this.clientRepository.save(client);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (this.clientRepository.findById(id).orElse(null) == null) {
            throw new Exception("Client with id " + id + " not found");
        }
        
        this.clientRepository.deleteById(id);
    }
}