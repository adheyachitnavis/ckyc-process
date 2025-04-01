package com.docuscan.ckyc.model;

import lombok.Data;

import java.util.List;

@Data
public class CkycDownloadRequest {
    private CkycDownloadHeader header;
    private List<CkycDownloadDetail> details;
}
