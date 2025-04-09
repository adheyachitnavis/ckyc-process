package com.docuscan.ckyc.model.download;

import lombok.Data;

import java.util.List;

@Data
public class DownloadRequest {
    private DownloadRequestHeader header;
    private List<DownloadRequestDetail> details;
}
