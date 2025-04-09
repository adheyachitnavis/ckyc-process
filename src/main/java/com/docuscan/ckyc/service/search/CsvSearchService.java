package com.docuscan.ckyc.service.search;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.model.CkycSearchResponse;
import com.docuscan.ckyc.util.SearchResponseCsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvSearchService {

    public List<CkycSearchResponse> readSearchResponse(String searchResDirPath) throws CsvProcessingException {
        try {
            return SearchResponseCsvUtils.read(searchResDirPath);
        } catch (Exception e) {
            throw new CsvProcessingException(e.getMessage(), e);
        }
    }
}
