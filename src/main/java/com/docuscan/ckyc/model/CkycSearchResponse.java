package com.docuscan.ckyc.model;

import lombok.Data;

import java.util.List;

@Data
public class CkycSearchResponse {
    private SearchResponseHeader header;
    private List<SearchResponseDetail> details;
}
