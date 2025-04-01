package com.docuscan.ckyc.model;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class SearchResponseHeader {
    private int recordType;
    private String fiCode;
    private String regionCode;
    private int totalNoOfDetailRecords;
    private String versionNumber;
    private Instant createDate;
    private String filler1;
    private String filler2;
    private String filler3;
    private String filler4;
    private int batchId;
}
