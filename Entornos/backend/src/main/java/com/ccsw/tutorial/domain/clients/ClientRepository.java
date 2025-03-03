package com.ccsw.tutorial.domain.clients;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    
    boolean existsByNameAndIdNot(String name, Long id);
    
    boolean existsByName(String name);
}
