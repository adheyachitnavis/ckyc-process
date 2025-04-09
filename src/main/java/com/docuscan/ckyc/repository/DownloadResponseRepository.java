package com.docuscan.ckyc.repository;

import com.docuscan.ckyc.model.download.DownloadResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadResponseRepository extends MongoRepository<DownloadResponse, String> {

}
