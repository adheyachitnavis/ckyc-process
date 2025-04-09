package com.docuscan.ckyc.model.download;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadResponseHeader {
    @Builder.Default
    private int recordType = 10;
    private String batchNumber;
    private String fiCode;
    private String regionCode;
    private int totalNoOfDetailRecords;
    private Instant createdDate;
    private String version;
    @Builder.Default
    private String customerType = "01";
}
