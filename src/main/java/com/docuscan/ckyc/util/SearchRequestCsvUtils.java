package com.docuscan.ckyc.util;

import com.docuscan.ckyc.model.DetailRecord;
import com.docuscan.ckyc.model.HeaderRecord;
import com.docuscan.ckyc.model.search.SearchInputBatch;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SearchRequestCsvUtils {

    private static final String PIPE_DELIMITER = "|";
    private static final String NEW_LINE = "\n";

    public static void write(SearchInputBatch batch, String filePath) throws IOException {

        try (FileWriter writer = new FileWriter(filePath)) {

            // Write the Header Record first
            HeaderRecord header = batch.getHeaderRecord();
            if (header != null) {
                writer.append(formatHeaderRecord(header)).append(NEW_LINE);
            }

            // Write each Detail Record after the header
            List<DetailRecord> details = batch.getDetailRecords();
            if (details != null) {
                for (DetailRecord detail : details) {
                    writer.append(formatDetailRecord(detail)).append(NEW_LINE);
                }
            }

            System.out.println("CSV file created successfully at: " + filePath);

        } catch (IOException e) {
            throw e;
        }

    }

    private static String formatHeaderRecord(HeaderRecord header) {
        return String.join(PIPE_DELIMITER,
                String.valueOf(header.getRecordType()),
                safeString(header.getFiCode()),
                safeString(header.getRegionCode()),
                String.valueOf(header.getTotalNoOfDetailRecords()),
                safeString(header.getVersionNumber()),
                DateUtils.fromDate(header.getCreateDate()),
                "",
                "",
                "",
                ""
        );
    }

    private static String formatDetailRecord(DetailRecord detail) {
        return String.join(PIPE_DELIMITER,
                String.valueOf(detail.getRecordType()),
                String.valueOf(detail.getLineNumber()),
                safeString(detail.getIdentityType()),
                safeString(detail.getIdentityNumber()),
                safeString(detail.getName()),
                DateUtils.fromDate(detail.getDob()),
                safeString(detail.getGender())
        );
    }

    private static String safeString(String value) {
        return value != null ? value : "";
    }
}
