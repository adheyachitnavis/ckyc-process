package com.docuscan.ckyc.repository;

import com.docuscan.ckyc.model.Run;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RunRepository extends MongoRepository<Run, String> {
}
