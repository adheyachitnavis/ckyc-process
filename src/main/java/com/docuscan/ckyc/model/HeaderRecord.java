package com.docuscan.ckyc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeaderRecord {

    @JsonProperty("RECORD_TYPE")
    private Integer recordType;

    @JsonProperty("FI_CODE")
    private String fiCode;

    @JsonProperty("REGION_CODE")
    private String regionCode;

    @JsonProperty("TOTAL_NO_OF_DETAIL_RECORDS")
    private Integer totalNoOfDetailRecords;

    @JsonProperty("VERSION_NUMBER")
    private String versionNumber;

    @JsonProperty("CREATE_DATE")
    private Instant createDate;

    @JsonProperty("FILLER_1")
    private String filler1; //optional

    @JsonProperty("FILLER_2")
    private String filler2; //optional

    @JsonProperty("FILLER_3")
    private String filler3; //optional

    @JsonProperty("FILLER_4") // added this as extra pipe was needed.
    private String filler4;

}
