package com.docuscan.ckyc.service;

import com.docuscan.ckyc.model.Client;
import com.docuscan.ckyc.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public List<Client> getAllActiveClients() {
        return repository.findByStatus("ACTIVE");
    }

    public Client getByName(String clientName) {
        return repository.findByName(clientName);
    }
}
