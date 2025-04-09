package com.docuscan.ckyc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeaderRecord {
    private Integer recordType = 10;
    private String fiCode;
    private String regionCode;
    private Integer totalNoOfDetailRecords;
    private String versionNumber;
    private Instant createDate;

}
