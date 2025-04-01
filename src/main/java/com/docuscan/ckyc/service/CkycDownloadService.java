package com.docuscan.ckyc.service;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.model.CkycSearchResponse;
import com.docuscan.ckyc.model.Client;
import com.docuscan.ckyc.model.SearchResponseDetail;
import com.docuscan.ckyc.util.CkycConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CkycDownloadService {

    private final FilePathService pathService;
    private final CsvSearchService searchService;
    private final CsvDownloadService downloadService;
    private final CustomerService customerService;

    public void createDownloadRequest(Client client) throws CsvProcessingException {
        var path = pathService.getSearchResponseDirPath(client);
        var searchResponses = searchService.readSearchResponse(path);

        for(CkycSearchResponse searchRes : searchResponses) {
            var panNumbers = searchRes.getDetails()
                    .stream()
                    .map(SearchResponseDetail::getIdentityNumber)
                    .toList();
            var customers = customerService.getCustomersByPanNumber(panNumbers);
            var downloadReq = CkycConverter.convertToDownloadRequest(searchRes, customers);
            downloadService.createDownloadRequestCsv(client, downloadReq);
        }
    }

}
