package com.docuscan.ckyc.model.search;

import lombok.Data;

@Data
public class SearchResponseDetail {
    private int recordType = 20;
    private int lineNumber;
    private String identityType;
    private String identityNumber;
    private String kycNumber;
    private String applicantName;
    private String pan;
    private long aadhaar;
    private String voterId;
    private String passport;
    private String drivingLicense;
    private String nrega;
    private String errorMessage;
    private String nationalPopulationRegisterLetter;
    private String certificateOfIncorporation;
    private String registrationCertificate;
}
