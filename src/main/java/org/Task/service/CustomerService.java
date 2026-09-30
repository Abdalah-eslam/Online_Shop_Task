package org.Task.service;

import jakarta.transaction.Transactional;
import org.Task.exception.DuplicateCustomerException;
import org.Task.model.Customer;
import org.Task.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer register(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            throw new DuplicateCustomerException("Email already exists");
        }
        return customerRepository.save(customer);
    }




}
