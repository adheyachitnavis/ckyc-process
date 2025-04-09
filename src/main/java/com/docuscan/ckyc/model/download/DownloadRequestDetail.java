package com.docuscan.ckyc.model.download;

import lombok.Data;

@Data
public class DownloadRequestDetail {
    private int bulkDownloadRecordType;
    private long ckycNo;
    private String authenticationFactor;
    private int authenticationFactorType;
    private String filler1;
}
