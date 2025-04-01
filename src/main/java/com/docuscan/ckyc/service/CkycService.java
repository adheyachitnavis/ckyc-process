package com.docuscan.ckyc.service;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.model.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CkycService {
    private final CsvProcessingService csvService;
    private final KycAuditService auditService;
    private final CustomerService customerService;
    private final AESEncryptionService aesEncryptionService;
    private final BatchService batchService;
    private final ClientService clientService;
    private final CkycDownloadService downloadService;

    @Value("${FI_CODE}")
    private String FI_CODE;

    @Value("${REGION_CODE}")
    private String REGION_CODE;

    @Value("${Version}")
    private String Version;

    @Value("${client.base_path}")
    private String basePath;

    private String fileName;


    public List<Customer> process() throws CsvProcessingException {
        var customers = csvService.readInputCsvFile();
        var searchInput = createSearchInput(customers.size());
        try {
            for (Customer customer : customers) {
                    processCustomer(customer, searchInput);
            }
            fileName = generateFileName();
            csvService.writeSearchBatchFile(searchInput, fileName);
        } catch (KycProcessingException e) {
            auditService.createKycAudit(
                    KycAudit
                            .builder()
                            .customerId(e.getCustomer().getId())
                            .failureReason(e.getMessage())
                            .timestamp(Instant.now())
                            .status(CustomerStatus.FAILED)
                            .build()
            );
        }
        return customers;
    }

    public void processAllClients() {
        var clients = clientService.getAllActiveClients();
        clients.forEach(this::processCustomer);

    }

    public void processCustomer(Client client) {
        var today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        var dir = basePath + "/" + client.getName() + "/" + today;
        var path = dir + "/input/input.csv";
        client.setDir(dir);
        try {
            var customers = csvService.readInputCsvFile(path);
            var searchInput = createSearchInput(client, customers.size());
            for (Customer customer : customers) {
                try {
                    processCustomer(customer, searchInput);
                } catch (KycProcessingException e) {
                    e.printStackTrace();
                }
            }
            fileName = generateFileName(client);
            csvService.writeSearchBatchFile2(searchInput, client.getDir() + "/search/" + fileName);
        } catch (CsvProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processCustomer(Customer customer, SearchInputBatch searchInput) throws KycProcessingException {
        var newCustomer = customerService.saveCustomer(customer);
        auditService.createKycAudit(KycAudit
                .builder()
                .status(CustomerStatus.INITIATED)
                .customerId(newCustomer.getId())
                .timestamp(Instant.now())
                .build());

        searchInput.getDetailRecords().add(getDetailRecord(customer, searchInput.getDetailRecords().size()));


//        try {
//
//            String pid = createPidData(customer);
//            String key = rsaEncryptionService.encryptAESKey(aesEncryptionService.getSecretKey());
//            var inquiry = CkycInquiry
//                    .builder()
//                    .pid(pid)
//                    .sessionKey(key)
//                    .build();
//            var signature = signRequest(inquiry);
//
//        } catch (Exception e) {
//            throw new KycProcessingException(newCustomer, e);
//        }
    }

    private DetailRecord getDetailRecord(Customer customer, int lineNumber) {
        return DetailRecord.builder()
                .recordType(20)
                .identityType("C")
                .identityNumber(customer.getPanNo() + "|") // adding extra pipe char at the end as per sample files
                .lineNumber(lineNumber+1)
                .build();
    }

    private SearchInputBatch createSearchInput(int noOfDetailRecords) {
        var header = HeaderRecord.builder()
                .createDate(Instant.now())
                .recordType(10)
                .fiCode(FI_CODE)
                .regionCode(REGION_CODE)
                .totalNoOfDetailRecords(noOfDetailRecords)
                .versionNumber(Version)
                .build();
        return SearchInputBatch.builder()
                .id(UUID.randomUUID().toString())
                .headerRecord(header)
                .detailRecords(new ArrayList<>())
                .build();
    }

    private SearchInputBatch createSearchInput(Client client, int noOfDetailRecords) {
        var header = HeaderRecord.builder()
                .createDate(Instant.now())
                .recordType(10)
                .fiCode(client.getFiCode())
                .regionCode(client.getRegionCode())
                .totalNoOfDetailRecords(noOfDetailRecords)
                .versionNumber(Version)
                .build();
        return SearchInputBatch.builder()
                .id(UUID.randomUUID().toString())
                .headerRecord(header)
                .detailRecords(new ArrayList<>())
                .build();
    }

    private String signRequest(CkycInquiry inquiry) {
        return "";
    }

    // step 2 and 3
    private String createPidData(Customer customer) throws Exception {
        var data = PidData
                .builder()
                .dateTime(Instant.now().toString())
                .idType("C")
                .idNo(customer.getPanNo())
                .build();

        JAXBContext context = JAXBContext.newInstance(PidData.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        marshaller.marshal(data, writer);

        var pid = writer.toString();
        var encryptedKey = aesEncryptionService.encrypt(pid);
        return Base64.getEncoder().encodeToString(encryptedKey.getBytes());
    }

    private String generateFileName(){

        var batch = batchService.getBatchByFiCode(FI_CODE);
        int newNumber;
        String newBatchNumber;
        // Generate date in ddMMyyyy format
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        if (batch == null) {
            batch = Batch.builder()
                    .fiCode(FI_CODE)
                    .batchNumber(0)
                    .build();
        }
        newNumber = batch.getBatchNumber() + 1;
        batch.setBatchNumber(newNumber);
        batch.setTimestamp(Instant.now());
        batchService.saveBatch(batch);
        newBatchNumber = String.format("S%05d", newNumber);
        fileName = FI_CODE + "_" + currentDate + "_" + Version + "_" + newBatchNumber + ".txt" ;
        return fileName;
    }

    private String generateFileName(Client client) {
        var fiCode = client.getFiCode();
        var batch = batchService.getBatchByFiCode(fiCode);
        int newNumber;
        String newBatchNumber;
        // Generate date in ddMMyyyy format
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        if (batch == null) {
            batch = Batch.builder()
                    .fiCode(fiCode)
                    .batchNumber(0)
                    .build();
        }
        newNumber = batch.getBatchNumber() + 1;
        batch.setBatchNumber(newNumber);
        batch.setTimestamp(Instant.now());
        batchService.saveBatch(batch);
        newBatchNumber = String.format("S%05d", newNumber);
        fileName = fiCode + "_" + currentDate + "_" + Version + "_" + newBatchNumber + ".txt" ;
        return fileName;
    }

    public void createDownloadRequest() throws CsvProcessingException{
        var clients = clientService.getAllActiveClients();
        for(Client client : clients) {
           downloadService.createDownloadRequest(client);
        }
    }
}
