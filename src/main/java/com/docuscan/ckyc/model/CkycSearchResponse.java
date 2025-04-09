package com.docuscan.ckyc.model;

import com.docuscan.ckyc.model.search.SearchResponseDetail;
import com.docuscan.ckyc.model.search.SearchResponseHeader;
import lombok.Data;

import java.util.List;

@Data
public class CkycSearchResponse {
    private SearchResponseHeader header;
    private List<SearchResponseDetail> details;
}
