package com.diego.bank_api.service;

import com.diego.bank_api.dto.customer.CustomerCreateRequest;
import com.diego.bank_api.dto.customer.CustomerResponse;
import com.diego.bank_api.entity.Customer;
import com.diego.bank_api.entity.enums.CustomerStatus;
import com.diego.bank_api.exception.*;
import com.diego.bank_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        if (customerRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if (customerRepository.findByDocumentNumber(request.documentNumber()).isPresent()) {
            throw new DocumentAlreadyExistsException();
        }

        Customer customer = new Customer();
        customer.setFullName(request.fullName());
        customer.setEmail(request.email());
        customer.setDocumentNumber(request.documentNumber());
        customer.setPhone(request.phone());
        customer.setBirthDate(request.birthDate());
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setCreatedAt(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);
        return toResponse(savedCustomer);
    }

    public CustomerResponse findCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return toResponse(customer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CustomerResponse updateCustomer(Long customerId, CustomerCreateRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        if (customerRepository.findByEmail(request.email()).isPresent()
                && !customer.getEmail().equals(request.email())) {
            throw new EmailAlreadyExistsException();
        }

        if (customerRepository.findByDocumentNumber(request.documentNumber()).isPresent()
                && !customer.getDocumentNumber().equals(request.documentNumber())) {
            throw new DocumentAlreadyExistsException();
        }

        customer.setFullName(request.fullName());
        customer.setEmail(request.email());
        customer.setDocumentNumber(request.documentNumber());
        customer.setPhone(request.phone());
        customer.setBirthDate(request.birthDate());

        Customer savedCustomer = customerRepository.save(customer);

        return toResponse(savedCustomer);
    }

    @Transactional
    public void removeCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        customerRepository.delete(customer);
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getDocumentNumber(),
                customer.getPhone(),
                customer.getBirthDate(),
                customer.getStatus(),
                customer.getCreatedAt()
        );
    }
}