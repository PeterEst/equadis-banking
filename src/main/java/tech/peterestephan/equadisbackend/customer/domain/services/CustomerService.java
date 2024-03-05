package tech.peterestephan.equadisbackend.customer.domain.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.common.exceptions.NotFoundException;
import tech.peterestephan.equadisbackend.customer.domain.entities.Customer;
import tech.peterestephan.equadisbackend.account.infrastructure.repositories.AccountRepository;
import tech.peterestephan.equadisbackend.customer.infrastructure.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Page<Customer> searchByName(String name, Pageable pageable){
        return customerRepository.findByNameContaining(name, pageable);
    }

    public Customer findById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }

        return optionalCustomer.get();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Account> getCustomerAccounts(Long customerId) {
        // Validates customer existence
        this.findById(customerId);
        return accountRepository.findByCustomerId(customerId);
    }

    public Page<Account> getCustomerAccounts(Long customerId, Pageable pageable) {
        // Validates customer existence
        this.findById(customerId);
        return accountRepository.findByCustomerId(customerId, pageable);
    }
}
