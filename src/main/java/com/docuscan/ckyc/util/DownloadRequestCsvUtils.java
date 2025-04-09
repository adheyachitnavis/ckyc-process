package com.docuscan.ckyc.util;

import com.docuscan.ckyc.model.download.DownloadRequestDetail;
import com.docuscan.ckyc.model.download.DownloadRequestHeader;
import com.docuscan.ckyc.model.download.DownloadRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DownloadRequestCsvUtils {

    public static void write(DownloadRequest request, String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();

        // Write header
        DownloadRequestHeader header = request.getHeader();
        sb.append(String.format("%d|%05d|%s|%s|%s|%d|%02d||||\n",
                header.getRecordType(), header.getBatchNumber(), header.getFiCode(),
                header.getRegionCode(), header.getBranchCode(), header.getTotalNoOfDetailRecords(),
                header.getCustType()));

        // Write details
        for (DownloadRequestDetail detail : request.getDetails()) {
            sb.append(String.format("%d|%d|%s|%02d||\n",
                    detail.getBulkDownloadRecordType(), detail.getCkycNo(),
                    detail.getAuthenticationFactor(), detail.getAuthenticationFactorType()));
        }

        Files.write(Paths.get(filePath), sb.toString().getBytes(), StandardOpenOption.CREATE);
    }
}
