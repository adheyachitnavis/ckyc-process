package com.docuscan.ckyc.model.search;

import com.docuscan.ckyc.model.DetailRecord;
import com.docuscan.ckyc.model.HeaderRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "search_input_batch")
public class SearchInputBatch {

    @Id
    private String id;

    private String batchNo;

    @JsonProperty("HEADER_RECORD")
    private HeaderRecord headerRecord;

    @JsonProperty("DETAIL_RECORDS")
    private List<DetailRecord> detailRecords;
}
