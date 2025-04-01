package com.docuscan.ckyc.service;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.model.CkycDownloadRequest;
import com.docuscan.ckyc.model.Client;
import com.docuscan.ckyc.util.DownloadRequestCsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CsvDownloadService {

    private final FilePathService pathService;

    public void createDownloadRequestCsv(Client client, CkycDownloadRequest downloadReq) throws CsvProcessingException {
        var path = pathService.getDowanloadRequestFilePath(client, downloadReq);
        try {
            DownloadRequestCsvUtils.write(downloadReq, path);
        } catch (IOException e) {
            throw new CsvProcessingException(e.getMessage(), e);
        }
    }
}
