package com.docuscan.ckyc.model.download;

import lombok.Data;

@Data
public class DownloadRequestHeader {
    private int recordType;
    private int batchNumber;
    private String fiCode;
    private String regionCode;
    private String branchCode;
    private int totalNoOfDetailRecords;
    private int custType;
}
