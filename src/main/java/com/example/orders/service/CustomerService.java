package com.example.orders.service;

import com.example.orders.model.Customer;
import com.example.orders.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> getAll() {
        return repository.findAll();
    }

    public Customer getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Customer update(Long id, Customer customer) {
        Customer existing = repository.findById(id).orElseThrow();
        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}