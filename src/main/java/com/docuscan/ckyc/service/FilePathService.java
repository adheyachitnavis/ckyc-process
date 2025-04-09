package com.docuscan.ckyc.service;

import com.docuscan.ckyc.model.Client;
import com.docuscan.ckyc.model.download.DownloadRequest;
import com.docuscan.ckyc.util.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FilePathService {

    @Value("${client.base_path}")
    private String basePath;

    public String getSearchResponseDirPath(Client client) {
        return clientCurrentProcessDirPath(client) + "/search_res";
    }

    public String getInputCsvFilePath(Client client) {
        return clientCurrentProcessDirPath(client) + "/input/input.csv";
    }

    public String getSearchRequestDirPath(Client client) {
        return clientCurrentProcessDirPath(client) + "/search_req";
    }

    public String getDownloadRequestFilePath(Client client, DownloadRequest downloadReq) {
        String filename = String.format("%s_%s_%s_%s_%s_%s.txt",
                client.getFiCode(),
                client.getRegionCode(),
                DateUtils.todayStr(),
                "V1.2",
                client.getUserId(),
                String.format("D%05d", downloadReq.getHeader().getBatchNumber())
        );
        return clientCurrentProcessDirPath(client) + "/download_req/" + filename;
    }

    private String clientDirPath(Client client) {
        return basePath + "/" + client.getName();
    }

    public String clientCurrentProcessDirPath(Client client) {
        return clientDirPath(client) + "/" + DateUtils.todayStrWithHiphen();
    }

    public String getDownloadResponseDirPath(Client client) {
        return clientCurrentProcessDirPath(client) + "/download_res";
    }

}
