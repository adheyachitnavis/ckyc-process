package com.docuscan.ckyc.service;

import com.docuscan.ckyc.model.Customer;
import com.docuscan.ckyc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getCustomersByPanNumber(List<String> panNumbers) {
        return customerRepository.findAllByPanNoIn(panNumbers);
    }
}
