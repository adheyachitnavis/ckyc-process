package com.docuscan.ckyc.service.download;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.model.download.DownloadRequest;
import com.docuscan.ckyc.model.CkycSearchResponse;
import com.docuscan.ckyc.model.Client;
import com.docuscan.ckyc.model.download.DownloadResponse;
import com.docuscan.ckyc.repository.DownloadResponseRepository;
import com.docuscan.ckyc.service.FilePathService;
import com.docuscan.ckyc.util.DownloadRequestCsvUtils;
import com.docuscan.ckyc.util.DownloadResponseCsvUtils;
import com.docuscan.ckyc.util.SearchResponseCsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvDownloadService {

    private final FilePathService pathService;
    private final DownloadResponseRepository responseRepository;

    public void createDownloadRequestCsv(Client client, DownloadRequest downloadReq) throws CsvProcessingException {
        var path = pathService.getDownloadRequestFilePath(client, downloadReq);
        try {
            DownloadRequestCsvUtils.write(downloadReq, path);
        } catch (IOException e) {
            throw new CsvProcessingException(e.getMessage(), e);
        }
    }

    public List<DownloadResponse> readDownloadResponse(String downloadResDirPath) throws CsvProcessingException {
        try {
            return DownloadResponseCsvUtils.read(downloadResDirPath);
        } catch (Exception e) {
            throw new CsvProcessingException(e.getMessage(), e);
        }
    }

    public void saveAll(List<DownloadResponse> responses) {
        responseRepository.saveAll(responses);
    }



}
