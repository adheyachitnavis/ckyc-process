package com.docuscan.ckyc.model;

import lombok.Data;

@Data
public class CkycDownloadHeader {
    private int recordType;
    private int batchNumber;
    private String fiCode;
    private String regionCode;
    private String branchCode;
    private int totalNoOfDetailRecords;
    private int custType;
    private String filler2 = "";
    private String filler3 = "";
    private String filler4 = "";
}
