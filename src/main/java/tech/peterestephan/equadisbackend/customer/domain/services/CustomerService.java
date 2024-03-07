package tech.peterestephan.equadisbackend.customer.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.peterestephan.equadisbackend.account.application.dtos.AccountDto;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.account.domain.mappers.AccountMapper;
import tech.peterestephan.equadisbackend.common.exceptions.NotFoundException;
import tech.peterestephan.equadisbackend.customer.application.dtos.CustomerDto;
import tech.peterestephan.equadisbackend.customer.domain.entities.Customer;
import tech.peterestephan.equadisbackend.account.infrastructure.repositories.AccountRepository;
import tech.peterestephan.equadisbackend.customer.domain.mappers.CustomerMapper;
import tech.peterestephan.equadisbackend.customer.infrastructure.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CustomerMapper customerMapper;
    private final AccountMapper accountMapper;

    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository, CustomerMapper customerMapper, AccountMapper accountMapper) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.customerMapper = customerMapper;
        this.accountMapper = accountMapper;
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Page<CustomerDto> getAll(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);

        return customers.map(customerMapper::customerToCustomerDto);
    }

    public Page<CustomerDto> searchByName(String name, Pageable pageable) {
        Page<Customer> customers = customerRepository.findByNameContaining(name, pageable);

        return customers.map(customerMapper::customerToCustomerDto);
    }

    public CustomerDto getDtoById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }

        return customerMapper.customerToCustomerDto(optionalCustomer.get());
    }

    public Customer getById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }

        return optionalCustomer.get();
    }

    public CustomerDto saveDto(CustomerDto customerDto) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDto);

        Customer createdCustomer = customerRepository.save(customer);

        return customerMapper.customerToCustomerDto(createdCustomer);
    }

    public Page<AccountDto> getCustomerAccounts(Long customerId, Pageable pageable) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }

        Page<Account> accounts = accountRepository.findByCustomerId(customerId, pageable);

        return accounts.map(accountMapper::accountToAccountDto);
    }
}
