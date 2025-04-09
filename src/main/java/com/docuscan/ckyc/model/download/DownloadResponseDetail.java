package com.docuscan.ckyc.model.download;

import com.docuscan.ckyc.model.Address;
import com.docuscan.ckyc.model.CkycVerifier;
import com.docuscan.ckyc.model.Name;
import com.docuscan.ckyc.model.PhoneDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadResponseDetail {
    @Builder.Default
    private int recordType = 20; // TODO: define enum
    private int lineNumber;
    @Builder.Default
    private int applicationType = 4; // TODO: define enum
    @Builder.Default // 1 = Individual
    private String constitutionType = "1"; // TODO: define enum
    // private String constitutionTypeOthers; // only when constitutionType is R(Other)
    // 01 = individual
    private int accountType = 1; // TODO: define enum
    private String ckycNumber;

    private Name applicantName;
    private Name maidenName;
    // 1 = father, 2 = spouse
    private int relationshipType; //TODO create enum
    private Name relatedPersonName;
    private Name motherName;

    private String gender; //TODO add enum - M, F, T

    private Instant dateOfBirth;

    private String panNo;

    private Address address;
    private String isSameAsCurrentAddress; //TODO convert to boolean
    private Address currentAddress;

    private PhoneDetail residencePhone;
    private PhoneDetail officePhone;
    private PhoneDetail mobilePhone;
    private PhoneDetail faxNumber;

    private String email;

    private String remarks;

    private Instant declarationDate;
    private String declarationPlace;

    private Instant kycVerificationDate;

    private int typeOfDocumentSubmitted; //TODO create enum

    private CkycVerifier ckycVerifier;

    private String organisationName;
    private String organisationCode;

    private int numberOfIdentityDetail;
    private int numberOfRelatedPersonDetail;
    private int numberOfImages;

    private String errorCode;

    private List<DownloadResponseDocumentDetails> documents;
}
