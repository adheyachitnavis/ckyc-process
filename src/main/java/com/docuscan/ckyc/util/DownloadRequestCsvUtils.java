package com.docuscan.ckyc.util;

import com.docuscan.ckyc.model.CkycDownloadDetail;
import com.docuscan.ckyc.model.CkycDownloadHeader;
import com.docuscan.ckyc.model.CkycDownloadRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DownloadRequestCsvUtils {

    public static void write(CkycDownloadRequest request, String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();

        // Write header
        CkycDownloadHeader header = request.getHeader();
        sb.append(String.format("%d|%05d|%s|%s|%s|%d|%02d||||\n",
                header.getRecordType(), header.getBatchNumber(), header.getFiCode(),
                header.getRegionCode(), header.getBranchCode(), header.getTotalNoOfDetailRecords(),
                header.getCustType()));

        // Write details
        for (CkycDownloadDetail detail : request.getDetails()) {
            sb.append(String.format("%d|%d|%s|%02d||\n",
                    detail.getBulkDownloadRecordType(), detail.getCkycNo(),
                    detail.getAuthenticationFactor(), detail.getAuthenticationFactorType()));
        }

        Files.write(Paths.get(filePath), sb.toString().getBytes(), StandardOpenOption.CREATE);
    }
}
