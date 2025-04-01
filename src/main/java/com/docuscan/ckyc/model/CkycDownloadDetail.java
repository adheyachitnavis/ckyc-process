package com.docuscan.ckyc.model;

import lombok.Data;

@Data
public class CkycDownloadDetail {
    private int bulkDownloadRecordType;
    private long ckycNo;
    private String authenticationFactor;
    private int authenticationFactorType;
    private String filler1;
}
