package com.docuscan.ckyc.repository;

import com.docuscan.ckyc.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    // Additional query methods can be added here if needed
    List<Customer> findAllByPanNoIn(List<String> panNumbers);
}

