package com.docuscan.ckyc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "customer")
public class Customer {
    @Id
    private String id;
    @JsonProperty("APPLICATION_ID")
    private String applicationId;
    @JsonProperty("AGREEMENT_NO")
    private String agreementNo;
    @JsonProperty("CIF_NO")
    private String cifNo;
    @JsonProperty("CUSTOMER_TYPE")
    private String customerType;
    @JsonProperty("CUSTOMER_ID")
    private String customerId;
    @JsonProperty("PREFIX")
    private String prefix;
    @JsonProperty("CUSTOMER_NAME")
    private String customerName;
    @JsonProperty("FIRST_NAME")
    private String firstName;
    @JsonProperty("MIDDLE_NAME")
    private String middleName;
    @JsonProperty("LAST_NAME")
    private String lastName;
    @JsonProperty("FATHER_FIRST_NAME")
    private String fatherFirstName;
    @JsonProperty("FATHER_NAME")
    private String fatherName;
    @JsonProperty("SPOUSE_FIRST_NAME")
    private String spouseFirstName;
    @JsonProperty("SPOUSE_NAME")
    private String spouseName;
    @JsonProperty("MOTHERS_NAME")
    private String mothersName;
    @JsonProperty("GENDER")
    private String gender;
    @JsonProperty("CONSTITUTION")
    private String constitution;
    @JsonProperty("DATE_OF_BIRTH")
    private String dateOfBirth;
    @JsonProperty("PAN_NO")
    private String panNo;
    @JsonProperty("MAILING_ADDRESS_1")
    private String mailingAddress1;
    @JsonProperty("MAILING_ADDRESS_2")
    private String mailingAddress2;
    @JsonProperty("MAILING_ADDRESS_3")
    private String mailingAddress3;
    @JsonProperty("MAILING_ADDRESS_CITY")
    private String mailingAddressCity;
    @JsonProperty("MAILING_ADDRESS_STATE")
    private String mailingAddressState;
    @JsonProperty("PINCODE")
    private String pincode;
    @JsonProperty("STATE_CODE")
    private String stateCode;
    @JsonProperty("MOBILE_NO")
    private String mobileNo;
    @JsonProperty("EMAIL_ID")
    private String emailId;
    @JsonProperty("LOAN_STATUS")
    private String loanStatus;
    @JsonProperty("CURRENT_DATE")
    private String currentDate;
    @JsonProperty("CHEQUE_HANDOVER_DATE")
    private String chequeHandoverDate;
    @JsonProperty("FIRST_DISB_DATE")
    private String firstDisbDate;
    @JsonProperty("COMPANY_NAME")
    private String companyName;
}