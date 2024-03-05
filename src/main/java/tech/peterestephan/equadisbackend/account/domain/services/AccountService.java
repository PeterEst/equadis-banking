package tech.peterestephan.equadisbackend.account.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.peterestephan.equadisbackend.common.exceptions.NotFoundException;
import tech.peterestephan.equadisbackend.common.exceptions.RequiredFieldException;
import tech.peterestephan.equadisbackend.customer.domain.entities.Customer;
import tech.peterestephan.equadisbackend.customer.domain.services.CustomerService;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.account.infrastructure.repositories.AccountRepository;
import tech.peterestephan.equadisbackend.transaction.domain.services.TransactionService;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public AccountService(AccountRepository accountRepository, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Page<Account> getAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Account save(Account account) {
        if (account.getCustomer().getId() == 0) {
            throw new RequiredFieldException("Customer ID is required");
        }

        Customer assignedCustomer = customerService.findById(account.getCustomer().getId());
        account.setCustomer(assignedCustomer);

        return accountRepository.save(account);
    }

    public Account findById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        return optionalAccount.get();
    }
}
