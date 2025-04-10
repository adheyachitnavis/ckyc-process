package com.docuscan.ckyc.service;

import com.docuscan.ckyc.model.Client;
import com.docuscan.ckyc.model.Run;
import com.docuscan.ckyc.repository.RunRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RunService {

    private final RunRepository repository;

    public Run createRun(Client client) {
        var run = Run.builder()
                .id(ObjectId.get().toString())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .clientId(new ObjectId(client.getId()))
                .status("READING_INPUT")
                .build();

        return repository.save(run);
    }

    public Run updateStatus(Run run, String status) {
        run.setUpdatedAt(Instant.now());
        run.setStatus(status);
        return repository.save(run);
    }
}
