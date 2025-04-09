package com.docuscan.ckyc.exception;

import com.docuscan.ckyc.model.Customer;
import lombok.Getter;

@Getter
public class KycProcessingException extends Exception {
    private final Customer customer;

    public KycProcessingException(Customer newCustomer, Exception e) {
        super(e);
        this.customer = newCustomer;
    }
}
