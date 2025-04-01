package com.docuscan.ckyc.repository;

import com.docuscan.ckyc.model.KycAudit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycAuditRepository extends MongoRepository<KycAudit, String> {
    // Additional query methods can be added here if needed
}
