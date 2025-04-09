package com.docuscan.ckyc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String line1;
    private String line2;
    private String line3;
    private String city;
    private String district;
    private String state;
    private String country;
    private String pincode;
    private String proofType;
}
