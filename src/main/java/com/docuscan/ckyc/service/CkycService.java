package com.docuscan.ckyc.service;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.exception.KycProcessingException;
import com.docuscan.ckyc.model.*;
import com.docuscan.ckyc.model.search.SearchInputBatch;
import com.docuscan.ckyc.service.download.CkycDownloadService;
import com.docuscan.ckyc.service.encryption.AESEncryptionService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CkycService {
    private final CsvProcessingService csvService;
    private final KycAuditService auditService;
    private final CustomerService customerService;
    private final AESEncryptionService aesEncryptionService;
    private final BatchService batchService;
    private final ClientService clientService;
    private final CkycDownloadService downloadService;
    private final FilePathService filePathService;
    private final RunService runService;

    @Value("${FI_CODE}")
    private String FI_CODE;

    @Value("${REGION_CODE}")
    private String REGION_CODE;

    @Value("${Version}")
    private String version;

    @Value("${client.base_path}")
    private String basePath;

    public void process(String clientName) throws CsvProcessingException {
        var client = clientService.getByName(clientName);
        if (client == null) {
            throw new CsvProcessingException("No client found with name " + clientName);
        }
        processCustomer(client);
    }

    public void processAllClients() throws CsvProcessingException {
        var clients = clientService.getAllActiveClients();
        for (Client client : clients) {
            processCustomer(client);
        }

    }

    public void processCustomer(Client client) throws CsvProcessingException {
        var run = runService.createRun(client);
        var path = filePathService.getInputCsvFilePath(client);
        var customers = csvService.readInputCsvFile(path);
        var newCustomers = new ArrayList<Customer>();
        for (Customer customer : customers) {
            try {
                customer.setRunId(new ObjectId(run.getId()));
                customer.setClientId(new ObjectId(client.getId()));
                newCustomers.add(processCustomer(customer));
                log.info("Customer created successfully " + customer.getCustomerName());
            } catch (KycProcessingException e) {
                log.error("Skipped processing customer " + customer.getCustomerName() + "For client " + client.getName(), e);
            }
        }
        run = runService.updateStatus(run, "CUSTOMER_CREATED");

        var searchInput = createSearchInput(client, customers.size());
        for (Customer customer : newCustomers) {
            searchInput.getDetailRecords().add(getDetailRecord(customer, searchInput.getDetailRecords().size()));
        }
        log.info("Search Request for prepared for client "+ client.getName());
        var batchNo = batchService.getSearchBatchNumber(client);
        var file = filePathService.getSearchRequestFile(client, batchNo);
        log.info("Writing t "+ client.getName());

        var searchRequestRaw = csvService.writeSearchBatchFile(searchInput, file);
        run.setSearchBatchNo(searchInput.getBatchNo());
        run.setSearchRequestRaw(searchRequestRaw);
        run.setSearchBatchNo(batchNo);
        runService.updateStatus(run, "SEARCH_REQUEST_CREATED");
    }

    private Customer processCustomer(Customer customer) throws KycProcessingException {
        var newCustomer = customerService.saveCustomer(customer);
        auditService.createKycAudit(KycAudit
                .builder()
                .status(CustomerStatus.INITIATED)
                .customerId(newCustomer.getId())
                .timestamp(Instant.now())
                .build());
        return newCustomer;


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
                .identityType("C")
                .identityNumber(customer.getPanNo() + "|") // adding extra pipe char at the end as per sample files
                .lineNumber(lineNumber+1)
                .build();
    }

    private SearchInputBatch createSearchInput(int noOfDetailRecords) {
        var header = HeaderRecord.builder()
                .createDate(Instant.now())
                .fiCode(FI_CODE)
                .regionCode(REGION_CODE)
                .totalNoOfDetailRecords(noOfDetailRecords)
                .versionNumber(version)
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
                .versionNumber(version)
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
        batch.setUpdatedAt(Instant.now());
        batchService.saveBatch(batch);
        newBatchNumber = String.format("S%05d", newNumber);
        var fileName = FI_CODE + "_" + currentDate + "_" + version + "_" + newBatchNumber + ".txt" ;
        return fileName;
    }

    public void createDownloadRequest(String username) throws CsvProcessingException{
        var clients = clientService.getAllActiveClients();
        for(Client client : clients) {
           downloadService.createDownloadRequest(client, username);
        }
    }

    public void checkDownloadResponse() throws CsvProcessingException{
        var clients = clientService.getAllActiveClients();
        for(Client client : clients) {
            downloadService.checkDownloadResponse(client);
        }
    }
}
