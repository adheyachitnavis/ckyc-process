package com.docuscan.ckyc.model.search;

import lombok.Data;

import java.time.Instant;

@Data
public class SearchResponseHeader {
    private int recordType;
    private String fiCode;
    private String regionCode;
    private int totalNoOfDetailRecords;
    private String versionNumber;
    private Instant createDate;
    private int batchId;
}
