package com.docuscan.ckyc.service.download;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.model.Client;
import com.docuscan.ckyc.model.search.CkycSearchResponse;
import com.docuscan.ckyc.model.search.SearchResponseDetail;
import com.docuscan.ckyc.service.CustomerService;
import com.docuscan.ckyc.service.FilePathService;
import com.docuscan.ckyc.service.search.CsvSearchService;
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

    public void createDownloadRequest(Client client, String username) throws CsvProcessingException {
        var path = pathService.getSearchResponseDirPath(client);
        var searchResponses = searchService.readSearchResponse(path);

        for(CkycSearchResponse searchRes : searchResponses) {
            var panNumbers = searchRes.getDetails()
                    .stream()
                    .map(SearchResponseDetail::getIdentityNumber)
                    .toList();
            var customers = customerService.getCustomersByPanNumber(panNumbers);
            var downloadReq = CkycConverter.convertToDownloadRequest(searchRes, customers, client);
            downloadService.createDownloadRequestCsv(client, downloadReq, username);
        }
    }

    public void checkDownloadResponse(Client client) throws CsvProcessingException{
        var path = pathService.getDownloadResponseDirPath(client);
        var downloadRes = downloadService.readDownloadResponse(path);
        downloadService.saveAll(downloadRes);

    }
}
