package com.docuscan.ckyc.model.search;

import lombok.Data;

import java.util.List;

@Data
public class CkycSearchResponse {
    private SearchResponseHeader header;
    private List<SearchResponseDetail> details;
}
