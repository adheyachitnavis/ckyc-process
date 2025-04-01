package com.docuscan.ckyc.util;

import com.docuscan.ckyc.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CkycConverter {
    public static CkycDownloadRequest convertToDownloadRequest(CkycSearchResponse searchResponse, List<Customer> customers) {

        var panToCustomerMap = customers.stream().collect(Collectors.toMap(
                Customer::getPanNo,
                customer -> customer
        ));

        var searchDetails = searchResponse.getDetails().stream()
                .filter(searchResponseDetail -> searchResponseDetail.getKycNumber() != null
                            && !searchResponseDetail.getKycNumber().trim().isEmpty()
                )
                .filter(detail -> panToCustomerMap.containsKey(detail.getIdentityNumber()))
                .toList();


        CkycDownloadRequest downloadRequest = new CkycDownloadRequest();

        // Convert header
        SearchResponseHeader searchHeader = searchResponse.getHeader();
        CkycDownloadHeader downloadHeader = new CkycDownloadHeader();
        downloadHeader.setRecordType(10);
        downloadHeader.setBatchNumber(searchHeader.getBatchId());
        downloadHeader.setFiCode(searchHeader.getFiCode());
        downloadHeader.setRegionCode(searchHeader.getRegionCode());
        downloadHeader.setBranchCode("");
        downloadHeader.setTotalNoOfDetailRecords(searchDetails.size());
        downloadHeader.setCustType(1);
        downloadRequest.setHeader(downloadHeader);

        // Convert details
        var downloadDetails = searchDetails.stream()
                .map(detail -> {

                    var cust = panToCustomerMap.get(detail.getIdentityNumber());
                    CkycDownloadDetail downloadDetail = new CkycDownloadDetail();
                    downloadDetail.setBulkDownloadRecordType(60);
                    downloadDetail.setCkycNo(Long.parseLong(detail.getKycNumber()));
                    //convert date from dd-mm-yyyy to dd-MMM-yyyy
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                    Date date = null;
                    try {
                        date = inputFormat.parse(cust.getDateOfBirth());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    String formattedDate = outputFormat.format(date);

                    downloadDetail.setAuthenticationFactor(formattedDate);
                    downloadDetail.setAuthenticationFactorType(01);
                    downloadDetail.setFiller1(detail.getErrorMessage());
                    return downloadDetail;
                })
                .collect(Collectors.toList());

        downloadRequest.setDetails(downloadDetails);
        return downloadRequest;
    }
}
