package tech.peterestephan.equadisbackend.account.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.peterestephan.equadisbackend.account.application.dtos.AccountCreationDto;
import tech.peterestephan.equadisbackend.account.application.dtos.AccountDto;
import tech.peterestephan.equadisbackend.account.domain.mappers.AccountMapper;
import tech.peterestephan.equadisbackend.common.exceptions.NotFoundException;
import tech.peterestephan.equadisbackend.customer.domain.entities.Customer;
import tech.peterestephan.equadisbackend.customer.domain.services.CustomerService;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.account.infrastructure.repositories.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountMapper = accountMapper;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Page<AccountDto> getAll(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);

        return accounts.map(accountMapper::accountToAccountDto);
    }

    public AccountDto saveDto(AccountCreationDto accountCreationDto) {
        Customer assignedCustomer = customerService.findById(accountCreationDto.getCustomer());

        Account account = accountMapper.accountCreationDtoToAccount(accountCreationDto, assignedCustomer);

        accountRepository.save(account);

        return accountMapper.accountToAccountDto(account);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account findById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        return optionalAccount.get();
    }

    public AccountDto findDtoById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        return accountMapper.accountToAccountDto(optionalAccount.get());
    }
}
