package com.docuscan.ckyc.model.download;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "download_response")
public class DownloadResponse {

    private DownloadResponseHeader header;
    private List<DownloadResponseDetail> details;
}
