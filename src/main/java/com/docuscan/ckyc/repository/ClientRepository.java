package com.docuscan.ckyc.repository;

import com.docuscan.ckyc.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClientRepository extends MongoRepository<Client, String> {
    List<Client> findByStatus(String status);

    Client findByName(String name);
}
