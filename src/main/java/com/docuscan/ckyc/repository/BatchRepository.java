package com.docuscan.ckyc.repository;

import com.docuscan.ckyc.model.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends MongoRepository<Batch, String> {
    // Additional query methods can be added here if needed
    Batch findByFiCode(String fiCode);
}
