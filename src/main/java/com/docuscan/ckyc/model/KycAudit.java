package com.docuscan.ckyc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "kyc_audit")
public class KycAudit {
    @Id
    private String id;
    private String customerId; // Reference to Customer _id
    private CustomerStatus status;
    private String failureReason;

    @CreatedDate
    private Instant timestamp;
}
