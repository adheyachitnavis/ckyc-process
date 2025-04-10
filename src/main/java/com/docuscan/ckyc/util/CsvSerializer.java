package com.docuscan.ckyc.util;

import com.docuscan.ckyc.model.DetailRecord;
import com.docuscan.ckyc.model.HeaderRecord;
import com.docuscan.ckyc.model.search.SearchInputBatch;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvSerializer {

    private static final String PIPE_DELIMITER = "|";
    private static final String NEW_LINE = "\n";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd-MM-yyyy")
            .withZone(ZoneOffset.UTC);

    public static String serializeToCsv(SearchInputBatch batch, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            var searchRequestRaw = new StringBuilder();
            // Write the Header Record first
            HeaderRecord header = batch.getHeaderRecord();
            if (header != null) {
                var headerText = formatHeaderRecord(header);
                writer.append(headerText).append(NEW_LINE);
                searchRequestRaw.append(headerText).append(NEW_LINE);
            }

            // Write each Detail Record after the header
            List<DetailRecord> details = batch.getDetailRecords();
            if (details != null) {
                for (DetailRecord detail : details) {
                    var detailText = formatDetailRecord(detail);
                    writer.append(detailText).append(NEW_LINE);
                    searchRequestRaw.append(detailText).append(NEW_LINE);
                }
            }

            System.out.println("CSV file created successfully at: " + filePath);
            return searchRequestRaw.toString();
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
                formatDate(header.getCreateDate()),
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
                formatDate(detail.getDob()),
                safeString(detail.getGender())
        );
    }

    private static String safeString(String value) {
        return value != null ? value : "";
    }

    private static String formatDate(Instant date) {
        return date != null ? DATE_FORMATTER.format(date) : "";
    }
}
