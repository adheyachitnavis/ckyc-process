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
public class DetailRecord {

    @JsonProperty("RECORD_TYPE")
    private Integer recordType;

    @JsonProperty("LINE_NUMBER")
    private Integer lineNumber;

    @JsonProperty("IDENTITY_TYPE")
    private String identityType;

    @JsonProperty("IDENTITY_NUMBER")
    private String identityNumber;

    @JsonProperty("NAME")
    private String name; //optional

    @JsonProperty("DOB")
    private Instant dob; //optional

    @JsonProperty("GENDER")
    private String gender; //optional
}
