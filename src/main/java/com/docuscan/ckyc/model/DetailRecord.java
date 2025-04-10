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
public class DetailRecord {

    @Builder.Default
    private Integer recordType = 20;
    private Integer lineNumber;
    private String identityType;
    private String identityNumber;
    private String name; //optional
    private Instant dob; //optional
    private String gender; //optional
}
