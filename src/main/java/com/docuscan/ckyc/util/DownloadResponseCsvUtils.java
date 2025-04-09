package com.docuscan.ckyc.util;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.model.Address;
import com.docuscan.ckyc.model.CkycVerifier;
import com.docuscan.ckyc.model.Name;
import com.docuscan.ckyc.model.PhoneDetail;
import com.docuscan.ckyc.model.download.DownloadResponse;
import com.docuscan.ckyc.model.download.DownloadResponseDetail;
import com.docuscan.ckyc.model.download.DownloadResponseDocumentDetails;
import com.docuscan.ckyc.model.download.DownloadResponseHeader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DownloadResponseCsvUtils {

    public static List<DownloadResponse> read(String dirPath) throws IOException, CsvProcessingException {
        File folder = new File(dirPath);
        if (!folder.exists() || !folder.isDirectory()) {
            log.info("Folder not found " + dirPath);
            return Collections.emptyList();
        }

        List<Path> csvFiles = Files.list(Paths.get(dirPath))
                .filter(path -> path.toString().endsWith(".txt"))
                .collect(Collectors.toList());

        List<DownloadResponse> responses = new ArrayList<>();

        for (Path filePath : csvFiles) {
            responses.add(parseCsv(filePath));
        }

        return responses;
    }

    private static DownloadResponse parseCsv(Path filePath) throws IOException, CsvProcessingException {
        List<String> lines = Files.readAllLines(filePath)
                .stream()
                .sorted(DownloadResponseCsvUtils::downloadResponseLinesSorter)
                .toList();
        var response = new DownloadResponse();
        response.setDetails(new ArrayList<>());


        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split("\\|", -1);
            var recordType = parts[0];
            if(i == 0) {
                var header = new DownloadResponseHeader();
                header.setBatchNumber(parts[1]);
                header.setFiCode(parts[2]);
                header.setRegionCode(parts[3]);
                header.setTotalNoOfDetailRecords(NumberUtil.parseInt(parts[4]));
                header.setCreatedDate(DateUtils.toDate(parts[5]));
                header.setVersion(parts[6]);
                header.setCustomerType(parts[7]);
                response.setHeader(header);
            } else if(recordType.equals("20")) {
                var detail = new DownloadResponseDetail();

                detail.setLineNumber(NumberUtil.parseInt(parts[1]));
                detail.setCkycNumber(parts[18]);

                detail.setApplicantName(getName(19, parts));
                detail.setMaidenName(getName(24, parts));
                detail.setRelationshipType(NumberUtil.parseInt(parts[29]));
                detail.setRelatedPersonName(getName(30, parts));
                detail.setMotherName(getName(35, parts));

                detail.setGender(parts[40]);
                detail.setDateOfBirth(DateUtils.toDate(parts[44]));
                detail.setPanNo(parts[52]);
                detail.setAddress(getAddres(60, parts));
                detail.setIsSameAsCurrentAddress(parts[70]);
                detail.setCurrentAddress(getAddres(72, parts));

                detail.setResidencePhone(getPhoneDetail(91, parts));
                detail.setOfficePhone(getPhoneDetail(93, parts));
                detail.setMobilePhone(getPhoneDetail(95, parts));
                detail.setFaxNumber(getPhoneDetail(97, parts));

                detail.setEmail(parts[99]);
                detail.setRemarks(parts[100]);

                detail.setDeclarationDate(DateUtils.toDate(parts[101]));
                detail.setDeclarationPlace(parts[102]);
                detail.setKycVerificationDate(DateUtils.toDate(parts[103]));
                detail.setTypeOfDocumentSubmitted(NumberUtil.parseInt(parts[104]));
                detail.setCkycVerifier(getCkycVerifier(105, parts));

                detail.setOrganisationName(parts[109]);
                detail.setOrganisationCode(parts[110]);

                detail.setNumberOfIdentityDetail(NumberUtil.parseInt(parts[111]));
                detail.setNumberOfRelatedPersonDetail(NumberUtil.parseInt(parts[112]));
                detail.setNumberOfImages(NumberUtil.parseInt(parts[115]));

                detail.setErrorCode(parts[116]);
                detail.setDocuments(new ArrayList<>());

                response.getDetails().add(detail);
            } else if(recordType.equals("70")) {
                var document = new DownloadResponseDocumentDetails();

                document.setLineNumber(NumberUtil.parseInt(parts[1]));
                document.setImageName(parts[2]);
                document.setImageType(NumberUtil.parseInt(parts[3]));

                response.getDetails()
                        .getLast()
                        .getDocuments().add(document);
            } else {
                throw new CsvProcessingException("Invalid record type " + recordType);
            }




        }
        return response;
    }

    private static CkycVerifier getCkycVerifier(int i, String[] parts) {
        return CkycVerifier.builder()
                .name(parts[i++])
                .designation(parts[i++])
                .branch(parts[i++])
                .empCode(parts[i++])
                .build();
    }

    private static PhoneDetail getPhoneDetail(int i, String[] parts) {
        return PhoneDetail.builder()
                .code(parts[i++])
                .number(parts[i++])
                .build();
    }

    private static Address getAddres(int i, String[] parts) {
        return Address.builder()
                .line1(parts[i++])
                .line2(parts[i++])
                .line3(parts[i++])
                .city(parts[i++])
                .district(parts[i++])
                .state(parts[i++])
                .country(parts[i++])
                .pincode(parts[i++])
                .proofType(parts[i++])
                .build();
    }

    private static Name getName(int i, String[] parts) {
        return Name.builder()
                .prefix(parts[i++])
                .first(parts[i++])
                .middle(parts[i++])
                .last(parts[i++])
                .full(parts[i++])
                .build();
    }

    private static int downloadResponseLinesSorter(String l1, String l2) {
        var p1 = l1.split("\\|");
        var p2 = l2.split("\\|");

        if (p1[0].equals("10")) {
            return -1;
        } else if(p2[0].equals("10")) {
            return 1;
        }

        if(p1[1].equals(p2[1])) {
            return p1[0].compareTo(p2[0]);
        }

//        if(p1[0].equals(p2[0])) {
//            return p1[1].compareTo(p2[1]);
//        }

        return p1[1].compareTo(p2[1]);
    }
}
