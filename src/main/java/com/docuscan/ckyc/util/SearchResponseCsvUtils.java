package com.docuscan.ckyc.util;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.model.search.CkycSearchResponse;
import com.docuscan.ckyc.model.search.SearchResponseDetail;
import com.docuscan.ckyc.model.search.SearchResponseHeader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchResponseCsvUtils {

    public static List<CkycSearchResponse> read(String dirPath) throws IOException, CsvProcessingException {
        List<Path> csvFiles = Files.list(Paths.get(dirPath))
                .filter(path -> path.toString().endsWith(".txt"))
                .collect(Collectors.toList());

        List<CkycSearchResponse> responses = new ArrayList<>();

        for (Path filePath : csvFiles) {
            responses.add(parseCsv(filePath));
        }

        return responses;
    }

    private static CkycSearchResponse parseCsv(Path filePath) throws IOException, CsvProcessingException {
        List<String> lines = Files.readAllLines(filePath);
        CkycSearchResponse response = new CkycSearchResponse();
        List<SearchResponseDetail> details = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split("\\|");
            if (i == 0) {
                SearchResponseHeader header = new SearchResponseHeader();
                header.setRecordType(Integer.parseInt(parts[0]));
                header.setFiCode(parts[1]);
                header.setRegionCode(parts[2]);
                header.setTotalNoOfDetailRecords(Integer.parseInt(parts[3]));
                header.setVersionNumber(parts[4]);
                header.setCreateDate(DateUtils.toDate(parts[5]));
                response.setHeader(header);
            } else {
                SearchResponseDetail detail = new SearchResponseDetail();
                detail.setRecordType(Integer.parseInt(parts[0]));
                detail.setLineNumber(Integer.parseInt(parts[1]));
                detail.setIdentityType(parts[2]);
                detail.setIdentityNumber(parts[3]);
                detail.setKycNumber(parts[4]);
                detail.setApplicantName(parts[5]);
                detail.setPan(parts[6]);
                detail.setAadhaar(Long.parseLong(parts[7]));
                detail.setVoterId(parts[8]);
                detail.setPassport(parts[9]);
                detail.setDrivingLicense(parts[10]);
                detail.setNrega(parts[11]);
                detail.setErrorMessage(parts.length > 12 ? parts[12] : "");
                detail.setNationalPopulationRegisterLetter(parts.length > 13 ? parts[13] : "");
                detail.setCertificateOfIncorporation(parts.length > 14 ? parts[14] : "");
                detail.setRegistrationCertificate(parts.length > 15 ? parts[15] : "");
                details.add(detail);
            }
        }
        response.setDetails(details);
        var bachCode = extractBatchCode(filePath.getFileName().toString());
        response.getHeader().setBatchId(bachCode);
        return response;
    }

    public static int extractBatchCode(String fileName) throws CsvProcessingException {
        // Regex pattern to match the batch code after '_S' and before '_Res.txt'
        Pattern pattern = Pattern.compile("_S(\\d+)_Res\\.txt$");
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.find()) {
            var batchCode = matcher.group(1);
            return Integer.parseInt(batchCode);
        }
        throw new CsvProcessingException("No batch code found in the file - {}".formatted(fileName));
    }
}
