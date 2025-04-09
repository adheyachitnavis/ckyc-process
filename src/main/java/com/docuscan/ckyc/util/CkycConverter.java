package com.docuscan.ckyc.util;

import com.docuscan.ckyc.model.*;
import com.docuscan.ckyc.model.download.DownloadRequestDetail;
import com.docuscan.ckyc.model.download.DownloadRequestHeader;
import com.docuscan.ckyc.model.download.DownloadRequest;
import com.docuscan.ckyc.model.search.SearchResponseHeader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CkycConverter {
    public static DownloadRequest convertToDownloadRequest(CkycSearchResponse searchResponse, List<Customer> customers, Client client) {

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


        DownloadRequest downloadRequest = new DownloadRequest();

        // Convert header
        SearchResponseHeader searchHeader = searchResponse.getHeader();
        DownloadRequestHeader downloadHeader = new DownloadRequestHeader();
        downloadHeader.setRecordType(10);
        downloadHeader.setBatchNumber(searchHeader.getBatchId());
        downloadHeader.setFiCode(searchHeader.getFiCode());
        downloadHeader.setRegionCode(searchHeader.getRegionCode());
        downloadHeader.setBranchCode(client.getBranchCode());
        downloadHeader.setTotalNoOfDetailRecords(searchDetails.size());
        downloadHeader.setCustType(1);
        downloadRequest.setHeader(downloadHeader);

        // Convert details
        var downloadDetails = searchDetails.stream()
                .map(detail -> {

                    var cust = panToCustomerMap.get(detail.getIdentityNumber());
                    DownloadRequestDetail downloadDetail = new DownloadRequestDetail();
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
