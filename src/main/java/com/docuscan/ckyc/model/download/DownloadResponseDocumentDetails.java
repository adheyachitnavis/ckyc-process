package com.docuscan.ckyc.model.download;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadResponseDocumentDetails {
    private int recordType = 70;
    private int lineNumber;
    private String imageName;
    private int imageType;

}
