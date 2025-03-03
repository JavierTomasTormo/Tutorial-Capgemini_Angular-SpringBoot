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
    public Client findById(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    @Override
    public void create(ClientDto dto) throws Exception {
        if (this.clientRepository.existsByName(dto.getName())) {
            throw new Exception("Cliente con el nombre '" + dto.getName() + "' ya existe");
        }
        
        Client client = new Client();
        client.setName(dto.getName());
        this.clientRepository.save(client);
    }

    @Override
    public void update(Long id, ClientDto dto) throws Exception {
        Client client = this.clientRepository.findById(id).orElse(null);
        if (client == null) {
            throw new Exception("Cliente con id " + id + " no se ha encontrado");
        }
        
        if (this.clientRepository.existsByNameAndIdNot(dto.getName(), id)) {
            throw new Exception("Cliente con el nombre '" + dto.getName() + "' ya existe");
        }
        
        client.setName(dto.getName());
        this.clientRepository.save(client);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (this.clientRepository.findById(id).orElse(null) == null) {
            throw new Exception("Cliente con id " + id + " no se ha encontrado");
        }
        
        this.clientRepository.deleteById(id);
    }
}