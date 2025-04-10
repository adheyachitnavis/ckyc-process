package com.docuscan.ckyc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "run")
public class Run {
    @Id
    private String id;
    private ObjectId clientId;
    private String searchBatchNo;
    private int downloadBatchNo;
    private String searchRequestRaw;
    private String downloadRequestRaw;
    private String downloadResponseRaw;
    private Instant createdAt;
    private Instant updatedAt;
    private String status; //TODO: create enum
}
